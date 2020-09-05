package com.boristul.core.database

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

object Converters {

    @TypeConverter
    @JvmStatic
    fun dateTimeToLong(value: DateTime?): Long? = value?.millis

    @TypeConverter
    @JvmStatic
    fun longToDateTime(value: Long?): DateTime? = value?.let { DateTime(it, DateTimeZone.UTC) }
}
