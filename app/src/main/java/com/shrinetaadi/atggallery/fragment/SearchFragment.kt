package com.shrinetaadi.atggallery.fragment

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shrinetaadi.atggallery.FlickrResult
import com.shrinetaadi.atggallery.R
import com.shrinetaadi.atggallery.adapter.SearchAdapter
import com.shrinetaadi.atggallery.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_fragment.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var flickrResult: MutableLiveData<FlickrResult>
    private lateinit var queryText: MutableLiveData<String>
    private lateinit var error: MutableLiveData<Boolean>
    private val searchAdapter = SearchAdapter(arrayListOf())
    private val Columns = 2
    private lateinit var searchView: SearchView
    private val disposables = CompositeDisposable()
    private var timeSinceLastRequest: Long = 0


    companion object {
        val TAG = SearchFragment::class.java.simpleName
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        flickrResult = viewModel.getFlickrResponse()
        queryText = viewModel.queryText
        error = viewModel.error
        setHasOptionsMenu(true)

        initViewModel()
        recyclerSearch.apply {
            layoutManager = StaggeredGridLayoutManager(Columns, LinearLayout.VERTICAL)
            adapter = searchAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.search_menu_1)
        searchView = item.actionView as SearchView

        timeSinceLastRequest = System.currentTimeMillis()
        createDebounceOperator()
    }

    private fun createDebounceOperator() {

        val observableQueryText = Observable.create<String> { emitter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!emitter.isDisposed) {
                        emitter.onNext(newText!!)
                    }
                    return false
                }
            })

        }
            .debounce(800, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())

        observableQueryText.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                disposables.add(d)
            }

            override fun onNext(t: String) {
                val str =
                    "onNext: time since last request:" + (System.currentTimeMillis() - timeSinceLastRequest)
                Log.d(TAG, str)
                Log.d(TAG, "onNext: search query:$t")
                timeSinceLastRequest = System.currentTimeMillis()
                sendRequestToserver(t)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete:")
            }

        })

    }

    private fun sendRequestToserver(query: String) {
        queryText.postValue(query.trim())
    }

    private fun initViewModel() {
        flickrResult.observe(viewLifecycleOwner, androidx.lifecycle.Observer { flickrResult ->
            flickrResult?.let {
                val flickrPhoto: FlickrResult.FlickrPhoto = it.photos
                val listFlickrphoto: List<FlickrResult.FlickrPhoto.Photo> = flickrPhoto.photo!!
                searchAdapter.updatePhoto(listFlickrphoto)
                for (i in listFlickrphoto) {
                    Log.i(TAG, "${i.url_s}")
                }
            }
        })
        viewModel.queryText.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                viewModel.makeQuery(queryText.value!!)
            }
        })
        error.observe(viewLifecycleOwner, androidx.lifecycle.Observer
        {
            it?.let {
                if (it) {
                    Snackbar.make(frameLayoutSearch, "Network failed", Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY") { viewModel.makeQuery(queryText.value!!) }
                        .show()
                }
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                if (it)
                    progressSearch.visibility = View.VISIBLE
                else
                    progressSearch.visibility = View.GONE
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}