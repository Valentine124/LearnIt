package com.valentine.learnit.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.valentine.learnit.main.COURSE_URL_STRING
import com.valentine.learnit.databinding.ActivityCourseDashboardBinding

class CourseDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseDashboardBinding
    private val webView by lazy {
        binding.webView
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
        setContentView(view)

        val uri = intent.getStringExtra(COURSE_URL_STRING)
        webView.loadUrl(uri!!)

    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}