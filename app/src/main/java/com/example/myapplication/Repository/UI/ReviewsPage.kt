package com.example.myapplication.Repository.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.AuthRep
import com.example.myapplication.Repository.FireBase.Review
import com.example.myapplication.Repository.FireBase.ReviewAdapter
import com.example.myapplication.databinding.ReviewsPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ReviewsPage : Fragment() {
    private var _binding: ReviewsPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var reviewsList: ArrayList<Review>
    private lateinit var myAdapter: ReviewAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ReviewsPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserEmail = currentUser?.email
        val title = arguments?.getString("title")
        val hebrewTitle = arguments?.getString("title(hebrew)")
        val details = arguments?.getString("details")
        val hebrewDetails = arguments?.getString("details(hebrew)")
        val poster = arguments?.getString("poster")
        val trailer = arguments?.getString("trailer")
        val img1 = arguments?.getString("img1")
        val img2 = arguments?.getString("img2")
        val img3 = arguments?.getString("img3")
        val img4 = arguments?.getString("img4")
        val rating = arguments?.getString("rating")
        binding.writeBtn.setOnClickListener {
            if (currentUserEmail != null) {
                val bundle = bundleOf(
                    "title" to title,
                    "title_hebrew" to hebrewTitle
                )
                val dialog = WriteReviewPopUp().apply {
                    arguments = bundle
                }
                dialog.show(parentFragmentManager, "WriteReviewPopUp")
            }
            else {
                Toast.makeText(requireContext(),
                    getString(R.string.you_can_t_write_a_review_without_being_logged_in), Toast.LENGTH_SHORT).show()
            }
        }
        binding.movienameReviewpage.text = title
        recyclerView = binding.reviewsresview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        reviewsList = arrayListOf()
        myAdapter = ReviewAdapter(reviewsList)
        recyclerView.adapter = myAdapter
        if (title != null) {
            fetchReviewsFromFirestore(title)
        }
        binding.backbtnReviews.setOnClickListener {
            val bundle = bundleOf(
                "title" to title,
                "title(hebrew)" to hebrewTitle,
                "details" to details,
                "details(hebrew)" to hebrewDetails,
                "poster" to poster,
                "trailer" to trailer,
                "img1" to img1,
                "img2" to img2,
                "img3" to img3,
                "img4" to img4,
                "rating" to rating
            )
            findNavController().navigate(R.id.action_reviewsPage_to_picsMovieTrailer,bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun fetchReviewsFromFirestore(title:String) {
        db = FirebaseFirestore.getInstance()
        db.collection("${title}_reviews")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val writer = document.get("email").toString()
                    val rating = document.get("rating").toString()
                    val review_txt = document.get("review").toString()
                    val review = Review(writer, rating, review_txt)
                    reviewsList.add(review)
                }
                myAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }
    }
}
