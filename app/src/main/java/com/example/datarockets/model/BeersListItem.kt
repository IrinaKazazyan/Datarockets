package com.example.datarockets.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.datarockets.constants.BEER_TABLE_NAME
import com.example.datarockets.db.typeConverters.IngredientsConverter
import java.io.Serializable

@Entity(tableName = BEER_TABLE_NAME)
data class BeersListItem(
    @PrimaryKey
    val id: Int,
    val abv: Double,
    val description: String,
    val ibu: Double,
    val image_url: String,

    @TypeConverters(IngredientsConverter::class)
    @NonNull
    val ingredients: Ingredients,

    val name: String,
    val tagline: String,
) : Serializable