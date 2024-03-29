package com.odinn.application.screens.common

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.util.Log
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.odinn.application.R
import com.odinn.application.screens.home.HomeActivity
import com.odinn.application.screens.notifications.NotificationsActivity
import com.odinn.application.screens.profile.ProfileActivity
import com.odinn.application.screens.search.SearchActivity
import com.odinn.application.screens.share.ShareActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.*

class PhotoBattleBottomNavigation(private val bnv: BottomNavigationViewEx,
                                  private val navNumber : Int,
                                  activity : Activity) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        bnv.menu.getItem(navNumber).isChecked = true
    }

    init {
        bnv.setIconSize(29f, 29f)
        bnv.setTextVisibility(false)
        bnv.enableItemShiftingMode(false)
        bnv.enableShiftingMode(false)
        bnv.enableAnimation(false)
        for (i in 0 until bnv.menu.size()) {
            bnv.setIconTintList(i, null)
        }
        bnv.setOnNavigationItemSelectedListener {
            val nextActivity =
                    when (it.itemId) {
                        R.id.nav_item_home -> HomeActivity::class.java
                        R.id.nav_item_search -> SearchActivity::class.java
                        R.id.nav_item_share -> ShareActivity::class.java
                        R.id.nav_item_likes -> NotificationsActivity::class.java
                        R.id.nav_item_profile -> ProfileActivity::class.java
                        else -> {
                            Log.e(BaseActivity.TAG, "unknown nav item clicked $it")
                            null
                        }
                    }
            if (nextActivity != null) {
                val intent = Intent(activity, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                activity.startActivity(intent)
                activity.overridePendingTransition(0, 0)
                true
            } else {
                false
            }
        }
    }
}

fun BaseActivity.setupBottomNavigation(navNumber : Int){
    val bnv = PhotoBattleBottomNavigation(bottom_navigation_view, navNumber, this)
    this.lifecycle.addObserver(bnv)
}