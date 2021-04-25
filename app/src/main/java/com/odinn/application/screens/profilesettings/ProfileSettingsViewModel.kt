package com.odinn.application.screens.profilesettings

import android.arch.lifecycle.ViewModel
import com.odinn.application.common.AuthManager

class ProfileSettingsViewModel(private val authManager: AuthManager) :ViewModel(),
        AuthManager by authManager{             //делегируем реализацию интерфейса передаваемому параметру

        }

