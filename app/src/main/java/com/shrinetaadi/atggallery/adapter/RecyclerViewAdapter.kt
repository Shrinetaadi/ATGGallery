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
import com.shrinetaadi.atggallery.MainActivityViewModel
import com.shrinetaadi.atggallery.Photo
import com.shrinetaadi.atggallery.R
import com.shrinetaadi.atggallery.activity.BigPictureActivity

class RecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var itemList: List<Photo>? = null
    private lateinit var viewModel: MainActivityViewModel
    fun setData(it: List<Photo>) {
        itemList = it
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photo = itemList?.get(position)

        Glide.with(holder.imggallery).load(photo?.url_s).into(holder.imggallery)
        val arg = Bundle()
        arg.putString("url", photo?.url_s.toString())
        arg.putString("title",photo?.title.toString())
        holder.imggallery.setOnClickListener {
            val intent = Intent(context, BigPictureActivity::class.java)
            intent.putExtra("arr",arg)
            context.startActivity(intent)

            //My Fragment is not inflating in framelayout
            //it is showing some error of NavController not set
            /*Navigation.findNavController(it)
                .navigate(R.id.action_recyclerMainFragment_to_bigPictureFragment)
             */

        }

    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 1
    }

    fun setViewModel(viewModel: MainActivityViewModel) {
        this.viewModel = viewModel

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imggallery = view.findViewById<ImageView>(R.id.imgItem)
    }


}