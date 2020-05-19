package com.example.popularmoviesthefinaledition.Utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.popularmoviesthefinaledition.Data.Movie
import com.example.popularmoviesthefinaledition.R
import java.util.ArrayList

class MovieGridAdapter(
    internal var context: Activity, private var movies: ArrayList<Movie>) : ArrayAdapter<Any>(context,
    R.layout.grid_view
) {

    override fun getCount(): Int {
        return movies.size
    }

    override fun getItem(position: Int): Any? {
        return movies[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater

        val rowView = inflater.inflate(R.layout.grid_view, null, true)

        val imageView = rowView.findViewById<View>(R.id.imageView) as ImageView

        val movie = getItem(position) as Movie

        imageView.tag = movie

        val url = movie.poster_path

        Log.d("GlideDebug", "$position $url")
        Glide.with(context).load(url).apply( RequestOptions().override(500, 500)).centerInside().into(imageView)
        return rowView
    }
}