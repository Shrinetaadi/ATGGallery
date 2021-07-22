package com.shrinetaadi.atggallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shrinetaadi.atggallery.retro.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: RetroRepository)  :ViewModel() {
     lateinit var  liveDataList: MutableLiveData<List<Photo>>
     init {
         liveDataList =  MutableLiveData()
     }

    fun getLiveDataObserver(): MutableLiveData<List<Photo>>{
        return liveDataList
    }
    fun loadlistofData(){
        repository.makeApiCall("",liveDataList)

    }
}