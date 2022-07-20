package com.valentine.learnit.ui.authentication

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.valentine.learnit.LearnItActivity
import com.valentine.learnit.databinding.ActivityLogInBinding
import com.valentine.learnit.main.CLIENT_ID


class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String = ""
    private var passWord: String = ""

    private val googleSignIn =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val pendingIntent = result.data
                try {
                    val credential = Identity.getSignInClient(this)
                        .getSignInCredentialFromIntent(pendingIntent)
                    handleResult(credential)
                } catch (e: ApiException) {
                    Toast.makeText(this, "Google Sign in Failed due to ${e.status}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private lateinit var callbackManager: CallbackManager




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.gmailIcon.setOnClickListener {
            signInWithGoogle()
        }

        binding.facebookIcon.setOnClickListener {
            signInWithFacebook()
        }

        binding.logInButton.setOnClickListener{
            validateUser()
        }

        binding.linkSignUp.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }

    private fun signInWithFacebook() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().
        logInWithReadPermissions(this, callbackManager,listOf("email", "public profile"))

        LoginManager.getInstance().registerCallback(callbackManager, object: FacebookCallback<LoginResult>{

            override fun onSuccess(result: LoginResult) {
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(this@LogInActivity,
                    "Facebook Login Cancelled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@LogInActivity,
                    "Facebook Login Failed due to ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Toast.makeText(this, "Logged in as ${user!!.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LearnItActivity::class.java))
                } else {
                    Toast.makeText(this,
                        "Facebook Auth Failed due to ${task.exception!!.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithGoogle() {
        val request: GetSignInIntentRequest = GetSignInIntentRequest.builder()
            .setServerClientId(CLIENT_ID)
            .build()

        Identity.getSignInClient(this)
            .getSignInIntent(request)
            .addOnSuccessListener { result ->
                try {
                    googleSignIn.launch(IntentSenderRequest.Builder(result.intentSender).build())
                }catch (e: IntentSender.SendIntentException) {
                    Toast.makeText(this, "Google Sign in Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Google Sign in Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleResult(credential: SignInCredential) {

        val idToken = credential.googleIdToken
        when {
            idToken != null -> {
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuth.signInWithCredential(firebaseCredential)
                    .addOnSuccessListener(this) {
                        val user = firebaseAuth.currentUser
                        Toast.makeText(this, "Logged in as ${user!!.email}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LearnItActivity::class.java))

                    }
                    .addOnFailureListener(this) {
                        Toast.makeText(this, "Google Sign in Failed due to ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            } else -> {
                Toast.makeText(this, "No ID token", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUser() {
        email = binding.emailText.text.toString().trim()
        passWord = binding.passwordText.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailText.error = "Invalid email format"
        }else if (TextUtils.isEmpty(passWord)){
            binding.passwordText.error = "Please enter password"
        }else {
            loginUser()
        }

    }

    private fun loginUser() {
        firebaseAuth.signInWithEmailAndPassword(email, passWord)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LearnItActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login failed due to ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, LearnItActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}