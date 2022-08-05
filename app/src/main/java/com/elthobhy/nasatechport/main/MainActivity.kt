package com.elthobhy.nasatechport.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.elthobhy.nasatechport.R
import com.elthobhy.nasatechport.core.data.remote.RemoteDataSource
import com.elthobhy.nasatechport.core.ui.adapter.LoadingStateAdapter
import com.elthobhy.nasatechport.core.utils.vo.Status
import com.elthobhy.nasatechport.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by inject<MainViewModel>()
    private val apodViewModel by inject<ApodViewModel>()
    private val adapterList by inject<TechportListAdapter>()
    private val apodAdapter by inject<ApodAdapter>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataApod()
        getData()
    }

    private fun getDataApod() {
        binding.rvApod.apply {
            adapter = apodAdapter
            setHasFixedSize(true)
        }

        apodViewModel.apod().observe(this){
            if(it != null){
                when(it.status){
                    Status.LOADING->{

                    }
                    Status.SUCCESS->{
                        apodAdapter.submitList(it.data)
                        Log.e("dataMain", "getData: ${it.data}")
                    }
                    Status.ERROR->{
                        Log.e("mainActivity", "getData: ${it.data}", )
                    }
                }
            }
            else{
                Log.e("mainActivity", "getData: $it", )
            }
        }
    }

    private fun getData() {
        binding.rvQuote.adapter = adapterList.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapterList.retry()
            }
        )
        binding.rvQuote.layoutManager = LinearLayoutManager(this)
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