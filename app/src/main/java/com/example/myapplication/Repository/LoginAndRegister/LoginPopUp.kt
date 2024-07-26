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
import com.example.myapplication.databinding.LoginPopUpBinding

class LoginPopup(private val onLoginSuccess: () -> Unit) : DialogFragment() {
    private var _binding: LoginPopUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginModel by viewModels {
        LoginModel.LoginViewModelFactory(AuthRep())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmbtn.setOnClickListener {
            viewModel.signInUser(binding.email.text.toString(), binding.password.text.toString())
        }
        val show = AnimationUtils.loadAnimation(requireContext(), R.anim.itemshowing)
        binding.root.startAnimation(show)
        binding.closebtn.setOnClickListener {
            dialog?.dismiss()
        }
        binding.registerbtn.setOnClickListener {
            val registerdialog = RegisterPopUp()
            registerdialog.show(parentFragmentManager, "RegisterPopUpFragment")
        }

        viewModel.userSignInStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(),
                    getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                onLoginSuccess()
                dialog?.dismiss()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                binding.confirmbtn.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER)
    }
}
