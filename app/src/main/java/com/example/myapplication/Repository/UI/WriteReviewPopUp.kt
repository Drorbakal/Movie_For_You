package com.example.myapplication.Repository.UI

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.AuthRep
import com.example.myapplication.databinding.WritePopUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class WriteReviewPopUp : DialogFragment() {
    private var _binding: WritePopUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WritePopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val authRep = AuthRep()
        super.onViewCreated(view, savedInstanceState)
        val show = AnimationUtils.loadAnimation(requireContext(), R.anim.itemshowing)
        binding.root.startAnimation(show)
        binding.writeReviewTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.writeReviewTxt.hint = R.string.write_a_review.toString()
                } else {
                    binding.writeReviewTxt.hint = null
                }
            }
        })
        binding.sendReviewBtn.setOnClickListener {
            val reviewText = binding.writeReviewTxt.text.toString().trim()
            val rating = binding.ratingBar.rating
            if (reviewText.isNotEmpty()) {
                val title = arguments?.getString("title")
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.let {
                    val email = it.email
                    if (title != null && email != null) {
                        addReviewToFirebase(title, reviewText, email,rating)
                    }
                }
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.please_write_a_review), Toast.LENGTH_SHORT).show()
            }
        }

        binding.closebtnWriteReview.setOnClickListener {
            dialog?.dismiss()
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            val email = it.email
        }

        lifecycleScope.launch {
            authRep.currentuser()
        }
    }

    private fun addReviewToFirebase(title: String, review: String,email:String, rating: Float) {
        val db = FirebaseFirestore.getInstance()
        val reviewData = hashMapOf(
            "review" to review,
            "rating" to rating,
            "email" to email,
        )

        db.collection("${title}_reviews")
            .add(reviewData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),
                    getString(R.string.review_added_successfully), Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(),
                    getString(R.string.failed_to_add_review, e.message), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
    }
}
