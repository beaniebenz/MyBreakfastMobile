package com.dicoding.mybreakfast

import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybreakfast.R
import com.dicoding.mybreakfast.database.MealDatabaseHelper
import com.dicoding.mybreakfast.model.Meal

class ListBreakfastAdapter(private val list: ArrayList<Meal>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ListBreakfastAdapter.ListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(mealId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_breakfast, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val meal = list[position]
        holder.tvName.text = meal.strMeal
        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .into(holder.imgPhoto)

        // Update favorite button state
        holder.favoriteButton.setImageResource(
            if (meal.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )

        holder.favoriteButton.setOnClickListener {
            meal.isFavorite = !meal.isFavorite
            updateFavoriteStatus(holder.itemView.context, meal)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun updateFavoriteStatus(context: Context, meal: Meal) {
        val dbHelper = MealDatabaseHelper(context)
        val db = dbHelper.writableDatabase

        if (meal.isFavorite) {
            // Add to favorites
            val values = ContentValues().apply {
                put(MealDatabaseHelper.COLUMN_MEAL_ID, meal.idMeal)
                put(MealDatabaseHelper.COLUMN_MEAL_NAME, meal.strMeal)
                put(MealDatabaseHelper.COLUMN_MEAL_THUMB, meal.strMealThumb)
            }
            db.insert(MealDatabaseHelper.TABLE_FAVORITE_MEALS, null, values)
        } else {
            // Remove from favorites
            db.delete(
                MealDatabaseHelper.TABLE_FAVORITE_MEALS,
                "${MealDatabaseHelper.COLUMN_MEAL_ID} = ?",
                arrayOf(meal.idMeal)
            )
        }
        db.close()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvName: TextView = itemView.findViewById(R.id.menu_name)
        var imgPhoto: ImageView = itemView.findViewById(R.id.menu_pic)
        var favoriteButton: ImageView = itemView.findViewById(R.id.favorite_button)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val mealId = list[position].idMeal
                listener.onItemClick(mealId)
            }
        }
    }
}
