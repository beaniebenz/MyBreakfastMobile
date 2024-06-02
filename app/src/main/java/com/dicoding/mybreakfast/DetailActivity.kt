package com.dicoding.mybreakfast

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESC = "extra_intro"
        const val EXTRA_BAHAN = "extra_birth"
        const val EXTRA_STEP = "extra_weapon"
        const val EXTRA_PIC = "extra_pic"
        const val EXTRA_LINK = "extra_link"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvMenuName: TextView = findViewById(R.id.menu_name)
        val tvMenuDesc: TextView = findViewById(R.id.menu_desc)
        val tvMenuBahan: TextView = findViewById(R.id.menu_bahan)
        val tvMenuStep: TextView = findViewById(R.id.menu_step)
        val imgMenuPic: ImageView = findViewById(R.id.menu_pic)
        val shareButton: Button = findViewById(R.id.share_button)
        var fav = false

        val menuName = intent.getStringExtra(EXTRA_NAME)
        val menuDesc = intent.getStringExtra(EXTRA_DESC)
        val menuBahan = intent.getStringExtra(EXTRA_BAHAN)
        val menuStep = intent.getStringExtra(EXTRA_STEP)
        val menuPic = intent.getIntExtra(EXTRA_PIC, 0)
        val menuLink = intent.getStringExtra(EXTRA_LINK)

        tvMenuName.text = menuName
        tvMenuDesc.text = menuDesc
        tvMenuBahan.text = menuBahan
        tvMenuStep.text = menuStep
        Glide.with(this)
            .load(menuPic)
            .apply(RequestOptions())
            .into(imgMenuPic)

        shareButton.setOnClickListener {
            val goShare = Intent()
            goShare.action = Intent.ACTION_SEND
            goShare.putExtra(Intent.EXTRA_TEXT, "Coba Cek Secara Lengkap: $menuLink")
            goShare.type = "text/plain"
            startActivity(Intent.createChooser(goShare, "Share To:"))
        }
    }
}