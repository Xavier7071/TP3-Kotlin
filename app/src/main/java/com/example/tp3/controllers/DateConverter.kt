package com.example.tp3.controllers

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toDate(timestamp: Long?): Date? {
            return when (timestamp) {
                null -> null
                else -> Date(timestamp)
            }
        }

        @TypeConverter
        @JvmStatic
        fun fromDate(date: Date?): Long? {
            return date?.time
        }
    }
}
