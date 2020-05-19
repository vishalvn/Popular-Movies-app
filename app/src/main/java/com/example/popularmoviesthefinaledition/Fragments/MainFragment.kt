package com.example.popularmoviesthefinaledition.Fragments

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.popularmoviesthefinaledition.*
import com.example.popularmoviesthefinaledition.Activities.DetailActivity
import com.example.popularmoviesthefinaledition.Constants.ERROR_MESSAGE
import com.example.popularmoviesthefinaledition.Constants.FAVOURITE
import com.example.popularmoviesthefinaledition.Constants.POSTER_PATH
import com.example.popularmoviesthefinaledition.Data.Movie
import com.example.popularmoviesthefinaledition.Data.MovieRepository
import com.example.popularmoviesthefinaledition.Utils.DataSource
import com.example.popularmoviesthefinaledition.Utils.MovieGridAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainFragment() : Fragment() {
    private lateinit var gridView: GridView
    private lateinit var rootView: View
    private lateinit var popularMovies: ArrayList<Movie>
    private lateinit var poster: String
    private lateinit var queue: RequestQueue
    private val POP_DESC = "popularity.desc"
    private var VOTE_DSEC = "vote_average.desc"
    private var FAV = "favourite"
    private var API_KEY: String? = null
    private var TAG = "APIDebug"
    private var sort_by: String = POP_DESC
    lateinit var adapter: MovieGridAdapter
    lateinit var sp: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)

        API_KEY = activity?.resources?.getString(R.string.api_key)

        gridView = rootView.findViewById<View>(R.id.gridView) as GridView
        queue = Volley.newRequestQueue(activity)
        sp = PreferenceManager.getDefaultSharedPreferences(activity!!.applicationContext)

        sort_by = sp.getString(sort_by, API_KEY)
        if (sort_by == FAV)
            getFavouriteFromDB()
        else {
            jsonObjectRequest(sort_by)
        }

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
            val layout = view as LinearLayout
            val clickedItem = layout.findViewById<View>(R.id.imageView) as ImageView
            clickedMovie = clickedItem.tag as Movie

            if (activity!!.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.container_detail,
                    DetailFragment()
                )
                transaction.commit()
            } else {
                val intent = Intent(activity, DetailActivity::class.java)
                startActivity(intent)
            }
        }
        return rootView
    }

    private fun jsonObjectRequest(sort_by: String) {

        // http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=0659fc41591cd95ccdbe9343772411b5
        popularMovies = ArrayList()
        val moviesRepository = MovieRepository()
        val disposable = CompositeDisposable()
        disposable.addAll(
            moviesRepository.getMovies(POP_DESC, API_KEY!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    it.movieList.forEach { movie ->
                        movie.poster_path = POSTER_PATH + movie.poster_path
                        popularMovies.add(movie)
                    }
                    adapter = MovieGridAdapter(
                        activity!!,
                        popularMovies
                    )
                    gridView.adapter = adapter

                }, {
                    Toast.makeText(
                        activity,
                        ERROR_MESSAGE,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, it.message)
                })
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if (id == R.id.action_most_popular) {
            sp.edit().putString("sort_by", POP_DESC).apply()
            jsonObjectRequest(POP_DESC)
            return true
        }
        if (id == R.id.action_highest_rated) {
            sp.edit().putString("sort_by", VOTE_DSEC).apply()
            jsonObjectRequest(VOTE_DSEC)
            return true
        }
//        if (id == R.id.action_favourite) {
//            sp.edit().putString("sort_by", "favourite").apply()
//            getFavouriteFromDB()
//        }
        return super.onOptionsItemSelected(item)
    }

    private fun getFavouriteFromDB() {
        val dataSource = DataSource(activity!!)
        dataSource.open()
        val favoriteMovies = dataSource.favouriteMovies
        dataSource.close()
        if (favoriteMovies.size == 0) {
            Toast.makeText(
                activity,
                FAVOURITE,
                Toast.LENGTH_LONG
            ).show()
        } else {
            val adapter = MovieGridAdapter(
                activity!!,
                favoriteMovies
            )
            gridView.adapter = adapter
        }
    }

    companion object {
        lateinit var clickedMovie: Movie
    }
}
