package com.ch3d.ifttt.batterywatchdog

import android.app.Service
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_LOW
import android.content.IntentFilter
import android.os.IBinder

class BatteryService : Service() {

    private var mLevelReceiver: BatteryLevelReceiver? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mLevelReceiver = BatteryLevelReceiver()
        registerReceiver(mLevelReceiver, IntentFilter(ACTION_BATTERY_LOW))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mLevelReceiver)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }
}
