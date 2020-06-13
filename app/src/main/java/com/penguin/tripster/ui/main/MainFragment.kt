package com.penguin.tripster.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.penguin.tripster.R
import com.penguin.tripster.databinding.MainFragmentBinding
import com.penguin.tripster.model.PlaceOfInterest
import com.penguin.tripster.ui.adapter.NearbyPlacesRecyclerViewAdapter
import com.penguin.tripster.ui.adapter.SpotlightPlacesRecyclerViewAdapter

class MainFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private lateinit var binding: MainFragmentBinding
    private lateinit var mContext: Context
    private val placesList = ArrayList<PlaceOfInterest>()
    private val topPlacesList = ArrayList<PlaceOfInterest>()

    companion object {
        var TAG: String = MainFragment::class.java.simpleName
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.isLoading = viewModel.isLoading

        val verticalLayoutManager = LinearLayoutManager(mContext)
        verticalLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.layoutManager = verticalLayoutManager
        binding.adapter = NearbyPlacesRecyclerViewAdapter(mContext, placesList)

        val horizontalLayoutManager = LinearLayoutManager(mContext)
        horizontalLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.layoutManagerSpotlight = horizontalLayoutManager
        binding.adapterSpotlight = SpotlightPlacesRecyclerViewAdapter(mContext, topPlacesList)

        viewModel.showLoading()
        initListeners()
        initObservers()
        viewModel.refreshPlaces(true)

        return binding.root
    }

    private fun initListeners() {
        binding.swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            viewModel.refreshPlaces(true)
        }
    }

    private fun initObservers() {
        viewModel.listDisplayPlaces.observe(viewLifecycleOwner, Observer { list ->
            if (list != null && list.isNotEmpty()) {
                placesList.clear()
                topPlacesList.clear()

                when(list.size) {
                    in 0..3 -> {
                        topPlacesList.addAll(list.subList(0, list.lastIndex))
                    }
                    else -> {
                        topPlacesList.addAll(list.subList(0, 3))
                        placesList.addAll(list.subList(3, list.lastIndex))
                    }
                }

                binding.adapter?.notifyDataSetChanged()
                binding.adapterSpotlight?.notifyDataSetChanged()
                viewModel.hideLoading()
            }
        })

        viewModel.isError.observe(viewLifecycleOwner, Observer {message ->
            message?.run{
                viewModel.hideLoading()
                Toast.makeText(mContext, this, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroy() {
        if (viewModel.listDisplayPlaces.hasObservers()) viewModel.listDisplayPlaces.removeObservers(this)
        if (viewModel.isError.hasObservers()) viewModel.isError.removeObservers(this)
        viewModel.job?.cancel()
        super.onDestroy()
    }

}