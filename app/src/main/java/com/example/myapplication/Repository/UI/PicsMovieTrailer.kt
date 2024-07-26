package com.example.myapplication.Repository.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.EditableItem
import com.example.myapplication.Repository.FireBase.EditableItemAdapter
import com.example.myapplication.Repository.FireBase.Review
import com.example.myapplication.databinding.PicsTrailersRatingBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class PicsMovieTrailer : Fragment() {
    private var _binding: PicsTrailersRatingBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PicsTrailersRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val total_rate = 0.0f
        binding.trailer.settings.javaScriptEnabled = true
        binding.trailer.settings.domStorageEnabled = true
        binding.trailer.webViewClient = WebViewClient()
        binding.trailer.loadUrl(trailer.toString())
        try {
            Picasso.get().load(img1).into(binding.img1)
            Picasso.get().load(img2).into(binding.img2)
            Picasso.get().load(img3).into(binding.img3)
            Picasso.get().load(img4).into(binding.img4)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.reviewBtn.setOnClickListener {
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
            findNavController().navigate(R.id.action_picsMovieTrailer_to_reviewsPage,bundle)
        }
        if (title != null) {
            fetchRatingFromFirestore(title)
        }
        binding.backbtnPtr.setOnClickListener {
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
            findNavController().navigate(R.id.action_picsMovieTrailer_to_moviePageActivity,bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchRatingFromFirestore(title: String) {
        var totalRating = 0f
        var count = 0
        db = FirebaseFirestore.getInstance()
        db.collection("${title}_reviews")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val rating = document.get("rating")?.toString()?.toFloatOrNull()
                    if (rating != null) {
                        totalRating += rating
                        count++
                    }
                }
                if (count > 0) {
                    val averageRating = totalRating / count
                    val formattedRating = String.format("%.1f", averageRating)
                    binding.reviewTxt.text = formattedRating
                }
                else {
                    binding.reviewRate.visibility = View.GONE
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to fetch ratings: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

