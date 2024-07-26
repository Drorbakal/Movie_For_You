package com.example.myapplication.Repository.UI

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.MovieAdapter
import com.example.myapplication.databinding.MoviePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MoviePageActivity : Fragment(), TheatrePopUp.TheatrePopUpListener {
    private var _binding: MoviePageBinding? = null
    private val binding get() = _binding!!
    private var selectedHour: String? = null
    private var selectedDate: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MoviePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserEmail = currentUser?.email
        var adultvalue = 0
        var childvalue = 0
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
        binding.calendercard.alpha = 0.0f
        binding.ticketscard.alpha = 0.0f
        binding.movieTitleMoviePage.text = title
        binding.movieDetailsMoviePage.text = details
        binding.removebtn.isClickable = false
        binding.removebtn.alpha = 0f
        if (currentUserEmail == "drorbakal17@gmail.com"){
            binding.removebtn.isClickable = true
            binding.removebtn.alpha = 1f
        }
        Picasso.get().load(poster).into(binding.moviePosterMoviepage)
        Picasso.get().load(poster).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                val drawable = BitmapDrawable(resources, bitmap)
                binding.backgroundSet.background = drawable
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                // Handle error
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        })
        binding.backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_moviePageActivity_to_homePage2)
        }
        binding.removebtn.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val currentUserEmail = currentUser?.email
            if (currentUserEmail == "drorbakal17@gmail.com") {
                val titleToRemove = arguments?.getString("title")
                titleToRemove?.let { title ->
                    val db = FirebaseFirestore.getInstance()
                    val moviesRef = db.collection("movies")
                    moviesRef.whereEqualTo("title", title)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            for (document in querySnapshot.documents) {
                                document.reference.delete()
                                    .addOnSuccessListener {
                                        Toast.makeText(requireContext(),
                                            getString(R.string.movie_deleted_successfully), Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(requireContext(),
                                            getString(R.string.error_deleting_movie, e.message), Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(),
                                getString(R.string.error_getting_documents, e.message), Toast.LENGTH_SHORT).show()
                        }
                } ?: run {
                    Toast.makeText(requireContext(),
                        getString(R.string.title_to_remove_is_null), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.you_do_not_have_permission_to_delete_movies), Toast.LENGTH_SHORT).show()
            }
        }
        binding.ticketsbtn.setOnClickListener {
            binding.calendercard.alpha = 1.0f
            val calendar = Calendar.getInstance()
            binding.datePicker.init(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            { _, year, monthOfYear, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, monthOfYear, dayOfMonth)
                selectedDate = selectedCalendar.time
            }
            val maxDate = Calendar.getInstance()
            maxDate.add(Calendar.DAY_OF_MONTH, 14)
            binding.datePicker.minDate = calendar.timeInMillis
            binding.datePicker.maxDate = maxDate.timeInMillis
        }
        binding.datebtn.setOnClickListener {
            binding.ticketscard.alpha = 1.0f
            val year = binding.datePicker.year
            val month = binding.datePicker.month
            val day = binding.datePicker.dayOfMonth
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, day)
            selectedDate = selectedCalendar.time

            selectedDate?.let {
                val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
            } ?: run {
            }
        }
        binding.theatrebtn.setOnClickListener {
            val selectedDateMillis = selectedDate?.time ?: 0
            val bundle = Bundle().apply {
                putLong("selectedDate", selectedDateMillis)
            }
            val dialog = TheatrePopUp()
            dialog.arguments = bundle
            dialog.setTheatrePopUpListener(this)
            dialog.show(parentFragmentManager, "TheatrePopUp")
        }
        binding.plus.setOnClickListener {
            adultvalue++
            binding.adultedittxt.setText(adultvalue.toString())
            binding.adulteditpricetxt.setText("%.1f$".format(adultvalue * 9.9))
        }
        binding.minus.setOnClickListener {
            if (adultvalue > 0) {
                adultvalue--
                binding.adultedittxt.setText(adultvalue.toString())
                binding.adulteditpricetxt.setText("%.1f$".format(adultvalue * 9.9))
            }
        }
        binding.plus2.setOnClickListener {
            childvalue++
            binding.childedittxt.setText(childvalue.toString())
            binding.childpriceedittxt.setText("%.1f$".format(childvalue * 7.9))
        }
        binding.minus2.setOnClickListener {
            if (childvalue > 0) {
                childvalue--
                binding.childedittxt.setText(childvalue.toString())
                binding.childpriceedittxt.setText("%.1f$".format(childvalue * 7.9))
            }
        }
        binding.order.setOnClickListener {
            if (binding.theatrebtn.text != "theatre") {
                if (adultvalue > 0 || childvalue > 0) {
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
                        "rating" to rating,
                        "adultvalue" to adultvalue,
                        "childvalue" to childvalue,
                        "theaterText" to binding.theatrebtn.text,
                        "hour" to selectedHour,
                        "date" to selectedDate?.time
                    )
                    findNavController().navigate(R.id.action_moviePageActivity_to_chooseSeatActivity, bundle)
                } else {
                    Toast.makeText(requireContext(),
                        getString(R.string.type_your_amount_of_tickets),Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.choose_your_cinema_theatre),Toast.LENGTH_SHORT).show()
            }
        }

        binding.moviePosterMoviepage.setOnClickListener {
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
            findNavController().navigate(R.id.action_moviePageActivity_to_picsMovieTrailer, bundle)
        }
        binding.movieDetailsMoviePage.setOnClickListener {
            val bundle = bundleOf(
                "title" to title,
                "title(hebrew)" to hebrewTitle,
                "details" to details,
                "details(hebrew)" to hebrewDetails
            )
            val dialog = DetailsPopUp.newInstance(bundle)
            dialog.show(parentFragmentManager, "DetailsPopUp")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTheatreSelected(theatre: String) {
        binding.theatrebtn.text = theatre
    }

    override fun onButtonClicked(buttonText: String) {
        selectedHour = buttonText
    }

}
