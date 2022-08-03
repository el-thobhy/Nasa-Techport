package com.elthobhy.nasatechport.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.elthobhy.nasatechport.databinding.ActivityDetailBinding
import com.elthobhy.nasatechport.utils.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactoryDetail(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(Constants.DATA)
        Log.e("id", "onCreate: $id", )
        if (id != null) {
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
                }
            }
        }

    }
}