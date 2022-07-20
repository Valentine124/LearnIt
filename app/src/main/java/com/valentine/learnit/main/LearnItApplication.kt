package com.valentine.learnit.main

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import androidx.work.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.valentine.learnit.R
import com.valentine.learnit.backgroundwork.NotificationWorker
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.db.RecentCourses
import com.valentine.learnit.internet.udemyapi.UdemyApi
import java.util.concurrent.TimeUnit

class LearnItApplication : Application(), SharedPreferences.OnSharedPreferenceChangeListener {

    val repository: LearnItRepository by lazy {
        LearnItRepository(UdemyApi.udemyService)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private val visitedCourses = mutableListOf<RecentCourses>()

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        database = Firebase.database
        database.setPersistenceEnabled(true)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val boolValue = pref.getBoolean(getString(R.string.toggle_theme_pref), true)

        if (boolValue) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }

        auth = FirebaseAuth.getInstance()
        pref.registerOnSharedPreferenceChangeListener(this)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        if (key?.equals(getString(R.string.toggle_theme_pref)) == true) {
            val toggleTheme = pref.getBoolean(key, false)
            if (toggleTheme) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
        if (key?.equals(getString(R.string.reminder_pref)) == true) {
            val currentUser = auth.currentUser
            val userRef = currentUser?.let {
                database.getReference("users").child(it.uid).child("visited")
            }

            userRef?.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    visitedCourses.clear()
                    if (snapshot.exists()) {
                        for (visited in snapshot.children) {

                            val data = visited.getValue(RecentCourses::class.java)
                            visitedCourses.add(data!!)
                        }
                        sendNotification(visitedCourses)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    WorkManager.getInstance(this@LearnItApplication).cancelAllWork()
                }
            })
        } else {
            WorkManager.getInstance(this).cancelAllWork()
        }
    }

    private fun sendNotification(visitedCourses: MutableList<RecentCourses>) {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val visited = visitedCourses.random()

        val data = workDataOf(NOTIFY_TITLE to visited.title,
            NOTIFY_IMAGE to visited.image_480x270, NOTIFY_URL to visited.url)

        val notifyWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraint)
            .setInputData(data)
            .build()


        WorkManager.getInstance(this).enqueue(notifyWorkRequest)
    }

}