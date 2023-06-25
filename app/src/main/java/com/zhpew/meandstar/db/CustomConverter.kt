package com.zhpew.meandstar.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CustomConverter {
    @TypeConverter
    fun fromString(value:String):ArrayList<String>{
        return Gson().fromJson(value, ArrayList<String>().javaClass.genericSuperclass)
    }

    @TypeConverter
    fun fromArrayList(list:ArrayList<String>):String{
        return Gson().toJson(list)
    }
}