package com.shrinetaadi.atggallery.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.shrinetaadi.atggallery.R

class BigPictureActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var imageView: ImageView
    lateinit var url: String
    lateinit var title:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bigpicture)
        toolbar = findViewById(R.id.toolbarBigPicture)
        imageView=findViewById(R.id.imgPicture)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (intent!=null){
            var arg: Bundle? = intent.getBundleExtra("arr")
            url = arg?.getString("url","defaulturl").toString()
            title = arg?.getString("title","Picture").toString()
        }
        supportActionBar?.title=title
        Glide.with(imageView).load(url).into(imageView)
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
