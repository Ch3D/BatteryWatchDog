package com.ch3d.ifttt.batterywatchdog

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_CHANGED
import android.content.IntentFilter
import android.os.BatteryManager.EXTRA_LEVEL
import android.support.v4.content.WakefulBroadcastReceiver
import android.text.TextUtils
import com.ch3d.ifttt.batterywatchdog.PreferencesProvider.Companion.getDefaultDeviceName
import com.ch3d.ifttt.batterywatchdog.model.Rule
import com.ch3d.ifttt.batterywatchdog.model.RuleData
import com.ch3d.ifttt.batterywatchdog.modelimport.BaseRule
import com.ch3d.ifttt.batterywatchdog.utils.*

class BatteryLevelReceiver : WakefulBroadcastReceiver() {

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

        val event = if (context.isCustomEventEnabled())
            context.getCustomEventName() else RuleData.EVENT_BATTERY_LOW

        val data = BaseRule(0, key!!, event!!,
                RuleData(deviceName!!, getBatteryPercentage(context)))

        val reportIntent = Intent(context, ReportIntentService::class.java)
        reportIntent.putExtra(Rule.EXTRA_RULE, data)
        context.startService(reportIntent)
    }

    private fun getBatteryPercentage(context: Context): String {
        val batteryStatus = context.registerReceiver(null, IntentFilter(ACTION_BATTERY_CHANGED))
        val level = batteryStatus?.getIntExtra(EXTRA_LEVEL, -1) ?: 0
        return level.toString()
    }
}
