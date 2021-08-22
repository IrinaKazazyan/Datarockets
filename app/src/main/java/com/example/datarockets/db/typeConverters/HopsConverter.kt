package com.example.datarockets.db.typeConverters

import androidx.room.TypeConverter
import com.example.datarockets.model.Hops
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class HopsConverter {
    @TypeConverter
    fun stringToListHops(data: String?): List<Hops?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Hops?>?>() {}.type
        return Gson().fromJson<List<Hops?>>(data, listType)
    }

    @TypeConverter
    fun listHopsToString(someObjects: List<Hops?>?): String? {
        return Gson().toJson(someObjects)
    }

}