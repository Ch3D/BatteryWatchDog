package com.ch3d.ifttt.batterywatchdog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_CHANGED
import android.content.IntentFilter
import android.os.BatteryManager.EXTRA_LEVEL
import android.text.TextUtils
import com.ch3d.ifttt.batterywatchdog.PrefrencesProvider.Companion.getDefaultDeviceName
import com.ch3d.ifttt.batterywatchdog.model.BaseRule
import com.ch3d.ifttt.batterywatchdog.model.RuleData
import com.ch3d.ifttt.batterywatchdog.network.ReportApiFactory
import com.ch3d.ifttt.batterywatchdog.utils.getCustomDeviceName
import com.ch3d.ifttt.batterywatchdog.utils.getIftttKey
import com.ch3d.ifttt.batterywatchdog.utils.isCustomNameEnabled
import com.ch3d.ifttt.batterywatchdog.utils.isReportingEnabled

class BatteryLevelReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (!context.isReportingEnabled()) {
            return
        }

        val key = context.getIftttKey()
        if (TextUtils.isEmpty(key)) {
            return
        }

        val deviceName = if (context.isCustomNameEnabled())
            context.getCustomDeviceName() else getDefaultDeviceName()

        ReportApiFactory
                .create(context)
                .report(
                        BaseRule(0, key!!, BaseRule.EVENT_BATTERY_LOW,
                                RuleData(deviceName!!, getBatteryPercentage(context)))
                )
    }

    private fun getBatteryPercentage(context: Context): String {
        val batteryStatus = context.registerReceiver(null, IntentFilter(ACTION_BATTERY_CHANGED))
        val level = batteryStatus?.getIntExtra(EXTRA_LEVEL, -1) ?: 0
        return level.toString()
    }
}
