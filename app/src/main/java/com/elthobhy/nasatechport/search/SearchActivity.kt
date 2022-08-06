package com.elthobhy.nasatechport.search

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.elthobhy.nasatechport.R
import com.elthobhy.nasatechport.core.utils.vo.Status
import com.elthobhy.nasatechport.databinding.ActivitySearchBinding
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val adapterList by inject<SearchAdapter>()
    private val searchViewModel by inject<SearchViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSearch.adapter = adapterList
        setSupportActionBar(binding.actionBar)
        supportActionBar?.title = ""

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0!=null){
                    showData(p0)
                }
                Log.e("tes", "onQueryTextChange: $p0" )
                return true
            }

        })

        return true
    }

    private fun showData(newText: String) {
        searchViewModel.getDataSearch(newText).observe(this){
            if(it.data!=null){
                when(it.status){
                    Status.LOADING->{
                        Log.e("loading", "showData: " )
                    }
                    Status.SUCCESS->{
                        adapterList.submitList(it.data)
                    }
                    Status.ERROR->{
                        Log.e("eror", "showData: ${it.message}" )
                    }
                }
            }
        }
    }

}