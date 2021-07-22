package com.shrinetaadi.atggallery.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shrinetaadi.atggallery.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class BigPictureFragment : Fragment() {
    lateinit var url:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
         url = arguments?.getString("url").toString()
        val view = inflater.inflate(R.layout.fragment_big_picture, container, false)

        val imgBigPicture = view.findViewById<ImageView>(R.id.imgBigPicture)

        Glide.with(imgBigPicture).load(url).into(imgBigPicture)



        return view
    }

}
