package com.manish.articlelisting.db.article

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class MediaDataListTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String): ArrayList<MediaData>? {
        val listType = object : TypeToken<ArrayList<MediaData>>() {

        }.type
        return gson.fromJson<ArrayList<MediaData>>(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: ArrayList<MediaData>): String {
        return gson.toJson(someObjects)
    }
}