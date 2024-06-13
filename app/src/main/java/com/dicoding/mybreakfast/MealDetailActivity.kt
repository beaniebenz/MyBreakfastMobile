package com.dicoding.mybreakfast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.widget.ImageView
import android.widget.TextView

class MealDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_detail)

        val mealId = intent.getStringExtra("MEAL_ID")
        if (mealId != null) {
            fetchMealDetail(mealId)
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
                    responseBody?.let {
                        val json = JSONObject(it)
                        val mealArray = json.getJSONArray("meals")
                        val mealObject = mealArray.getJSONObject(0)

                        val mealName = mealObject.getString("strMeal")
                        val mealCategory = mealObject.getString("strCategory")
                        val mealInstructions = mealObject.getString("strInstructions")
                        val mealImageUrl = mealObject.getString("strMealThumb")

                        withContext(Dispatchers.Main) {
                            val mealImage = findViewById<ImageView>(R.id.meal_image)
                            val mealNameTextView = findViewById<TextView>(R.id.meal_name)
                            val mealCategoryTextView = findViewById<TextView>(R.id.meal_category)
                            val mealInstructionsTextView = findViewById<TextView>(R.id.meal_instructions)

                            mealNameTextView.text = mealName
                            mealCategoryTextView.text = mealCategory
                            mealInstructionsTextView.text = mealInstructions

                            Glide.with(this@MealDetailActivity)
                                .load(mealImageUrl)
                                .placeholder(R.drawable.placeholder_image)
                                .error(R.drawable.error_image)
                                .into(mealImage)
                        }
                    }
                } else {
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
