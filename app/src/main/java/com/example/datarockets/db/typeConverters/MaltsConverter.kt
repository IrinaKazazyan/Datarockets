package com.example.datarockets.db.typeConverters

import androidx.room.TypeConverter
import com.example.datarockets.model.Hops
import com.example.datarockets.model.Malt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class MaltsConverter {

    @TypeConverter
    fun stringToListMalt(data: String?): List<Malt?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Malt?>?>() {}.type
        return Gson().fromJson<List<Malt?>>(data, listType)
    }

    @TypeConverter
    fun listMaltToString(someObjects: List<Malt?>?): String? {
        return Gson().toJson(someObjects)
    }

}
