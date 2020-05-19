package com.example.popularmoviesthefinaledition

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.popularmoviesthefinaledition.Data.Movie

@Database(entities = [Movie::class],version = 1)

abstract class AppDataBase:RoomDatabase() {
    abstract fun movieDao():MovieDao

}