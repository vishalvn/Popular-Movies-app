package com.example.popularmoviesthefinaledition.Utils

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.popularmoviesthefinaledition.Constants.CONTENT_AUTHORITY
import java.util.HashMap

class Provider : ContentProvider() {
    private val mOpenHelper: OpenHelper? = null
    internal lateinit var dataSource: DataSource

    override fun onCreate(): Boolean {
        dataSource = DataSource(context!!)

        dataSource.open()


        return true
    }


    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {


        val qb = SQLiteQueryBuilder()
        val dataSource = DataSource(context!!)

        qb.tables = CONTENT_AUTHORITY

        val retCursor: Cursor
        when (uriMatcher.match(uri)) {

            uriCode -> qb.setProjectionMap(
                values
            )
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        val c = qb.query(dataSource.openHelper.writableDatabase, projection, selection, selectionArgs, null, null, sortOrder)

        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }

    override fun getType(uri: Uri): String? {
        when (uriMatcher.match(uri)) {
            uriCode -> return Contract.FavouriteEntry.CONTENT_TYPE
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {

        var count = 0
        when (uriMatcher.match(uri)) {
            uriCode -> count = DataSource.database.delete(
                Contract.PATH_FAVOURITE, s, strings)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        var count = 0
        when (uriMatcher.match(uri)) {
            uriCode -> count = DataSource.database.update(
                Contract.PATH_FAVOURITE, contentValues, s, strings)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    companion object {


        internal val uriCode = 1
        internal val uriMatcher: UriMatcher
        private val values: HashMap<String, String>? = null

        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(
                CONTENT_AUTHORITY, "favourite",
                uriCode
            )
            uriMatcher.addURI(
                CONTENT_AUTHORITY, "favourite/*",
                uriCode
            )
        }
    }


}