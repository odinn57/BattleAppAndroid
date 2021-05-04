package com.odinn.application.screens.likes

import android.os.Bundle
import android.util.Log
import com.odinn.application.R
import com.odinn.application.screens.common.BaseActivity
import com.odinn.application.screens.common.setupAuthGuard
import com.odinn.application.screens.common.setupBottomNavigation

class LikesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation(3)
        Log.d(TAG, "onCreate")
        setupAuthGuard {  }
    }

    companion object{
        const val TAG = "LikesActivity"
    }
}
