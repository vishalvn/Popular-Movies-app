package com.example.popularmoviesthefinaledition.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.popularmoviesthefinaledition.Utils.DataSource
import com.example.popularmoviesthefinaledition.Data.MovieRepository
import com.example.popularmoviesthefinaledition.R
import com.example.popularmoviesthefinaledition.Constants.URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList


class DetailFragment : Fragment() {

    private var clickedMovieId: Int = 0
    private lateinit var reviewTV: TextView
    private lateinit var trailersLv: ListView
    private lateinit var trailersNames: ArrayList<String>
    private var trailersKeys: ArrayList<String>? = null
    private var mShareActionProvider: ShareActionProvider? = null
    private lateinit var shareIntent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.detail_fragment, null, false)
        val favButton = rootView.findViewById<View>(R.id.FavtoggleButton) as ToggleButton
        clickedMovieId = MainFragment.clickedMovie.id
        activity!!.title = MainFragment.clickedMovie.title

        val moviePoster = rootView.findViewById<View>(R.id.movie_poster) as ImageView
        Glide.with(activity!!).load(MainFragment.clickedMovie.poster_path)
            .apply(RequestOptions().override(300, 300)).centerInside().into(moviePoster)
        Log.d("DetailDebug", MainFragment.clickedMovie.poster_path)
        val movieDate = rootView.findViewById<View>(R.id.movie_date) as TextView
        movieDate.text = MainFragment.clickedMovie.release_date

        val movieVoteAverage = rootView.findViewById<View>(R.id.movie_vote_average) as TextView
        movieVoteAverage.text = MainFragment.clickedMovie.vote_average

        val movieOverView = rootView.findViewById<View>(R.id.movie_overview) as TextView
        movieOverView.text = MainFragment.clickedMovie.overview
        favButton.setOnCheckedChangeListener { _, b ->
            val favouriteDataSource =
                DataSource(activity!!)
            favouriteDataSource.open()

            if (favButton.text == "MARK AS A FAVOURITE") {
                favouriteDataSource.create(MainFragment.clickedMovie)
                Toast.makeText(activity, "Marked as FAVOURITE", Toast.LENGTH_LONG).show()
            } else {
                favouriteDataSource.delete(MainFragment.clickedMovie.id)
                Toast.makeText(activity, "Marked as not favourite", Toast.LENGTH_LONG).show()
            }

            favouriteDataSource.close()
        }

        trailersLv = rootView.findViewById<View>(R.id.trailers_list_view) as ListView

        trailersLv.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }

        trailersNames = ArrayList()
        trailersKeys = ArrayList()

        val api_key = activity!!.resources.getString(R.string.api_key)
        val moviesRepository = MovieRepository()
        moviesRepository.getTrailers(clickedMovieId, api_key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.trailerList.forEach { trailer ->
                    trailersNames.add(trailer.name)
                    trailersKeys!!.add(trailer.key)
                }
                val adapter = CustomListAdapter(activity!!, trailersNames)
                trailersLv.adapter = adapter
            }, {
                Toast.makeText(
                    activity,
                    "Sorry some thing happened while retrieving trailers, please try again",
                    Toast.LENGTH_LONG
                ).show()
            })

        trailersLv.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val clickedKey = trailersKeys!![i]
                val url = URL + clickedKey
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

        reviewTV = rootView.findViewById<View>(R.id.reviews_text_view) as TextView

        var reviewStr = ""
        moviesRepository.getReviews(clickedMovieId, api_key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.reviewsList.forEach { review ->
                    reviewStr += review.author + ": " + System.getProperty("line.separator") + System.getProperty(
                        "line.separator"
                    ) + review.content + System.getProperty("line.separator") + System.getProperty("line.separator")
                    Log.d("DetailDebug", "$review.author: /r/n$review.content")
                }

                reviewTV.setText(reviewStr)

            }, {

            })

        val favouriteDataSource =
            DataSource(activity!!)
        favouriteDataSource.open()
        if (favouriteDataSource.isFavouriteMovie(clickedMovieId)) {
            favButton.text = "FAVOURITE"
        }
        return rootView
    }

    inner class CustomListAdapter(//ArrayList<String> images ;
        internal var context: Activity, private var stringArrayList: ArrayList<String>
    ) : ArrayAdapter<Any>(context, R.layout.grid_view) {

        override fun getCount(): Int {
            return stringArrayList.size
        }

        override fun getItem(position: Int): Any? {
            return stringArrayList[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = context.layoutInflater
            val rowView = inflater.inflate(R.layout.list_view, null, true)
            val tv = rowView.findViewById<View>(R.id.list_item_text_view) as TextView
            tv.text = stringArrayList[position]
            return rowView
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_share) {
            mShareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider
            if (trailersKeys != null && trailersKeys!!.size > 0) {
                val url = URL + trailersKeys!![0]
                shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_STREAM, url)
                }
            }
            if (mShareActionProvider != null) {
                mShareActionProvider!!.setShareIntent(shareIntent)
            }
        return super.onOptionsItemSelected(item)
    }
}


