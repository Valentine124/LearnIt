package com.valentine.learnit.ui.learning

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.valentine.learnit.dashboard.CourseDashboardActivity
import com.valentine.learnit.databinding.FragmentLearningsBinding
import com.valentine.learnit.db.RecentCourses
import com.valentine.learnit.main.BASE_URL
import com.valentine.learnit.main.COURSE_URL_STRING

class LearningsFragment : Fragment() {

    private var _binding: FragmentLearningsBinding? = null
    private val binding get() = _binding!!

    private val database = Firebase.database
    private lateinit var firebaseAuth: FirebaseAuth

    private val model: LearningsViewModel by viewModels()

    private val adapters = LearningAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLearningsBinding.inflate(inflater, container, false)

        binding.learningToolbar.apply {
            title = "Visited Courses"
        }
        firebaseAuth = FirebaseAuth.getInstance()
        addVisitedCourse()
        showVisited()

        requireActivity().addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        })

        binding.visitedList.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapters
        }


        adapters.setOnLearningsClickListener(object : OnLearningsClickListener{
            override fun onLearningsClick(recent: RecentCourses) {
                val intent = Intent(requireContext(), CourseDashboardActivity::class.java)
                intent.putExtra(COURSE_URL_STRING, BASE_URL + recent.url)
                startActivity(intent)
            }
        })

        return binding.root
    }

    private fun showVisited() {

    }

    private fun addVisitedCourse() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            binding.noItemText.visibility = View.INVISIBLE
            binding.visitedList.visibility = View.VISIBLE
        }else {
            binding.noItemText.visibility = View.VISIBLE
            binding.visitedList.visibility = View.INVISIBLE
        }
        val userRef = currentUser?.let {
            database.getReference("users").child(it.uid).child("visited")
        }
        userRef?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                model.visitedList.clear()
                if (snapshot.exists()) {
                    for (user in snapshot.children) {
                        val data = user.getValue(RecentCourses::class.java)
                            model.visitedList.add(data!!)
                    }
                    adapters.submitList(model.visitedList.reversed())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                model.visitedList.clear()
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}