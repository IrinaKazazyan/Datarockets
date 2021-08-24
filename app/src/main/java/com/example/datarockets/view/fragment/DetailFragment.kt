package com.example.datarockets.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.datarockets.R
import com.example.datarockets.epoxy.DetailController
import com.example.datarockets.utils.AppUtil
import com.example.datarockets.viewmodel.DetailViewModel

class DetailFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var detailController: DetailController
    private lateinit var recyclerView: EpoxyRecyclerView

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
        recyclerView = view.findViewById<EpoxyRecyclerView>(R.id.detail_recycler_view)
        recyclerView.setController(detailController)

        setRecyclerData()
    }

    private fun setRecyclerData() {
        detailViewModel.beerLiveData.observe(viewLifecycleOwner,
            Observer { it ->
                if (it == null) {
                    Toast.makeText(requireContext(), "it equals null", Toast.LENGTH_LONG).show()
                } else {
                    detailController.setData(it[0])
                }
            })
        detailViewModel.getBeerItemObservable(requireContext(), beerId)
    }

    override fun onRefresh() {
        if (AppUtil.isNetworkConnected(requireContext())) {
            refreshData()
        } else {
            Toast.makeText(
                requireContext(),
                "Oops!! Check your network connection!",
                Toast.LENGTH_LONG
            ).show()
        }
        swipeRefreshLayout.isRefreshing = false

        swipeRefreshLayout.setColorSchemeColors(
            resources.getColor(android.R.color.holo_blue_bright),
            resources.getColor(android.R.color.holo_orange_dark),
            resources.getColor(android.R.color.holo_green_dark),
            resources.getColor(android.R.color.holo_red_dark)
        )
    }

    private fun refreshData() {
        detailViewModel.getBeerItemById(requireContext(), beerId)
        detailViewModel.getBeerItemObservable(requireContext(), beerId)
        recyclerView.setController(detailController)
    }
}