package com.shrinetaadi.atggallery.retro

import com.shrinetaadi.atggallery.FlickrResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInstance {

    @GET("rest/?method=flickr.photos.getRecent&per_page=20&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
     fun getRecentPhotos(
        @Query("page") page: Int
    ): Call<FlickrResult>

    @GET("rest/?method=flickr.photos.search&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
     fun getSearchResult(
        @Query("text") text: String
    ): Call<FlickrResult>

}