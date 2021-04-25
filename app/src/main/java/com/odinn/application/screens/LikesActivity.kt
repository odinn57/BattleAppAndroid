package com.odinn.application.screens

import android.os.Bundle
import android.util.Log
import com.odinn.application.R
import com.odinn.application.screens.common.BaseActivity
import com.odinn.application.screens.common.setupBottomNavigation

class LikesActivity : BaseActivity() {
    private val TAG = "LikesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation(3)
        Log.d(TAG, "onCreate")
    }
}
