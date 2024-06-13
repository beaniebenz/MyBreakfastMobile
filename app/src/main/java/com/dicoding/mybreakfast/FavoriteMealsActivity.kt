package com.dicoding.mybreakfast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mybreakfast.database.MealDatabaseHelper
import com.dicoding.mybreakfast.model.Meal

class FavoriteMealsActivity : AppCompatActivity(), ListBreakfastAdapter.OnItemClickListener {

    private lateinit var rvFavoriteMeals: RecyclerView
    private lateinit var db: MealDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_meals)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite Meals"

        rvFavoriteMeals = findViewById(R.id.rv_favorite_meals)
        rvFavoriteMeals.setHasFixedSize(true)
        rvFavoriteMeals.layoutManager = LinearLayoutManager(this)

        db = MealDatabaseHelper(this)
        val favoriteMeals = db.getAllFavoriteMeals()
        val listCharAdapter = ListBreakfastAdapter(ArrayList(favoriteMeals), this)
        rvFavoriteMeals.adapter = listCharAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(mealId: String) {
        // Implement navigation to meal detail if needed
    }
}
