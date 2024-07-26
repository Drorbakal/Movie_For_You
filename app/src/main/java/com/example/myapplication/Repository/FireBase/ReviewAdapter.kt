package com.example.myapplication.Repository.FireBase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ReviewsCardBinding

class ReviewAdapter(private val reviewsList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(private val binding: ReviewsCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.reviewWriter.text = review.writer
            binding.reviewRating.text = review.movie_rating
            binding.reviewTxt.text = review.review_txt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ReviewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewsList[position])
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }
}
