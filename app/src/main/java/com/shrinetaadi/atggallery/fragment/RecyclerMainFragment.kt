package com.shrinetaadi.atggallery.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shrinetaadi.atggallery.MainActivityViewModel
import com.shrinetaadi.atggallery.R
import com.shrinetaadi.atggallery.adapter.RecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecyclerMainFragment : Fragment(R.layout.fragment_recycler_main) {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var navController : NavController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recycler_main, container, false)
        recyclerView = view.findViewById(R.id.recyclerHome)


        initViewModel()
        initRecyclerView()

        return view
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewAdapter = RecyclerViewAdapter(context as Context)
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                recyclerViewAdapter.setData(it)
                recyclerViewAdapter.setViewModel(viewModel)
                recyclerViewAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "ERROR0", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.loadlistofData()
    }


}
