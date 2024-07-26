package com.example.myapplication.Repository.UI

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.OrderNumPopupBinding
import com.squareup.picasso.Picasso

class OrderPopUp : DialogFragment() {
    private var _binding: OrderNumPopupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderNumPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title")
        val poster = arguments?.getString("poster")
        val date = arguments?.getString("date")
        val hour = arguments?.getString("hour")
        val total = arguments?.getFloat("amount")
        val seats = arguments?.getString("seats")
        val theatre = arguments?.getString("theatre")

        binding.closeOrderBtn.setOnClickListener {
            dismiss()
        }
        binding.amountOrder.text = "${total.toString()}$"
        binding.dateOrder.text = date
        binding.hourOrder.text = hour
        binding.theatreOrder.text = theatre
        binding.titleOrder.text = title
        if (seats.toString() == "seat11"){
            binding.seatsOrder.text = "row:1 , seat:1"
        }
        if (seats.toString() == "seat21"){
            binding.seatsOrder.text = "row:2 , seat:1"
        }
        if (seats.toString() == "seat31"){
            binding.seatsOrder.text = "row:3 , seat:1"
        }
        if (seats.toString() == "seat41"){
            binding.seatsOrder.text = "row:4 , seat:1"
        }
        if (seats.toString() == "seat51"){
            binding.seatsOrder.text = "row:5 , seat:1"
        }
        if (seats.toString() == "seat21"){
            binding.seatsOrder.text = "row:1 , seat:2"
        }
        if (seats.toString() == "seat22"){
            binding.seatsOrder.text = "row:2 , seat:2"
        }
        if (seats.toString() == "seat23"){
            binding.seatsOrder.text = "row:3 , seat:2"
        }
        if (seats.toString() == "seat24"){
            binding.seatsOrder.text = "row:4 , seat:2"
        }
        if (seats.toString() == "seat25"){
            binding.seatsOrder.text = "row:5 , seat:2"
        }
        if (seats.toString() == "seat31"){
            binding.seatsOrder.text = "row:1 , seat:3"
        }
        if (seats.toString() == "seat32"){
            binding.seatsOrder.text = "row:2 , seat:3"
        }
        if (seats.toString() == "seat33"){
            binding.seatsOrder.text = "row:3 , seat:3"
        }
        if (seats.toString() == "seat34"){
            binding.seatsOrder.text = "row:4 , seat:3"
        }
        if (seats.toString() == "seat35"){
            binding.seatsOrder.text = "row:5 , seat:3"
        }
        if (seats.toString() == "seat41"){
            binding.seatsOrder.text = "row:1 , seat:4"
        }
        if (seats.toString() == "seat42"){
            binding.seatsOrder.text = "row:2 , seat:4"
        }
        if (seats.toString() == "seat43"){
            binding.seatsOrder.text = "row:3 , seat:4"
        }
        if (seats.toString() == "seat44"){
            binding.seatsOrder.text = "row:4 , seat:4"
        }
        if (seats.toString() == "seat45"){
            binding.seatsOrder.text = "row:5 , seat:4"
        }
        if (seats.toString() == "seat51"){
            binding.seatsOrder.text = "row:1 , seat:5"
        }
        if (seats.toString() == "seat52"){
            binding.seatsOrder.text = "row:2 , seat:5"
        }
        if (seats.toString() == "seat53"){
            binding.seatsOrder.text = "row:3 , seat:5"
        }
        if (seats.toString() == "seat54"){
            binding.seatsOrder.text = "row:4 , seat:5"
        }
        if (seats.toString() == "seat55"){
            binding.seatsOrder.text = "row:5 , seat:5"
        }
        Picasso.get().load(poster).into(binding.posterOrder)
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
}
