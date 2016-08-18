package com.ch3d.ifttt.batterywatchdog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_CHANGED
import android.content.IntentFilter
import android.os.BatteryManager.EXTRA_LEVEL
import android.text.TextUtils
import com.ch3d.ifttt.batterywatchdog.PrefrencesProvider.Companion.getDefaultDeviceName

class BatteryLevelReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val key = context.getIftttKey()
        if (TextUtils.isEmpty(key)) {
            return
        }

        val deviceName = if (context.isCustomNameEnabled())
            context.getCustomDeviceName() else getDefaultDeviceName()

        val event = if (context.isCustomEventEnabled())
            context.getCustomEventName() else ReportData.EVENT_BATTERY_LOW

        ReportData(key!!, event!!, deviceName!!, getBatteryPercentage(context))
        ReportAsyncTask().execute()
    }

    private fun getBatteryPercentage(context: Context): String {
        val batteryStatus = context.registerReceiver(null, IntentFilter(ACTION_BATTERY_CHANGED))
        val level = batteryStatus?.getIntExtra(EXTRA_LEVEL, -1) ?: 0
        return Integer.toString(level)
    }
}
