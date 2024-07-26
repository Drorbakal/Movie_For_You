package com.example.myapplication.Repository.UI


import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ServicePopUpBinding

class ServiceDialogFragment : DialogFragment() {

    private lateinit var binding: ServicePopUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ServicePopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val show = AnimationUtils.loadAnimation(requireContext(), R.anim.itemshowing)
        binding.root.startAnimation(show)
        binding.sendBtn.setOnClickListener {
            val subject = binding.subjectTxt.text.toString()
            val emailText = binding.emailTxt.text.toString()

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("dapp4123@GMAIL.COM"))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, emailText)
            }
            startActivity(Intent.createChooser(intent, "Send Email"))
            dismiss()
        }

        binding.backBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }
}
