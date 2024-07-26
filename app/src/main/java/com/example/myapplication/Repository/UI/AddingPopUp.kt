package com.example.myapplication.Repository.UI

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.EditableItem
import com.example.myapplication.Repository.FireBase.EditableItemAdapter
import com.example.myapplication.databinding.AddPopUpBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddingPopUp : DialogFragment() {
    private var _binding: AddPopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var storage: FirebaseStorage
    private var selectedImageView: ImageView? = null
    private val PICK_IMAGE_REQUEST = 71
    private lateinit var db: FirebaseFirestore
    private var posterUrl: String? = null
    private var img1Url: String? = null
    private var img2Url: String? = null
    private var img3Url: String? = null
    private var img4Url: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storage = FirebaseStorage.getInstance()
        db = FirebaseFirestore.getInstance()

        val show = AnimationUtils.loadAnimation(requireContext(), R.anim.itemshowing)
        binding.root.startAnimation(show)
        val items = mutableListOf(
            EditableItem(getString(R.string.movie_title)),
            EditableItem(getString(R.string.movie_title_hebrew)),
            EditableItem(getString(R.string.trailer_link)),
            EditableItem(getString(R.string.rating)),
            EditableItem(getString(R.string.movie_details)),
            EditableItem(getString(R.string.movie_details_hebrew))
        )
        val adapter = EditableItemAdapter(items)
        binding.addingEditText.layoutManager = LinearLayoutManager(requireContext())
        binding.addingEditText.adapter = adapter
        binding.addMovieBtn.setOnClickListener {
            val title = items[0].text
            val titleHebrew = items[1].text
            val trailer = items[2].text
            val rating = items[3].text
            val details = items[4].text
            val detailsHebrew = items[5].text

            // Check if all image URLs are available and validate fields
            if (posterUrl != null && img1Url != null && img2Url != null && img3Url != null && img4Url != null) {
                if (title.isNotEmpty() && titleHebrew.isNotEmpty() && trailer.isNotEmpty() &&
                    rating.isNotEmpty() && details.isNotEmpty() && detailsHebrew.isNotEmpty()) {

                    // Save the movie data along with the image URLs to Firestore
                    val moviedata = hashMapOf(
                        "title" to title,
                        "title_hebrew" to titleHebrew,
                        "trailer" to trailer,
                        "rating" to rating,
                        "details" to details,
                        "details_hebrew" to detailsHebrew,
                        "poster" to posterUrl,
                        "img1" to img1Url,
                        "img2" to img2Url,
                        "img3" to img3Url,
                        "img4" to img4Url
                    )
                    db.collection("movies")
                        .add(moviedata)  // Using add() to auto-generate document ID
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Movie added successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Failed to add review: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(requireContext(), "Please fill in all the required fields", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please select all images", Toast.LENGTH_SHORT).show()
            }
        }

        binding.posterview.setOnClickListener {
            selectImage(binding.posterview)
        }
        binding.image1view.setOnClickListener {
            selectImage(binding.image1view)
        }
        binding.image2view.setOnClickListener {
            selectImage(binding.image2view)
        }
        binding.image3view.setOnClickListener {
            selectImage(binding.image3view)
        }
        binding.image4view.setOnClickListener {
            selectImage(binding.image4view)
        }
        binding.closeAddBtn.setOnClickListener {
            dismiss()
        }
        checkAndRequestPermissions()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectImage(imageView: ImageView) {
        selectedImageView = imageView
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val fileUri = data.data
            uploadImageToFirebase(fileUri!!)
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        val ref = storage.reference.child("images/${UUID.randomUUID()}")
        ref.putFile(fileUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    selectedImageView?.setImageURI(fileUri)
                    // Set the appropriate URL based on the selectedImageView
                    when (selectedImageView) {
                        binding.posterview -> posterUrl = uri.toString()
                        binding.image1view -> img1Url = uri.toString()
                        binding.image2view -> img2Url = uri.toString()
                        binding.image3view -> img3Url = uri.toString()
                        binding.image4view -> img4Url = uri.toString()
                    }
                }
            }
            .addOnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkAndRequestPermissions() {
        val permissions = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(requireActivity(), permissions.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permissions granted
                } else {
                    Toast.makeText(requireContext(), "Permissions are required to select images", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}
