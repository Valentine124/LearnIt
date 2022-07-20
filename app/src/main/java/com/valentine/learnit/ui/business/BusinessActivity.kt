package com.valentine.learnit.ui.business

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.valentine.learnit.dashboard.CourseDashboardActivity
import com.valentine.learnit.databinding.ActivityBusinessBinding
import com.valentine.learnit.db.RecentCourses
import com.valentine.learnit.internet.Result
import com.valentine.learnit.loadstate.PagedListLoadStateAdapter
import com.valentine.learnit.main.BASE_URL
import com.valentine.learnit.main.COURSE_URL_STRING
import com.valentine.learnit.main.LearnItApplication
import com.valentine.learnit.main.ResultComparator
import com.valentine.learnit.ui.OnItemClickedListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BusinessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessBinding
    private val database = Firebase.database.reference
    private lateinit var firebaseAuth: FirebaseAuth

    private val model: BusinessViewModel by viewModels {
        BusinessFactory((application as LearnItApplication).repository)
    }

    private val adapters = BusinessAdapter(ResultComparator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.toolbar2.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                this@BusinessActivity.onBackPressed()
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        binding.businessList.apply {
            this.layoutManager = LinearLayoutManager(this@BusinessActivity)
            this.adapter = adapters
            this.adapter = adapters.withLoadStateHeaderAndFooter(
                footer = PagedListLoadStateAdapter(),
                header = PagedListLoadStateAdapter()
            )
        }

        lifecycleScope.launch {
            model.getBusinessCourses().collectLatest {
                adapters.submitData(it)
            }
        }

        adapters.setOnItemClicked(object : OnItemClickedListener{
            override fun onItemClickedListener(result: Result) {
                val intent = Intent(this@BusinessActivity, CourseDashboardActivity::class.java)
                intent.putExtra(COURSE_URL_STRING, BASE_URL + result.url)
                startActivity(intent)

                val id = result.id
                val trackingId = result.tracking_id
                val image = result.image_480x270
                val price = result.price
                val url = result.url
                val instructors = result.visible_instructors
                val title = result.title

                val recentCourse = RecentCourses(trackingId, id, title, image, price, url, instructors)

                currentUser?.let {
                    database.child("users").child(it.uid).child("visited").child("$id").setValue(recentCourse)
                }
            }

        })

    }

}