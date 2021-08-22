package com.example.datarockets.db.typeConverters

import android.text.TextUtils
import androidx.room.TypeConverter
import com.example.datarockets.model.Ingredients
import com.google.gson.Gson
import org.json.JSONObject


public class IngredientsConverter {

    @TypeConverter
    fun stringToIngredientItem(string: String): Ingredients? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Ingredients::class.java)
    }

    @TypeConverter
    fun ingredientItemToString(outboxItem: Ingredients): String {
        return Gson().toJson(outboxItem)
    }
}

