package com.shrinetaadi.atggallery

data class FlickrResult(val photos: FlickrPhoto)
data class FlickrPhoto (
    val page: Int ,
    val pages: Int,
    val perPage: Int,
    val total: Int,
    val photo: List<Photo>,
    val stat: String
)
data class Photo(
    val id: String,
    val title: String,
    val url_s: String,
    val height: Int,
    val width: Int
)