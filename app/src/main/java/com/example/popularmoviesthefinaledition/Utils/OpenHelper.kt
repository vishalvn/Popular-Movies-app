package com.example.popularmoviesthefinaledition.Utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OpenHelper (context: Context) : SQLiteOpenHelper(context,
    DBName, null,
    DATABASE_VERSION
) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE)

    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_FAVOURITE")
        onCreate(sqLiteDatabase)
    }

    companion object {


        private val DBName = "Favourite.db"
        private val DATABASE_VERSION = 1

        val TABLE_FAVOURITE = "Favourite"
        val COLUMN_ID = "id"
        val COLUMN_MOVIE_ID = "movie_id"
        val COLUMN_MOVIE_POSTER = "movie_poster"
        val COLUMN_MOVIE_TITLE = "movie_title"
        val COLUMN_MOVIE_RELEASE_DATE = "movie_release_date"
        val COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average"
        val COLUMN_MOVIE_OVERVIEW = "movie_overview"

        val ALL_COLUMNS = arrayOf(
            COLUMN_MOVIE_ID,
            COLUMN_MOVIE_POSTER,
            COLUMN_MOVIE_TITLE,
            COLUMN_MOVIE_RELEASE_DATE,
            COLUMN_MOVIE_VOTE_AVERAGE,
            COLUMN_MOVIE_OVERVIEW
        )


        private val CREATE_TABLE = "CREATE TABLE " + TABLE_FAVOURITE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_MOVIE_ID + " INTEGER, " +
                COLUMN_MOVIE_POSTER + " TEXT, " +
                COLUMN_MOVIE_OVERVIEW + " TEXT, " +
                COLUMN_MOVIE_RELEASE_DATE + " TEXT, " +
                COLUMN_MOVIE_TITLE + " TEXT, " +
                COLUMN_MOVIE_VOTE_AVERAGE + " TEXT " +
                ")"
    }
}