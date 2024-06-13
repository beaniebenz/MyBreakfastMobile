package com.dicoding.mybreakfast.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.mybreakfast.model.Meal

class MealDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "meals.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_FAVORITE_MEALS = "favorite_meals"
        const val COLUMN_ID = "id"
        const val COLUMN_MEAL_ID = "meal_id"
        const val COLUMN_MEAL_NAME = "meal_name"
        const val COLUMN_MEAL_THUMB = "meal_thumb"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_FAVORITE_MEALS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_MEAL_ID TEXT,"
                + "$COLUMN_MEAL_NAME TEXT,"
                + "$COLUMN_MEAL_THUMB TEXT)")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITE_MEALS")
        onCreate(db)
    }

    fun getAllFavoriteMeals(): List<Meal> {
        val db = readableDatabase
        val cursor: Cursor = db.query("favorite_meals", null, null, null, null, null, null)
        val favoriteMeals = mutableListOf<Meal>()
        while (cursor.moveToNext()) {
            val mealId = cursor.getString(cursor.getColumnIndexOrThrow(MealDatabaseHelper.COLUMN_MEAL_ID))
            val mealName = cursor.getString(cursor.getColumnIndexOrThrow(MealDatabaseHelper.COLUMN_MEAL_NAME))
            val mealThumb = cursor.getString(cursor.getColumnIndexOrThrow(MealDatabaseHelper.COLUMN_MEAL_THUMB))
            favoriteMeals.add(Meal(mealId, mealName, mealThumb, true))
        }
        cursor.close()
        return favoriteMeals
    }
}
