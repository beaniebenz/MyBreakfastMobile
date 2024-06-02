package com.dicoding.mybreakfast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException
import com.dicoding.mybreakfast.R
import android.widget.ImageView



class MealDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_detail)

        val mealId = intent.getStringExtra("MEAL_ID")
        if (mealId != null) {
            fetchMealDetail(mealId)
        } else {
        }


    }

    private fun fetchMealDetail(mealId: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId")
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    withContext(Dispatchers.Main) {
                        val mealImage = findViewById<ImageView>(R.id.meal_image)
                        Glide.with(this@MealDetailActivity)
                            .load("https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg")
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(mealImage)
                    }
                } else {

                }
            } catch (e: IOException) {
                e.printStackTrace()

            }
        }
    }
}
