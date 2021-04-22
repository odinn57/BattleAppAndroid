package com.odinn.application.activities

import android.os.Bundle
import android.util.Log
import com.odinn.application.R
import com.odinn.application.views.setupBottomNavigation

class LikesActivity : BaseActivity() {
    private val TAG = "LikesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation(3)
        Log.d(TAG, "onCreate")
    }
}
