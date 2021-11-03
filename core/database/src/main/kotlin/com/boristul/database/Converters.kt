package com.boristul.database

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate

object Converters {

    @TypeConverter
    @JvmStatic
    fun dateTimeToLong(value: DateTime?): Long? = value?.millis

    @TypeConverter
    @JvmStatic
    fun longToDateTime(value: Long?): DateTime? = value?.let { DateTime(it, DateTimeZone.UTC) }

    @TypeConverter
    @JvmStatic
    fun localDateToString(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    @JvmStatic
    fun stringToLocalDate(value: String?): LocalDate = LocalDate(value)
}
