package com.example.datarockets.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.datarockets.R
import com.example.datarockets.epoxy.DetailController
import com.example.datarockets.viewmodel.DetailViewModel

class DetailFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var detailController: DetailController

    private var beerId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beerId = requireArguments().getInt("beerId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)

        detailController = DetailController()
        val recyclerView = view.findViewById<EpoxyRecyclerView>(R.id.detail_recycler_view)
        recyclerView.setController(detailController)

        setRecyclerData()
        Log.d("TAG", "onViewCreated")
    }

    private fun setRecyclerData() {
        detailViewModel.beerLiveData.observe(viewLifecycleOwner,
            Observer { it ->
                if (it == null) {
                    Toast.makeText(requireContext(), "it equals null", Toast.LENGTH_LONG).show()
                } else {
                    Log.d("TAG", "onViewCreated beerId $beerId")
                    detailController.setData(it[0])
                }
            })
        detailViewModel.getBeerItem(beerId)
    }

    override fun onRefresh() {
        Log.d("TAG", "detail fragment onRefresh")
        setRecyclerData()
        swipeRefreshLayout.isRefreshing = false
    }
}