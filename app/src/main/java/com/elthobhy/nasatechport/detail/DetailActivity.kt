package com.elthobhy.nasatechport.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.elthobhy.nasatechport.R
import com.elthobhy.nasatechport.core.utils.Constants
import com.elthobhy.nasatechport.databinding.ActivityDetailBinding
import org.koin.android.ext.android.inject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by inject<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        setData()
    }


    private fun setData() {
        val id = intent.getStringExtra(Constants.DATA)
        val title = intent.getStringExtra(Constants.APOD)
        when{
            id != null ->{
                viewModel.getDetail(id).observe(this){ dataDetail->
                    binding.apply {
                        val latestUpdated = dataDetail.lastupdated?.split('T')
                        val updated = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate.parse(latestUpdated?.get(0), DateTimeFormatter.ISO_DATE)
                        } else {
                            dataDetail.lastupdated
                        }
                        titleProject.text = dataDetail?.title
                        lastUpdated.text = updated.toString()
                        responsibleNasaProgram.text = dataDetail?.responsiblenasaprogram
                        descriptionProject.text = dataDetail?.description
                        goToLink.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(dataDetail?.projecturl?.urlString)
                            startActivity(intent)
                        }
                        imageDetail.layoutParams.height = 0
                        supportActionBar?.title = "Nasa Techport Detail"
                    }
                }
            }
            title != null ->{
                viewModel.getDetailApod(title).observe(this){ apod->
                    binding.apply {
                        titleProject.text = apod?.title
                        lastUpdated.text = apod?.date
                        responsibleNasaProgram.text = apod?.copyright
                        descriptionProject.text = apod?.explanation
                        Glide.with(this@DetailActivity)
                            .load(apod?.hdurl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .placeholder(R.color.white)
                            .into(imageDetail)
                        imageDetail.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(apod?.url)
                            startActivity(intent)
                        }
                        goToLink.visibility = View.GONE
                        supportActionBar?.title = "Astronomy Picture of The Day"
                    }
                }
            }
        }
    }

    private fun initActionBar() {
        setSupportActionBar(binding.actionBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.actionBar.setNavigationOnClickListener {
            finish()
        }
    }
}