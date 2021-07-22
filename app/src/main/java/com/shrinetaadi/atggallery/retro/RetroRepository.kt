package com.shrinetaadi.atggallery.retro

import androidx.lifecycle.MutableLiveData
import com.shrinetaadi.atggallery.FlickrResult
import com.shrinetaadi.atggallery.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetroRepository @Inject constructor(private val retroInstance: RetroServiceInstance) {

    fun makeApiCall(query: String, liveDatList: MutableLiveData<List<Photo>>){
    val call: Call<FlickrResult> = retroInstance.getPhotos()
        call?.enqueue(object : Callback<FlickrResult>{
            override fun onResponse(call: Call<FlickrResult>, response: Response<FlickrResult>) {
                print(response)
                liveDatList.postValue(response.body()?.photos?.photo    )
            }

            override fun onFailure(call: Call<FlickrResult>, t: Throwable) {
                print("ERROR")
                liveDatList.postValue(null)
            }

        })
    }

}