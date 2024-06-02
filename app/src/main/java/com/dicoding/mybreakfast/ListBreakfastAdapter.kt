package com.dicoding.mybreakfast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybreakfast.R
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
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvName: TextView = itemView.findViewById(R.id.menu_name)
        var imgPhoto: ImageView = itemView.findViewById(R.id.menu_pic)

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
