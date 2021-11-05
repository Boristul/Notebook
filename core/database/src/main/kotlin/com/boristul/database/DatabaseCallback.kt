package com.boristul.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DatabaseCallback : RoomDatabase.Callback() {

    @Suppress("MaxLineLength")
    override fun onCreate(db: SupportSQLiteDatabase) {
        db.execSQL("""INSERT INTO tags(name, _id) VALUES ("Birthdays", 1)""")
        db.execSQL("""INSERT INTO tags(name, _id) VALUES ("Important", 2)""")
    }
}
