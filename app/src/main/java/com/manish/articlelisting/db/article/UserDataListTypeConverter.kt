package com.manish.articlelisting.db.article

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class UserDataListTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String): ArrayList<UserData>? {
        val listType = object : TypeToken<ArrayList<UserData>>() {

        }.type
        return gson.fromJson<ArrayList<UserData>>(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: ArrayList<UserData>): String {
        return gson.toJson(someObjects)
    }
}