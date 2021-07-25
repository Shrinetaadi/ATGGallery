package com.shrinetaadi.atggallery.retro

import androidx.lifecycle.MutableLiveData
import com.shrinetaadi.atggallery.FlickrResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetroRepository @Inject constructor(private val retroInstance: RetroServiceInstance) {

     fun makeApiCall(page: Int, liveDatList: MutableLiveData<FlickrResult>,isLoading:MutableLiveData<Boolean>,error:MutableLiveData<Boolean> ){
    val call: Call<FlickrResult> = retroInstance.getRecentPhotos(page)
        call?.enqueue(object : Callback<FlickrResult>{
            override fun onResponse(call: Call<FlickrResult>, response: Response<FlickrResult>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    liveDatList.postValue(response.body())
                }else{
                    error.value=true
                }
            }

            override fun onFailure(call: Call<FlickrResult>, t: Throwable) {
                print("ERROR")
                isLoading.value=false
                error.value=true
                liveDatList.postValue(null)
            }

        })
    }
    fun makeSearchCall(text: String, liveDatList: MutableLiveData<FlickrResult>,isLoading:MutableLiveData<Boolean>,error:MutableLiveData<Boolean> ){
    val call: Call<FlickrResult> = retroInstance.getSearchResult(text)
        call?.enqueue(object : Callback<FlickrResult>{
            override fun onResponse(call: Call<FlickrResult>, response: Response<FlickrResult>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    liveDatList.postValue(response.body())
                }else{
                    error.value=true
                }
            }

            override fun onFailure(call: Call<FlickrResult>, t: Throwable) {
                print("ERROR")
                isLoading.value=false
                error.value=true
                liveDatList.postValue(null)
            }

        })
    }

}