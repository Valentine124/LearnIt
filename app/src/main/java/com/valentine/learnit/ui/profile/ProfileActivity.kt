package com.valentine.learnit.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.valentine.learnit.databinding.ActivityProfileBinding
import com.valentine.learnit.ui.authentication.LogInActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.profileToolbar.apply {
            title = "Profile"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            this.setNavigationOnClickListener {
                this@ProfileActivity.onBackPressed()
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()

        getUser()

        binding.SignOutButton.setOnClickListener {
            signOutUser()
        }

        binding.logInButton.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }
    }

    private fun signOutUser() {
        firebaseAuth.signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }

    private fun getUser() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            binding.signInLayout.visibility = View.GONE

            binding.profileImage.apply {
                load(currentUser.photoUrl)
                visibility = View.VISIBLE
            }
            binding.profileName.apply {
                text = currentUser.displayName
                visibility = View.VISIBLE
            }
            binding.profileEmail.apply {
                text = currentUser.email
                visibility = View.VISIBLE
            }
        } else {
            binding.profileImage.visibility = View.GONE
            binding.profileName.visibility = View.GONE
            binding.profileEmail.visibility = View.GONE
            binding.SignOutButton.visibility = View.GONE
            binding.name.visibility = View.GONE
            binding.email.visibility = View.GONE

            binding.signInLayout.visibility = View.VISIBLE
        }
    }
}