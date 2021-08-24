package com.example.datarockets.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.datarockets.constants.BEER_TABLE_NAME
import com.example.datarockets.db.typeConverters.IngredientsConverter
import java.io.Serializable

@Entity(tableName = BEER_TABLE_NAME)
data class BeersListItem(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "abv")
    val abv: Double?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "ibu")
    val ibu: Double?,

    @ColumnInfo(name = "image_url")
    val image_url: String?,

    @ColumnInfo(name = "ingredients")
    @TypeConverters(IngredientsConverter::class)
    @NonNull
    val ingredients: Ingredients?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "tagline")
    val tagline: String?,
) : Serializable