package com.example.datarockets.model

import androidx.annotation.NonNull
import androidx.room.TypeConverters
import com.example.datarockets.db.typeConverters.HopsConverter
import com.example.datarockets.db.typeConverters.MaltsConverter


data class Ingredients(

    @TypeConverters(HopsConverter::class)
    @NonNull
    val hops: List<Hops>,

    @TypeConverters(MaltsConverter::class)
    @NonNull
    val malt: List<Malt>,

    val yeast: String
)