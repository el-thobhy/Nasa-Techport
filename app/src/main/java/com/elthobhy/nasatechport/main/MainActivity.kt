package com.elthobhy.nasatechport.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.elthobhy.nasatechport.databinding.ActivityMainBinding
import com.elthobhy.nasatechport.core.ui.adapter.LoadingStateAdapter
import com.elthobhy.nasatechport.core.ui.adapter.TechportListAdapter
import com.elthobhy.nasatechport.core.utils.vo.Status
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
        mainViewModel.techport().observe(this) {
            if(it!=null){
                when(it.status){
                    Status.LOADING->{
                        adapterList.retry()
                    }
                    Status.SUCCESS->{
                        lifecycleScope.launchWhenStarted {
                            it.data?.let { it1 -> adapterList.submitData(it1) }
                        }
                    }
                    Status.ERROR->{
                        Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}