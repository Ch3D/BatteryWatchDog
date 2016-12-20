package com.ch3d.ifttt.batterywatchdog

import android.app.Application
import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    internal val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()

        Realm.setDefaultConfiguration(RealmConfiguration.Builder(this).build())

        firebaseAnalytics.setUserId("test-user-id")
        firebaseAnalytics.setUserProperty("userId", "my-user-id")
    }
}

fun Context.getApp(): App {
    return this.applicationContext as App
}
