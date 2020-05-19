package com.example.popularmoviesthefinaledition.Utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.popularmoviesthefinaledition.Data.Movie
import java.util.ArrayList

class DataSource (context: Context) {
    internal var openHelper: SQLiteOpenHelper =
        OpenHelper(context)

    val favouriteMovies: ArrayList<Movie>
        get() {
            val movies = ArrayList<Movie>()

            val cursor = database.query(
                OpenHelper.TABLE_FAVOURITE,
                OpenHelper.ALL_COLUMNS, null, null, null, null, null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val movie = Movie(
                        cursor.getInt(
                            cursor
                                .getColumnIndex(OpenHelper.COLUMN_MOVIE_ID)
                        )
                        , cursor.getString(
                            cursor
                                .getColumnIndex(OpenHelper.COLUMN_MOVIE_POSTER)
                        )
                        , cursor.getString(
                            cursor
                                .getColumnIndex(OpenHelper.COLUMN_MOVIE_RELEASE_DATE)
                        )
                        , cursor.getString(
                            cursor
                                .getColumnIndex(OpenHelper.COLUMN_MOVIE_OVERVIEW)
                        )
                        , cursor.getString(
                            cursor
                                .getColumnIndex(OpenHelper.COLUMN_MOVIE_TITLE)
                        )
                        , cursor.getString(
                            cursor
                                .getColumnIndex(OpenHelper.COLUMN_MOVIE_VOTE_AVERAGE)
                        )

                    )


                    movies.add(movie)
                }
            }
            return movies
        }

    fun open() {

        database = openHelper.writableDatabase
    }

    fun close() {
        openHelper.close()
    }


    fun create(movie: Movie) {

        val contentValues = ContentValues()

        contentValues.put(OpenHelper.COLUMN_MOVIE_ID, movie.id)
        contentValues.put(OpenHelper.COLUMN_MOVIE_POSTER, movie.poster_path)
        contentValues.put(OpenHelper.COLUMN_MOVIE_OVERVIEW, movie.overview)
        contentValues.put(OpenHelper.COLUMN_MOVIE_RELEASE_DATE, movie.release_date)
        contentValues.put(OpenHelper.COLUMN_MOVIE_TITLE, movie.title)
        contentValues.put(OpenHelper.COLUMN_MOVIE_VOTE_AVERAGE, movie.vote_average)

        val values = ContentValues()
        values.put(Contract.FavouriteEntry.COLUMN_MOVIE_ID, "1")


        database.insert(OpenHelper.TABLE_FAVOURITE, null, contentValues)

    }

    fun delete(movieID: Int) {
        val whereClause = OpenHelper.COLUMN_MOVIE_ID + " = " + movieID

        database.delete(OpenHelper.TABLE_FAVOURITE, whereClause, null)
    }

    fun isFavouriteMovie(movieId: Int): Boolean {

        val selection = OpenHelper.COLUMN_MOVIE_ID + "=" + movieId
        val cursor = database.query(
            OpenHelper.TABLE_FAVOURITE,
            OpenHelper.ALL_COLUMNS, selection, null, null, null, null)
        return cursor.count > 0
    }

    companion object {
        lateinit var database: SQLiteDatabase
    }
}