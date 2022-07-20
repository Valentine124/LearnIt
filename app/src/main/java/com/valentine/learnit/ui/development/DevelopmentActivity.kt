package com.valentine.learnit.ui.development

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.valentine.learnit.main.BASE_URL
import com.valentine.learnit.main.COURSE_URL_STRING
import com.valentine.learnit.dashboard.CourseDashboardActivity
import com.valentine.learnit.main.LearnItApplication
import com.valentine.learnit.main.ResultComparator
import com.valentine.learnit.databinding.ActivityDevelopmentBinding
import com.valentine.learnit.db.RecentCourses
import com.valentine.learnit.internet.Result
import com.valentine.learnit.ui.OnItemClickedListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DevelopmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDevelopmentBinding
    private val database = Firebase.database.reference
    private lateinit var firebaseAuth: FirebaseAuth

    private val model: DevelopmentViewModel by viewModels {
        DevelopmentFactory((application as LearnItApplication).repository)
    }

    private val adapters = DevelopmentAdapter(ResultComparator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevelopmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar3.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            this.setNavigationOnClickListener {
                this@DevelopmentActivity.onBackPressed()
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        binding.developmentList.apply {
            this.layoutManager = LinearLayoutManager(this@DevelopmentActivity)
            this.adapter = adapters
        }

        lifecycleScope.launch {
            model.getDevelopmentCourses().collectLatest {
                adapters.submitData(it)
            }
        }

        adapters.setItemClickListener(object : OnItemClickedListener {
            override fun onItemClickedListener(result: Result) {
                val intent = Intent(this@DevelopmentActivity, CourseDashboardActivity::class.java)
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