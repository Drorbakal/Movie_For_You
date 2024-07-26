package com.example.myapplication.Repository.UI

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Repository.FireBase.AuthRep
import com.example.myapplication.Repository.FireBase.MovieAdapter
import com.example.myapplication.Repository.LoginAndRegister.LoginPopup
import com.example.myapplication.Repository.FireBase.Movie
import com.example.myapplication.databinding.HomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class HomePage : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesList: ArrayList<Movie>
    private lateinit var myAdapter: MovieAdapter
    private lateinit var db: FirebaseFirestore
    private var _binding: HomePageBinding? = null
    private val binding get() = _binding!!
    private val authRep = AuthRep()
    private var loginPopupShown = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomePageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.resview
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        moviesList= arrayListOf()
        myAdapter= MovieAdapter(moviesList, authRep, viewLifecycleOwner)
        recyclerView.adapter=myAdapter
        EventChangeListner()
        authRep.connect.observe(viewLifecycleOwner) { connect ->
            if (connect == 0 && !loginPopupShown) {
                loginPopupShown = true
                Handler(Looper.getMainLooper()).postDelayed({
                    val loginDialog = LoginPopup {
                        lifecycleScope.launch {
                            authRep.currentuser()
                        }
                    }
                    loginDialog.show(parentFragmentManager, "LoginPopupFragment")
                }, 100)
            }
            binding.logoutbtn.visibility = if (connect == 1) {
                loginPopupShown = false
                View.VISIBLE
            } else {
                View.GONE
            }
            val currentUser = FirebaseAuth.getInstance().currentUser
            val currentUserEmail = currentUser?.email
            binding.addingBtn.visibility = if (currentUserEmail == "drorbakal17@gmail.com") {
                if (connect == 1) View.VISIBLE else View.GONE
            } else {
                View.GONE
            }
        }
        binding.addingBtn.setOnClickListener {
            val dialog = AddingPopUp()
            dialog.show(parentFragmentManager, "AddingPopUp")
        }
        binding.logoutbtn.setOnClickListener {
            authRep.logout()
            Toast.makeText(requireContext(),
                getString(R.string.logged_out_successfully), Toast.LENGTH_SHORT).show()
        }

        binding.serviceBtn.setOnClickListener {
            val dialog = ServiceDialogFragment()
            dialog.show(parentFragmentManager, "ServiceDialogFragment")
        }

        lifecycleScope.launch {
            authRep.currentuser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun EventChangeListner() {
        db = FirebaseFirestore.getInstance()
        db.collection("movies").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        moviesList.add(dc.document.toObject(Movie::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Check the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Adjust layout for landscape orientation
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager?.scrollToPosition(0) // Reset scroll position if needed
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Adjust layout for portrait orientation
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager?.scrollToPosition(0) // Reset scroll position if needed
        }
    }

}
