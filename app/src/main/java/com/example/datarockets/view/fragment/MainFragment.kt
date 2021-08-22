package com.example.datarockets.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
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
import com.example.datarockets.model.BeersListItem
import com.example.datarockets.utils.AppUtil.isNetworkConnected
import com.example.datarockets.viewmodel.MainViewModel

class MainFragment : Fragment(),
    RecyclerViewAdapter.OnBeerClickListener {


    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel
    private var page = 0
    private var isScrolling: Boolean? = false
    private var currentItems: Int = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0

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
        navController = Navigation.findNavController(view)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(onSwipeRefreshListener)
        initRecyclerView(view)
        observeBeerItemList()
    }

    private fun initRecyclerView(view: View) {
        Log.d("TAG", "Main Fragment initRecyclerView")
        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainFragment)
            adapter = recyclerViewAdapter

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
                        observeBeerItemList()
                    }
                }
            })
        }
    }

    private var onSwipeRefreshListener =
        SwipeRefreshLayout.OnRefreshListener {
            Log.d("LOG_TAG", "onRefresh")
            if (isNetworkConnected(requireContext())) {
                observeBeerItemList()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Oops!! Check your network connection!",
                    Toast.LENGTH_LONG
                ).show()
                swipeRefreshLayout.isRefreshing = false
            }
        }

    private fun observeBeerItemList() {
        val bConnected = isNetworkConnected(requireContext())
        page += 1
        mainViewModel.getBeerItemListObservable(requireContext(), bConnected, page, 10)
            .observe(requireActivity(), Observer<List<BeersListItem>>(function = { imageItemList ->
                if (imageItemList != null) {
                    recyclerViewAdapter.beerList = imageItemList
                    recyclerViewAdapter.notifyDataSetChanged()
                }
                swipeRefreshLayout.isRefreshing = false
            }))
    }

    override fun itemClicked(beersListItem: BeersListItem) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                beersListItem.id
            )
        )
        Log.d("TAG", " item clicked ${beersListItem.id}")
    }
}