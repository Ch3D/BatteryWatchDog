package com.ch3d.ifttt.batterywatchdog

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_CHANGED
import android.content.IntentFilter
import android.os.BatteryManager.EXTRA_LEVEL
import android.support.v4.content.WakefulBroadcastReceiver
import com.ch3d.ifttt.batterywatchdog.PreferencesProvider.Companion.getDefaultDeviceName
import com.ch3d.ifttt.batterywatchdog.model.Rule.Companion.EXTRA_RULE
import com.ch3d.ifttt.batterywatchdog.model.RuleData
import com.ch3d.ifttt.batterywatchdog.modelimport.BaseRule
import com.ch3d.ifttt.batterywatchdog.utils.*

class BatteryLevelReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (!context.isReportingEnabled()) {
            return
        }

        val key = context.getIftttKey()
        if (!key.isNullOrBlank()) {
            val ruleData = RuleData(getDeviceName(context), getBatteryPercentage(context))
            val data = BaseRule(0, key!!, getEventName(context), ruleData)

            val reportIntent = Intent(context, ReportIntentService::class.java)
                    .putExtra(EXTRA_RULE, data)
            context.startService(reportIntent)
        }
    }

    private fun getEventName(context: Context) =
            if (context.isCustomEventEnabled()) context.getCustomEventName() ?: RuleData.EVENT_BATTERY_LOW
            else RuleData.EVENT_BATTERY_LOW

    private fun getDeviceName(context: Context) =
            if (context.isCustomNameEnabled()) context.getCustomDeviceName() ?: getDefaultDeviceName()
            else getDefaultDeviceName()

    private fun getBatteryPercentage(context: Context): String {
        val batteryStatus = context.registerReceiver(null, IntentFilter(ACTION_BATTERY_CHANGED))
        val level = batteryStatus?.getIntExtra(EXTRA_LEVEL, -1) ?: 0
        return level.toString()
    }
}
