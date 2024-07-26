package com.example.myapplication.Repository.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.Review
import com.example.myapplication.databinding.ChooseSeatPageBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ChooseSeatActivity : Fragment() {
    private var _binding: ChooseSeatPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChooseSeatPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
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
        val adultvalue = arguments?.getInt("adultvalue") ?: 0
        val childvalue = arguments?.getInt("childvalue") ?: 0
        val theaterText = arguments?.getString("theaterText")
        val hour = arguments?.getString("hour")
        val date = arguments?.getString("date")
        var count: Int = adultvalue + childvalue
        val amount = (childvalue * 7.9) + (adultvalue * 9.9)
        val toastMessage = "title: $title theatre : $theaterText"
        var totaltext = ""
        val selectedDateMillis = arguments?.getLong("date")
        val selectedDate = Calendar.getInstance().apply {
            timeInMillis = selectedDateMillis ?: 0
        }
        val selectedDateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
        db = FirebaseFirestore.getInstance()
        db.collection("${title}_tickets")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val db_date = document.get("date").toString()
                    val db_hour = document.get("hour").toString()
                    val db_seat = document.get("seats").toString()
                    val db_theatre = document.get("theatre").toString()
                    if (selectedDateString == db_date && db_hour == hour && db_theatre == theaterText) {
                        if ("seat11" in db_seat) {
                            binding.seat11.alpha = 0.1f
                        }
                        if ("seat12" in db_seat) {
                            binding.seat12.alpha = 0.1f
                        }
                        if ("seat13" in db_seat) {
                            binding.seat13.alpha = 0.1f
                        }
                        if ("seat14" in db_seat) {
                            binding.seat14.alpha = 0.1f
                        }
                        if ("seat15" in db_seat) {
                            binding.seat15.alpha = 0.1f
                        }
                        if ("seat21" in db_seat) {
                            binding.seat21.alpha = 0.1f
                        }
                        if ("seat22" in db_seat) {
                            binding.seat22.alpha = 0.1f
                        }
                        if ("seat23" in db_seat) {
                            binding.seat23.alpha = 0.1f
                        }
                        if ("seat24" in db_seat) {
                            binding.seat24.alpha = 0.1f
                        }
                        if ("seat25" in db_seat) {
                            binding.seat25.alpha = 0.1f
                        }
                        if ("seat31" in db_seat) {
                            binding.seat31.alpha = 0.1f
                        }
                        if ("seat32" in db_seat) {
                            binding.seat32.alpha = 0.1f
                        }
                        if ("seat33" in db_seat) {
                            binding.seat33.alpha = 0.1f
                        }
                        if ("seat34" in db_seat) {
                            binding.seat34.alpha = 0.1f
                        }
                        if ("seat35" in db_seat) {
                            binding.seat35.alpha = 0.1f
                        }
                        if ("seat41" in db_seat) {
                            binding.seat41.alpha = 0.1f
                        }
                        if ("seat42" in db_seat) {
                            binding.seat42.alpha = 0.1f
                        }
                        if ("seat43" in db_seat) {
                            binding.seat43.alpha = 0.1f
                        }
                        if ("seat44" in db_seat) {
                            binding.seat44.alpha = 0.1f
                        }
                        if ("seat45" in db_seat) {
                            binding.seat45.alpha = 0.1f
                        }
                        if ("seat51" in db_seat) {
                            binding.seat51.alpha = 0.1f
                        }
                        if ("seat52" in db_seat) {
                            binding.seat52.alpha = 0.1f
                        }
                        if ("seat53" in db_seat) {
                            binding.seat53.alpha = 0.1f
                        }
                        if ("seat54" in db_seat) {
                            binding.seat54.alpha = 0.1f
                        }
                        if ("seat55" in db_seat) {
                            binding.seat55.alpha = 0.1f
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
            }
        binding.seat11.setOnClickListener {
            if (binding.seat11.alpha == 1f) {
                binding.seat11.alpha = 0.34f
                count--
                totaltext += "seat11"
            } else if (binding.seat11.alpha == 0.34f) {
                count++
                binding.seat11.alpha = 1f
                totaltext = totaltext.replace("seat11", "")
            }
        }
        binding.seat12.setOnClickListener {
            if (binding.seat12.alpha == 1f) {
                binding.seat12.alpha = 0.34f
                count--
                totaltext += "seat12"
            } else if (binding.seat12.alpha == 0.34f) {
                count++
                binding.seat12.alpha = 1f
                totaltext = totaltext.replace("seat12", "")
            }
        }
        binding.seat13.setOnClickListener {
            if (binding.seat13.alpha == 1f) {
                binding.seat13.alpha = 0.34f
                count--
                totaltext += "seat13"
            }
            else if (binding.seat13.alpha == 0.34f) {
                count++
                binding.seat13.alpha = 1f
                totaltext = totaltext.replace("seat13", "")
            }
        }
        binding.seat14.setOnClickListener {
            if (binding.seat14.alpha == 1f) {
                binding.seat14.alpha = 0.34f
                count--
                totaltext += "seat14"
            }
            else if (binding.seat14.alpha == 0.34f) {
                count++
                binding.seat14.alpha = 1f
                totaltext = totaltext.replace("seat14", "")
            }
        }
        binding.seat15.setOnClickListener {
            if (binding.seat15.alpha == 1f) {
                binding.seat15.alpha = 0.34f
                count--
                totaltext += "seat15"
            }
            else if (binding.seat15.alpha == 0.34f) {
                count++
                binding.seat15.alpha = 1f
                totaltext = totaltext.replace("seat15", "")
            }
        }
        binding.seat21.setOnClickListener {
            if (binding.seat21.alpha == 1f) {
                binding.seat21.alpha = 0.34f
                count--
                totaltext += "seat21"
            }
            else if (binding.seat21.alpha == 0.34f) {
                count++
                binding.seat21.alpha = 1f
                totaltext = totaltext.replace("seat21", "")
            }
        }
        binding.seat22.setOnClickListener {
            if (binding.seat22.alpha == 1f) {
                binding.seat22.alpha = 0.34f
                count--
                totaltext += "seat22"
            }
            else if (binding.seat22.alpha == 0.34f) {
                count++
                binding.seat22.alpha = 1f
                totaltext = totaltext.replace("seat22", "")
            }
        }
        binding.seat23.setOnClickListener {
            if (binding.seat23.alpha == 1f) {
                binding.seat23.alpha = 0.34f
                count--
                totaltext += "seat23"
            }
            else if (binding.seat23.alpha == 0.34f) {
                count++
                binding.seat23.alpha = 1f
                totaltext = totaltext.replace("seat23", "")
            }
        }
        binding.seat24.setOnClickListener {
            if (binding.seat24.alpha == 1f) {
                binding.seat24.alpha = 0.34f
                count--
                totaltext += "seat24"
            }
            else if (binding.seat24.alpha == 0.34f) {
                count++
                binding.seat24.alpha = 1f
                totaltext = totaltext.replace("seat24", "")
            }
        }
        binding.seat25.setOnClickListener {
            if (binding.seat25.alpha == 1f) {
                binding.seat25.alpha = 0.34f
                count--
                totaltext += "seat25"
            }
            else if (binding.seat25.alpha == 0.34f) {
                count++
                binding.seat25.alpha = 1f
                totaltext = totaltext.replace("seat25", "")
            }
        }
        binding.seat31.setOnClickListener {
            if (binding.seat31.alpha == 1f) {
                binding.seat31.alpha = 0.34f
                count--
                totaltext += "seat31"
            }
            else if (binding.seat31.alpha == 0.34f) {
                count++
                binding.seat31.alpha = 1f
                totaltext = totaltext.replace("seat31", "")
            }
        }
        binding.seat32.setOnClickListener {
            if (binding.seat32.alpha == 1f) {
                binding.seat32.alpha = 0.34f
                count--
                totaltext += "seat32"
            }
            else if (binding.seat32.alpha == 0.34f) {
                count++
                binding.seat32.alpha = 1f
                totaltext = totaltext.replace("seat32", "")
            }
        }
        binding.seat33.setOnClickListener {
            if (binding.seat33.alpha == 1f) {
                binding.seat33.alpha = 0.34f
                count--
                totaltext += "seat33"
            }
            else if (binding.seat33.alpha == 0.34f) {
                count++
                binding.seat33.alpha = 1f
                totaltext = totaltext.replace("seat33", "")
            }
        }
        binding.seat34.setOnClickListener {
            if (binding.seat34.alpha == 1f) {
                binding.seat34.alpha = 0.34f
                count--
                totaltext += "seat34"
            }
            else if (binding.seat34.alpha == 0.34f) {
                count++
                binding.seat34.alpha = 1f
                totaltext = totaltext.replace("seat34", "")
            }
        }
        binding.seat35.setOnClickListener {
            if (binding.seat35.alpha == 1f) {
                binding.seat35.alpha = 0.34f
                count--
                totaltext += "seat35"
            }
            else if (binding.seat35.alpha == 0.34f) {
                count++
                binding.seat35.alpha = 1f
                totaltext = totaltext.replace("seat35", "")
            }
        }
        binding.seat41.setOnClickListener {
            if (binding.seat41.alpha == 1f) {
                binding.seat41.alpha = 0.34f
                count--
                totaltext += "seat41"
            }
            else if (binding.seat41.alpha == 0.34f) {
                count++
                binding.seat41.alpha = 1f
                totaltext = totaltext.replace("seat41", "")
            }
        }
        binding.seat42.setOnClickListener {
            if (binding.seat42.alpha == 1f) {
                binding.seat42.alpha = 0.34f
                count--
                totaltext += "seat42"
            }
            else if (binding.seat42.alpha == 0.34f) {
                count++
                binding.seat42.alpha = 1f
                totaltext = totaltext.replace("seat42", "")
            }
        }
        binding.seat43.setOnClickListener {
            if (binding.seat43.alpha == 1f) {
                binding.seat43.alpha = 0.34f
                count--
                totaltext += "seat43"
            }
            else if (binding.seat43.alpha == 0.34f) {
                count++
                binding.seat43.alpha = 1f
                totaltext = totaltext.replace("seat43", "")
            }
        }
        binding.seat44.setOnClickListener {
            if (binding.seat44.alpha == 1f) {
                binding.seat44.alpha = 0.34f
                count--
                totaltext += "seat44"
            }
            else if (binding.seat44.alpha == 0.34f) {
                count++
                binding.seat44.alpha = 1f
                totaltext = totaltext.replace("seat44", "")
            }
        }
        binding.seat45.setOnClickListener {
            if (binding.seat45.alpha == 1f) {
                binding.seat45.alpha = 0.34f
                count--
                totaltext += "seat45"
            }
            else if (binding.seat45.alpha == 0.34f) {
                count++
                binding.seat45.alpha = 1f
                totaltext = totaltext.replace("seat45", "")
            }
        }
        binding.seat51.setOnClickListener {
            if (binding.seat51.alpha == 1f) {
                binding.seat51.alpha = 0.34f
                count--
                totaltext += "seat51"
            }
            else if (binding.seat51.alpha == 0.34f) {
                count++
                binding.seat51.alpha = 1f
                totaltext = totaltext.replace("seat51", "")
            }
        }
        binding.seat52.setOnClickListener {
            if (binding.seat52.alpha == 1f) {
                binding.seat52.alpha = 0.34f
                count--
                totaltext += "seat52"
            }
            else if (binding.seat52.alpha == 0.34f) {
                count++
                binding.seat52.alpha = 1f
                totaltext = totaltext.replace("seat52", "")
            }
        }
        binding.seat53.setOnClickListener {
            if (binding.seat53.alpha == 1f) {
                binding.seat53.alpha = 0.34f
                count--
                totaltext += "seat53"
            }
            else if (binding.seat53.alpha == 0.34f) {
                count++
                binding.seat53.alpha = 1f
                totaltext = totaltext.replace("seat53", "")
            }
        }
        binding.seat54.setOnClickListener {
            if (binding.seat54.alpha == 1f) {
                binding.seat54.alpha = 0.34f
                count--
                totaltext += "seat54"
            }
            else if (binding.seat54.alpha == 0.34f) {
                count++
                binding.seat54.alpha = 1f
                totaltext = totaltext.replace("seat54", "")
            }
        }
        binding.seat55.setOnClickListener {
            if (binding.seat55.alpha == 1f) {
                binding.seat55.alpha = 0.34f
                count--
                totaltext += "seat55"
            }
            else if (binding.seat55.alpha == 0.34f) {
                count++
                binding.seat55.alpha = 1f
                totaltext = totaltext.replace("seat55", "")
            }
        }
        binding.confirmTicketsBtn.setOnClickListener {
            if (count != 0) {
                Toast.makeText(requireContext(),
                    getString(R.string.choose_the_right_amount_of_seats), Toast.LENGTH_SHORT).show()
            } else {
                val db = FirebaseFirestore.getInstance()
                val reviewData = hashMapOf(
                    "title" to title,
                    "theatre" to theaterText,
                    "hour" to hour,
                    "date" to selectedDateString,
                    "childs" to childvalue,
                    "adults" to adultvalue,
                    "total" to amount,
                    "seats" to totaltext
                )

                db.collection("${title}_tickets")
                    .add(reviewData)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "order successfully", Toast.LENGTH_SHORT).show()
                        val bundle = bundleOf(
                            "title" to title,
                            "poster" to poster,
                            "theatre" to theaterText,
                            "hour" to hour,
                            "date" to selectedDateString,
                            "amount" to amount.toFloat(), // Convert to Float
                            "seats" to totaltext
                        )


                        val dialog = OrderPopUp().apply {
                            arguments = bundle
                        }
                        dialog.show(parentFragmentManager, "OrderPopUp")
                        findNavController().navigate(R.id.action_chooseSeatActivity_to_homePage2)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Failed to add review: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }



        binding.backbtnSeats.setOnClickListener {
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
            findNavController().navigate(R.id.action_chooseSeatActivity_to_moviePageActivity, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}