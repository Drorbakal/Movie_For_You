package com.example.myapplication.Repository.FireBase

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.AuthRep
import com.squareup.picasso.Picasso
import java.util.Locale

class MovieAdapter(
    private val movieList: ArrayList<Movie>,
    private val authRep: AuthRep,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movies_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie: Movie = movieList[position]
        val currentLocale = Locale.getDefault()

        if (currentLocale.language == "he" && currentLocale.country == "IL") {
            holder.title.text = movie.hebrew_title
        } else {
            holder.title.text = movie.title
        }
        holder.rating.text = movie.rating

        Log.d("MovieAdapter", "Poster URL: ${movie.poster}")

        // Load the movie poster using Picasso
        Picasso.get()
            .load(movie.poster)
            .error(R.drawable.notfound)
            .into(holder.poster)

        holder.itemView.setOnClickListener { view ->
            val bundle = bundleOf(
                "title" to movie.title,
                "title(hebrew)" to movie.hebrew_title,
                "details" to movie.details,
                "details(hebrew)" to movie.hebrew_details,
                "poster" to movie.poster,
                "trailer" to movie.trailer,
                "img1" to movie.img1,
                "img2" to movie.img2,
                "img3" to movie.img3,
                "img4" to movie.img4,
                "rating" to movie.rating
            )
            view.findNavController().navigate(R.id.action_homePage2_to_moviePageActivity, bundle)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_movie)
        val rating: TextView = itemView.findViewById(R.id.age_rating)
        val poster: ImageView = itemView.findViewById(R.id.poster_movie)
    }
}