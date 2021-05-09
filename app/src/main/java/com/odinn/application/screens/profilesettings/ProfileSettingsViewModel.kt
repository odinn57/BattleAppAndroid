package com.odinn.application.screens.profilesettings

import com.google.android.gms.tasks.OnFailureListener
import com.odinn.application.common.AuthManager
import com.odinn.application.screens.common.BaseViewModel

class ProfileSettingsViewModel(private val authManager: AuthManager,
                               onFailureListener: OnFailureListener) : BaseViewModel(onFailureListener),
        AuthManager by authManager {             //делегируем реализацию интерфейса передаваемому параметру

}

