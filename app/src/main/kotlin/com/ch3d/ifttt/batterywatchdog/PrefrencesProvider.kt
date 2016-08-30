package com.ch3d.ifttt.batterywatchdog

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.ch3d.android.utils.StringUtils

class PrefrencesProvider {
    companion object {
        private val DEFAULT_RESULT = ""

        private val IFTTT_KEY = "${BuildConfig.APPLICATION_ID}.ifttt_key"
        private val CUSTOM_DEVICE_ENABLED = "${BuildConfig.APPLICATION_ID}.custom_device_enabled"
        private val CUSTOM_EVENT_ENABLED = "${BuildConfig.APPLICATION_ID}.custom_event_enabled"
        private val CUSTOM_DEVICE_NAME = "${BuildConfig.APPLICATION_ID}.custom_device_name"
        private val CUSTOM_EVENT_NAME = "${BuildConfig.APPLICATION_ID}.custom_event_name"
        private val REPORTING_ENABLED = "${BuildConfig.APPLICATION_ID}.reporting_enabled"

        fun getString(context: Context, key: String, default: String? = DEFAULT_RESULT): String? =
                getDefaultSharedPreferences(context).getString(key, default)

        fun getBool(context: Context, key: String) =
                getDefaultSharedPreferences(context).getBoolean(key, false)

        fun getCustomDeviceName(context: Context) = getString(context, CUSTOM_DEVICE_NAME, null)

        fun getCustomEventName(context: Context) = getString(context, CUSTOM_EVENT_NAME, null)

        fun getIftttKey(context: Context) = getString(context, IFTTT_KEY)

        fun isCustomNameEnabled(context: Context) = getBool(context, CUSTOM_DEVICE_ENABLED)

        fun isCustomEventEnabled(context: Context) = getBool(context, CUSTOM_EVENT_ENABLED)

        fun setCustomNameEnabled(context: Context, enabled: Boolean) =
                getDefaultSharedPreferences(context).edit()
                        .putBoolean(CUSTOM_DEVICE_ENABLED, enabled).commit()

        fun setCustomEventEnabled(context: Context, enabled: Boolean) =
                getDefaultSharedPreferences(context).edit()
                        .putBoolean(CUSTOM_EVENT_ENABLED, enabled).commit()

        fun saveIftttKey(context: Context, key: String) =
                getDefaultSharedPreferences(context).edit()
                        .putString(IFTTT_KEY, key).commit()

        fun getDefaultDeviceName() = Build.BRAND + StringUtils.SPACE + Build.MODEL

        fun saveCustomDeviceName(context: Context, deviceName: String) =
                getDefaultSharedPreferences(context).edit()
                        .putString(CUSTOM_DEVICE_NAME, deviceName).commit()

        fun saveCustomEventName(context: Context, deviceName: String) =
                getDefaultSharedPreferences(context).edit()
                        .putString(CUSTOM_EVENT_NAME, deviceName).commit()

        fun isReportinEnabled(context: Context) =
                getDefaultSharedPreferences(context).getBoolean(REPORTING_ENABLED, true)

        fun setReportinEnabled(context: Context, enabled: Boolean) =
                getDefaultSharedPreferences(context).edit()
                        .putBoolean(REPORTING_ENABLED, enabled).commit()
    }
}

