package com.elthobhy.nasatechport.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
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
                        binding.shimmerApod.visibility = View.VISIBLE
                        binding.rvApod.visibility = View.GONE
                    }
                    Status.SUCCESS->{
                        apodAdapter.submitList(it.data?.reversed())
                        Handler(Looper.getMainLooper())
                            .postDelayed({
                                binding.shimmerApod.visibility = View.GONE
                                binding.rvApod.visibility = View.VISIBLE
                            },1500)
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
                        binding.shimmerTechport.visibility = View.VISIBLE
                        binding.rvQuote.visibility = View.GONE
                    }
                    Status.SUCCESS->{
                        lifecycleScope.launchWhenStarted {
                            it.data?.let { it1 -> adapterList.submitData(it1) }
                        }
                        Handler(Looper.getMainLooper())
                            .postDelayed({
                                binding.shimmerTechport.visibility = View.GONE
                                binding.rvQuote.visibility = View.VISIBLE
                            },1500)
                    }
                    Status.ERROR->{
                        Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}