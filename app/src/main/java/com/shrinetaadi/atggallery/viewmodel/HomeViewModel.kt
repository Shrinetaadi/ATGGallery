package com.shrinetaadi.atggallery.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shrinetaadi.atggallery.FlickrResult
import com.shrinetaadi.atggallery.fragment.RecyclerMainFragment
import com.shrinetaadi.atggallery.retro.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RetroRepository)  :ViewModel() {
    private lateinit var liveDataList: MutableLiveData<FlickrResult>
    lateinit var isLoading: MutableLiveData<Boolean>
    lateinit var error: MutableLiveData<Boolean>
    lateinit var PAGE_NO: MutableLiveData<Int>


     fun getFlickrResponse(): MutableLiveData<FlickrResult> {
        if (!::liveDataList.isInitialized) {
            liveDataList = MutableLiveData()
            isLoading = MutableLiveData()
            isLoading.value = false
            error = MutableLiveData()
            PAGE_NO = MutableLiveData()
            PAGE_NO.value = 1
            loadlistofData(PAGE_NO.value!!)
        }

        return liveDataList
    }
    fun refresh() {
        PAGE_NO.value = 1
        loadlistofData(PAGE_NO.value!!)
    }

    fun loadNextPage() {
        if (!(isLoading.value!!)) {
            PAGE_NO.value = PAGE_NO.value!! + 1
            loadlistofData(PAGE_NO.value!!)
        }
    }

     fun loadlistofData(page: Int){
         Log.i(RecyclerMainFragment::class.java.simpleName, "Page No: $page")
         isLoading.value = true
         error.value = false
         repository.makeApiCall(page,liveDataList,isLoading,error)

    }
}