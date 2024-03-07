package com.srmanager.database.util

import androidx.room.TypeConverter
import java.text.DecimalFormat


class TypeConverterHelper {
    private val decimalFormat: DecimalFormat = DecimalFormat("#.##")

    @TypeConverter
    fun toDouble(value: String?): Double {
        return value?.toDouble() ?: 0.0 // Or any default value you prefer
    }

    @TypeConverter
    fun fromDouble(value: Double): String {
        return decimalFormat.format(value)
    }
}