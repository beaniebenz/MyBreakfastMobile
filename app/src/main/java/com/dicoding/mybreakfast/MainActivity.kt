package com.dicoding.mybreakfast

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import com.dicoding.mybreakfast.model.Meal


class MainActivity : AppCompatActivity(), ListBreakfastAdapter.OnItemClickListener {
    private lateinit var rvBreakfasts: RecyclerView
    private val list: ArrayList<Meal> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionbar = supportActionBar
        actionbar!!.title = "My Breakfast"

        rvBreakfasts = findViewById(R.id.rv_breakfasts)
        rvBreakfasts.setHasFixedSize(true)
        rvBreakfasts.layoutManager = LinearLayoutManager(this)

        fetchMeals()
    }

    private fun fetchMeals() {
        // Setting up OkHttpClient and Request for API call
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.themealdb.com/api/json/v1/1/search.php?f=a")
            .build()

        // Making an asynchronous API call
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle failure to fetch data
                Log.e("MainActivity", "Failed to fetch data", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Handle successful response
                    response.body?.let { responseBody ->
                        val json = JSONObject(responseBody.string())
                        val mealsArray = json.getJSONArray("meals")

                        // Parsing JSON response and populating the list
                        for (i in 0 until mealsArray.length()) {
                            val mealObject = mealsArray.getJSONObject(i)
                            val meal = Meal(
                                mealObject.getString("idMeal"),
                                mealObject.getString("strMeal"),
                                mealObject.getString("strMealThumb")
                            )
                            list.add(meal)
                        }

                        // Updating UI on the main thread
                        runOnUiThread {
                            val listCharAdapter = ListBreakfastAdapter(list, this@MainActivity)
                            rvBreakfasts.adapter = listCharAdapter
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("MainActivity", "Failed to fetch data: ${response.message}")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.about -> {
                val goAbout = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(goAbout)
            }
        }
    }

    override fun onItemClick(mealId: String) {
        // Handle item click here
        // For example, you can start a new activity with the meal ID
        val intent = Intent(this, MealDetailActivity::class.java)
        intent.putExtra("MEAL_ID", mealId)
        startActivity(intent)
    }
}
