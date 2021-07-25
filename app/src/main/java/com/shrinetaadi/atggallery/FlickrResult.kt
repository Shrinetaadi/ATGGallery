package com.shrinetaadi.atggallery

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlickrResult(val photos: FlickrPhoto) : Parcelable {
    @Parcelize
    data class FlickrPhoto(
        val page: Int,
        val pages: Int,
        val perPage: Int,
        val total: Int,
        val photo: List<Photo>,
        val stat: String
    ) : Parcelable {

        @Parcelize
        data class Photo(
            val id: String,
            val title: String,
            val url_s: String,
        ) : Parcelable
    }
}