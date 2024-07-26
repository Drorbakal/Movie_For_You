package com.example.myapplication.Repository.UI

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.TheatrePopUpBinding
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class TheatrePopUp: DialogFragment() {
    private var _binding: TheatrePopUpBinding? = null
    private val binding get() = _binding!!

    interface TheatrePopUpListener {
        fun onTheatreSelected(theatre: String)
        fun onButtonClicked(buttonText: String)
    }

    private var listener: TheatrePopUpListener? = null

    fun setTheatrePopUpListener(listener: MoviePageActivity) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TheatrePopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val israelZoneId = ZoneId.of("Israel")
        val currentTime = ZonedDateTime.now(israelZoneId)
        val currentTimeString = currentTime.format(DateTimeFormatter.ofPattern("HH"))
        val selectedDateMillis = arguments?.getLong("selectedDate")
        val selectedDate = Calendar.getInstance().apply {
            timeInMillis = selectedDateMillis ?: 0
        }
        val today = Calendar.getInstance()
        val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today.time)
        val selectedDateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
        binding.btn11.alpha = 0f
        binding.btn13.alpha = 0f
        binding.btn15.alpha = 0f
        binding.btn17.alpha = 0f
        binding.btn19.alpha = 0f
        binding.btn21.alpha = 0f
        binding.btn11.isClickable = false
        binding.btn13.isClickable = false
        binding.btn15.isClickable = false
        binding.btn17.isClickable = false
        binding.btn19.isClickable = false
        binding.btn21.isClickable = false
        binding.th1.setOnClickListener {
            listener?.onTheatreSelected("Planet")
            binding.btn11.alpha = 1f
            binding.btn13.alpha = 1f
            binding.btn15.alpha = 1f
            binding.btn17.alpha = 1f
            binding.btn19.alpha = 1f
            binding.btn21.alpha = 1f
            binding.btn11.isClickable = true
            binding.btn13.isClickable = true
            binding.btn15.isClickable = true
            binding.btn17.isClickable = true
            binding.btn19.isClickable = true
            binding.btn21.isClickable = true
            if (selectedDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && selectedDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                if (currentTimeString.toInt() >= 11) {
                    binding.btn11.alpha = 0f
                    binding.btn11.isClickable = false
                }
                if (currentTimeString.toInt() >= 13) {
                    binding.btn13.alpha = 0f
                    binding.btn13.isClickable = false
                }
                if (currentTimeString.toInt() >= 15) {
                    binding.btn15.alpha = 0f
                    binding.btn15.isClickable = false
                }
                if (currentTimeString.toInt() >= 17) {
                    binding.btn17.alpha = 0f
                    binding.btn17.isClickable = false
                }
                if (currentTimeString.toInt() >= 19) {
                    binding.btn19.alpha = 0f
                    binding.btn19.isClickable = false
                }
                if (currentTimeString.toInt() >= 21) {
                    binding.btn21.alpha = 0f
                    binding.btn21.isClickable = false
                }
            }
            binding.btn11.setOnClickListener {
                listener?.onButtonClicked(binding.btn11.text.toString())
                dismiss()
            }
            binding.btn13.setOnClickListener {
                listener?.onButtonClicked(binding.btn13.text.toString())
                dismiss()
            }
            binding.btn15.setOnClickListener {
                listener?.onButtonClicked(binding.btn15.text.toString())
                dismiss()
            }
            binding.btn17.setOnClickListener {
                listener?.onButtonClicked(binding.btn17.text.toString())
                dismiss()
            }
            binding.btn19.setOnClickListener {
                listener?.onButtonClicked(binding.btn19.text.toString())
                dismiss()
            }
            binding.btn21.setOnClickListener {
                listener?.onButtonClicked(binding.btn21.text.toString())
                dismiss()
            }
        }
        binding.th2.setOnClickListener {
            listener?.onTheatreSelected("Cinema City")
            binding.btn11.alpha = 1f
            binding.btn13.alpha = 1f
            binding.btn15.alpha = 1f
            binding.btn17.alpha = 1f
            binding.btn19.alpha = 1f
            binding.btn21.alpha = 1f
            binding.btn11.isClickable = true
            binding.btn13.isClickable = true
            binding.btn15.isClickable = true
            binding.btn17.isClickable = true
            binding.btn19.isClickable = true
            binding.btn21.isClickable = true
            if (selectedDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && selectedDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                if (currentTimeString.toInt() >= 11) {
                    binding.btn11.alpha = 0f
                    binding.btn11.isClickable = false
                }
                if (currentTimeString.toInt() >= 13) {
                    binding.btn13.alpha = 0f
                    binding.btn13.isClickable = false
                }
                if (currentTimeString.toInt() >= 15) {
                    binding.btn15.alpha = 0f
                    binding.btn15.isClickable = false
                }
                if (currentTimeString.toInt() >= 17) {
                    binding.btn17.alpha = 0f
                    binding.btn17.isClickable = false
                }
                if (currentTimeString.toInt() >= 19) {
                    binding.btn19.alpha = 0f
                    binding.btn19.isClickable = false
                }
                if (currentTimeString.toInt() >= 21) {
                    binding.btn21.alpha = 0f
                    binding.btn21.isClickable = false
                }
            }
            binding.btn11.setOnClickListener {
                listener?.onButtonClicked(binding.btn11.text.toString())
                dismiss()
            }
            binding.btn13.setOnClickListener {
                listener?.onButtonClicked(binding.btn13.text.toString())
                dismiss()
            }
            binding.btn15.setOnClickListener {
                listener?.onButtonClicked(binding.btn15.text.toString())
                dismiss()
            }
            binding.btn17.setOnClickListener {
                listener?.onButtonClicked(binding.btn17.text.toString())
                dismiss()
            }
            binding.btn19.setOnClickListener {
                listener?.onButtonClicked(binding.btn19.text.toString())
                dismiss()
            }
            binding.btn21.setOnClickListener {
                listener?.onButtonClicked(binding.btn21.text.toString())
                dismiss()
            }
        }
        binding.th3.setOnClickListener {
            listener?.onTheatreSelected("Hot Globus")
            binding.btn11.alpha = 1f
            binding.btn13.alpha = 1f
            binding.btn15.alpha = 1f
            binding.btn17.alpha = 1f
            binding.btn19.alpha = 1f
            binding.btn21.alpha = 1f
            binding.btn11.isClickable = true
            binding.btn13.isClickable = true
            binding.btn15.isClickable = true
            binding.btn17.isClickable = true
            binding.btn19.isClickable = true
            binding.btn21.isClickable = true
            if (selectedDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && selectedDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                if (currentTimeString.toInt() >= 11) {
                    binding.btn11.alpha = 0f
                    binding.btn11.isClickable = false
                }
                if (currentTimeString.toInt() >= 13) {
                    binding.btn13.alpha = 0f
                    binding.btn13.isClickable = false
                }
                if (currentTimeString.toInt() >= 15) {
                    binding.btn15.alpha = 0f
                    binding.btn15.isClickable = false
                }
                if (currentTimeString.toInt() >= 17) {
                    binding.btn17.alpha = 0f
                    binding.btn17.isClickable = false
                }
                if (currentTimeString.toInt() >= 19) {
                    binding.btn19.alpha = 0f
                    binding.btn19.isClickable = false
                }
                if (currentTimeString.toInt() >= 21) {
                    binding.btn21.alpha = 0f
                    binding.btn21.isClickable = false
                }
            }
            binding.btn11.setOnClickListener {
                listener?.onButtonClicked(binding.btn11.text.toString())
                dismiss()
            }
            binding.btn13.setOnClickListener {
                listener?.onButtonClicked(binding.btn13.text.toString())
                dismiss()
            }
            binding.btn15.setOnClickListener {
                listener?.onButtonClicked(binding.btn15.text.toString())
                dismiss()
            }
            binding.btn17.setOnClickListener {
                listener?.onButtonClicked(binding.btn17.text.toString())
                dismiss()
            }
            binding.btn19.setOnClickListener {
                listener?.onButtonClicked(binding.btn19.text.toString())
                dismiss()
            }
            binding.btn21.setOnClickListener {
                listener?.onButtonClicked(binding.btn21.text.toString())
                dismiss()
            }
        }
        binding.theatreCloseBtn.setOnClickListener {
            dismiss()
        }
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