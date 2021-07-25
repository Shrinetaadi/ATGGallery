package com.shrinetaadi.atggallery.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shrinetaadi.atggallery.FlickrResult
import com.shrinetaadi.atggallery.viewmodel.HomeViewModel
import com.shrinetaadi.atggallery.R
import com.shrinetaadi.atggallery.adapter.RecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recycler_main.*

@AndroidEntryPoint
class RecyclerMainFragment : Fragment(R.layout.fragment_recycler_main) {
   lateinit var flickResult: MutableLiveData<FlickrResult>
    lateinit var page_No: MutableLiveData<Int>
    lateinit var error: MutableLiveData<Boolean>
    val recyclerViewAdapter = RecyclerViewAdapter(arrayListOf())
    val num_colomn = 2
    lateinit var viewModel: HomeViewModel

    lateinit var manager: StaggeredGridLayoutManager
    var isScolling = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recycler_main, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        flickResult = viewModel.getFlickrResponse()
        error = viewModel.error
        page_No = viewModel.PAGE_NO

        initViewModel()

        manager = StaggeredGridLayoutManager(num_colomn, LinearLayout.VERTICAL)
        recyclerHome.apply {
            layoutManager = manager
            adapter = recyclerViewAdapter
        }
        recyclerHome.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)

                        isScolling = true
                }

                override  fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)


                    val total = manager.itemCount
                    val current = manager.childCount
                    val scrollOutItems = manager.findFirstVisibleItemPositions(null)

                    if (isScolling && (scrollOutItems[0] + current >= total)) {
                        isScolling = false
                        Log.i(
                            RecyclerMainFragment::class.java.simpleName,
                            "$total $current (${scrollOutItems[0]}, ${scrollOutItems[1]})"
                        )
                        viewModel.loadNextPage()
                    }
                }

            })

                swipeRefreshLayout.setOnRefreshListener{
                    viewModel.refresh()
                }

    }


    private  fun initViewModel() {
        flickResult.observe(viewLifecycleOwner, Observer { flickrResult ->
            flickrResult?.let {
                val flickrPhotos: FlickrResult.FlickrPhoto = it.photos
                val listFlickrPhotos: List<FlickrResult.FlickrPhoto.Photo> = flickrPhotos.photo
                recyclerViewAdapter.updatePhoto(listFlickrPhotos, page_No.value!!)
                swipeRefreshLayout.isRefreshing = false
                for (i in listFlickrPhotos) {
                    Log.i(RecyclerMainFragment::class.java.simpleName, "${i.url_s}")
              }
            }
        })

        error.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it)
                    swipeRefreshLayout.isRefreshing = false
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    progressHome.visibility = View.VISIBLE
                } else {
                    progressHome.visibility = View.GONE
                }
            }
        })
    }


}
