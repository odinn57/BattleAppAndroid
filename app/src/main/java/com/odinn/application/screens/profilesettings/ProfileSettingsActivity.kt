package com.odinn.application.screens.profilesettings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.odinn.application.R
import com.odinn.application.data.firebase.common.FirebaseHelper
import com.odinn.application.screens.common.BaseActivity
import com.odinn.application.screens.common.setupAuthGuard
import kotlinx.android.synthetic.main.activity_profile_settings.*

class ProfileSettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        setupAuthGuard {
            val viewModel = initViewModel<ProfileSettingsViewModel>()
            sign_out_text.setOnClickListener { viewModel.signOut() }
            back_image.setOnClickListener { finish() }
        }

    }
}
