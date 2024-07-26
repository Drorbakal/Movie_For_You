import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.DetailsPopUpBinding

class DetailsPopUp : DialogFragment() {
    private var _binding: DetailsPopUpBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null
    private var details: String? = null

    companion object {
        fun newInstance(bundle: Bundle): DetailsPopUp {
            val dialog = DetailsPopUp()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from arguments bundle
        title = arguments?.getString("title")
        details = arguments?.getString("details")

        // Set data to views
        binding.closebtnDetailsPopup.setOnClickListener {
            dismiss()
        }
        binding.movieTitlePopup.text = title
        binding.movieDetailsPopup.text = details
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
