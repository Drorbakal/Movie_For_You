package com.example.myapplication.Repository.LoginAndRegister

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.AuthRep
import com.example.myapplication.databinding.RegisterPopUpBinding

class RegisterPopUp : DialogFragment() {
    private var _binding: RegisterPopUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterModel by viewModels {
        RegisterModel.RegisterViewModelFactory(AuthRep())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmbtnRegister.setOnClickListener {
            viewModel.createUser(
                binding.nameRegister.text.toString(),
                binding.emailRegister.text.toString(),
                binding.passwordRegister.text.toString()
            )
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.confirmbtnRegister.isVisible = !isLoading
        }

        viewModel.registrationSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(),
                    getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                binding.confirmbtnRegister.isVisible = true
            }
        }

        val show = AnimationUtils.loadAnimation(requireContext(), R.anim.itemshowing)
        binding.root.startAnimation(show)
        binding.closebtnRegister.setOnClickListener {
            dialog?.dismiss()
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
