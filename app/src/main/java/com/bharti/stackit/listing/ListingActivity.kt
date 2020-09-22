package com.bharti.stackit.listing

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bharti.stackit.R
import kotlinx.android.synthetic.main.activity_listing.*

class ListingActivity : AppCompatActivity() {

    private val viewModel: ListingViewModel by lazy { ViewModelProvider(this).get(ListingViewModel::class.java) }

    private lateinit var stackAdapter: StackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)
        getStackData()
        initSwipeLayout()
        setupRecycler()
        setupObservers()
        retry?.setOnClickListener {
            retry?.visibility = View.GONE
            getStackData()
        }
    }

    private fun getStackData() {
        swipeLayout?.isRefreshing = true
        viewModel.getStackData()
    }

    private fun initSwipeLayout() {
        swipeLayout?.setOnRefreshListener { getStackData() }
    }

    private fun setupRecycler() {
        stackAdapter = StackAdapter(mutableListOf())
        val linearLayoutManager = LinearLayoutManager(this)
        rvStackList.apply {
            layoutManager = linearLayoutManager
            adapter = stackAdapter
            val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(context, R.drawable.divider)
                ?.let { itemDecoration.setDrawable(it) }
            addItemDecoration(itemDecoration)
        }
    }

    private fun setupObservers() {
        viewModel.listingLiveData.observe(this, {
            swipeLayout?.isRefreshing = false
            rvStackList.visibility = View.VISIBLE
            stackAdapter.updateList(it)
        })

        viewModel.errorLiveData.observe(this, {
            swipeLayout?.isRefreshing = false
            rvStackList.visibility = View.GONE
            retry.visibility = View.VISIBLE
        })
    }
}