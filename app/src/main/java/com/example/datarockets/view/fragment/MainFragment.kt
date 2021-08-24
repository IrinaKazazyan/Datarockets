package com.example.datarockets.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.datarockets.R
import com.example.datarockets.adapters.RecyclerViewAdapter
import com.example.datarockets.constants.LOG_TAG
import com.example.datarockets.model.BeersListItem
import com.example.datarockets.utils.AppUtil.isNetworkConnected
import com.example.datarockets.viewmodel.MainViewModel

class MainFragment : Fragment(),
    RecyclerViewAdapter.OnBeerClickListener {


    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel
    private var page = 0
    private var isScrolling: Boolean? = false
    private var currentItems: Int = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerViewAdapter = RecyclerViewAdapter(this@MainFragment)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observeBeerItemList()
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
        Log.d(LOG_TAG, "Main Fragment onViewCreated")
        navController = Navigation.findNavController(view)
        progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(onSwipeRefreshListener)
        initRecyclerView(view)
    }

    private var onSwipeRefreshListener =
        SwipeRefreshLayout.OnRefreshListener {
            Log.d(LOG_TAG, "onRefresh")
            if (isNetworkConnected(requireContext())) {
                refreshData()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Oops!! Check your network connection!",
                    Toast.LENGTH_LONG
                ).show()
                swipeRefreshLayout.isRefreshing = false
            }


            swipeRefreshLayout.setColorSchemeColors(
                resources.getColor(android.R.color.holo_blue_bright),
                resources.getColor(android.R.color.holo_orange_dark),
                resources.getColor(android.R.color.holo_green_dark),
                resources.getColor(android.R.color.holo_red_dark)
            )

        }

    private fun initRecyclerView(view: View) {
        Log.d(LOG_TAG, "Main Fragment initRecyclerView")
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(decoration)
            adapter = recyclerViewAdapter

            pullDownToRefresh()
        }
    }

    private fun RecyclerView.pullDownToRefresh() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = layoutManager!!.childCount
                totalItems = layoutManager!!.itemCount
                scrollOutItems =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

                if (isScrolling!! && currentItems + scrollOutItems == totalItems) {
                    isScrolling = false

                    if (isNetworkConnected(requireContext())) {
                        observeBeerItemListByPage()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Oops!! Check your network connection!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    private fun observeBeerItemList() {
        val bConnected = isNetworkConnected(requireContext())
        mainViewModel.getBeersList(requireContext(), bConnected)
        mainViewModel.getBeerItemListObservable(requireContext())
            .observe(
                requireActivity(),
                Observer<List<BeersListItem>>(function = { beerItemList ->
                    if (beerItemList != null) {
                        recyclerViewAdapter.updateList(beerItemList)
                    }
                })
            )
    }

    private fun observeBeerItemListByPage() {
        observeProgressBar()
        page += 1
        mainViewModel.getBeerItemListByPageObservable(requireContext(), page)
            .observe(requireActivity(), Observer<List<BeersListItem>>(function = { beerItemList ->
                if (beerItemList != null) {
                    recyclerViewAdapter.updateList(beerItemList)
                }
                swipeRefreshLayout.isRefreshing = false
            }))
    }

    private fun refreshData() {
        mainViewModel.getBeerItemListObservable(requireContext())
            .observe(requireActivity(), Observer<List<BeersListItem>>(function = { beer ->
                if (beer != null) {
                    recyclerViewAdapter.updateList(beer.shuffled())
                }
                swipeRefreshLayout.isRefreshing = false
            }))
    }

    private fun observeProgressBar() {
        mainViewModel.progressbarObservable!!.observe(
            requireActivity(),
            Observer<Boolean>(function = { progressObserve ->
                if (progressObserve) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            })
        )
    }


    override fun itemClicked(beersListItem: BeersListItem) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                beersListItem.id
            )
        )
        Log.d(LOG_TAG, " item clicked ${beersListItem.id}")
    }
}