package com.shrinetaadi.atggallery.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shrinetaadi.atggallery.FlickrResult
import com.shrinetaadi.atggallery.retro.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: RetroRepository)  : ViewModel() {
    private lateinit var flickrResult: MutableLiveData<FlickrResult>
    lateinit var queryText: MutableLiveData<String>
    lateinit var isLoading: MutableLiveData<Boolean>
    lateinit var error: MutableLiveData<Boolean>
    private var tempText = ""

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }


    fun getFlickrResponse(): MutableLiveData<FlickrResult> {
        if (!::flickrResult.isInitialized) {
            flickrResult = MutableLiveData()
            queryText = MutableLiveData()
            isLoading = MutableLiveData()
            isLoading.value = false
            error = MutableLiveData()
            error.value = false
        }
        return flickrResult
    }
    fun makeQuery(query: String) {
        if ((!isLoading.value!!) && (query != "") && (query != tempText)) {
            tempText = query
            fetchFlickrResponse(query)
        }
    }

    private fun fetchFlickrResponse(query: String) {
        Log.i(TAG, "Query text: $query")
        isLoading.value = true
        error.value = false
        repository.makeSearchCall(query,flickrResult,isLoading,error)
    }


}