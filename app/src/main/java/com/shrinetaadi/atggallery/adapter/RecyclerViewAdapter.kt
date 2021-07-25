package com.shrinetaadi.atggallery.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrinetaadi.atggallery.*
import com.shrinetaadi.atggallery.activity.BigPictureActivity
import kotlinx.android.synthetic.main.item_gallery.view.*

class RecyclerViewAdapter(val listFlickrPhotos: ArrayList<FlickrResult.FlickrPhoto.Photo>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    fun updatePhoto(newPhoto: List<FlickrResult.FlickrPhoto.Photo>, pageNo: Int) {
        if (pageNo == 1)
            listFlickrPhotos.clear()
        listFlickrPhotos.addAll(newPhoto)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listFlickrPhotos[position])
    }


    override fun getItemCount() = listFlickrPhotos.size


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imggallery = view.imgItem
        val context = view.context
        val progressDrawable = getProgressDrawable(view.context)
        fun bind(photo: FlickrResult.FlickrPhoto.Photo) {
            imggallery.loadImage(photo.url_s, progressDrawable)
            imggallery.setOnClickListener {
                val arg = Bundle()
                arg.putString("title", photo.title)
                arg.putString("url", photo.url_s)
                val intent = Intent(context, BigPictureActivity::class.java)
                intent.putExtra("arr", arg)
                context.startActivity(intent)

            }
        }


    }


}