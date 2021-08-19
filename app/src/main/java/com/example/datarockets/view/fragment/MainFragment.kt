package com.example.datarockets.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.datarockets.R
import com.example.datarockets.adapters.RecyclerViewAdapter
import com.example.datarockets.model.BeersList
import com.example.datarockets.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var mainViewModel: MainViewModel
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "Main Fragment onViewCreated")

        val swipeRefreshLayout = view.findViewById(R.id.pullToRefresh) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        Log.d("TAG", "Main Fragment initRecyclerView")
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = RecyclerViewAdapter()
            adapter = recyclerViewAdapter
        }
    }

    private fun initViewModel() {
        Log.d("TAG", "Main Fragment initViewModel")

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getBeersList()
        mainViewModel.getBeersListObservable().observe(requireActivity(), Observer<BeersList> {
            if (it == null) {
                Toast.makeText(requireContext(), "it equals null", Toast.LENGTH_LONG).show()
            } else {
                recyclerViewAdapter.beerList = it
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onRefresh() {
//        refreshData
        Log.d("TAG", "main fragment onRefresh")
        page += 1
        mainViewModel.getRefreshBeersList(page, 10)
        mainViewModel.getBeersListObservable().observe(requireActivity(), Observer<BeersList> {
            if (it == null) {
                Log.d("TAG", "main fragment onRefresh it == null")
                Toast.makeText(requireContext(), "it equals null", Toast.LENGTH_LONG).show()
            } else {
                recyclerViewAdapter.beerList.addAll(it)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        pullToRefresh.isRefreshing = false
    }
}