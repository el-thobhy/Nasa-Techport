package com.elthobhy.nasatechport.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.elthobhy.nasatechport.databinding.ActivityMainBinding
import com.elthobhy.nasatechport.ui.adapter.LoadingStateAdapter
import com.elthobhy.nasatechport.ui.adapter.TechportListAdapter
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by inject<MainViewModel>()
    private val adapterList by inject<TechportListAdapter>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvQuote.layoutManager = LinearLayoutManager(this)

        getData()
    }

    private fun getData() {
        binding.rvQuote.adapter = adapterList.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapterList.retry()
            }
        )
        mainViewModel.quote.observe(this) {
            adapterList.submitData(lifecycle, it)
        }
    }
}