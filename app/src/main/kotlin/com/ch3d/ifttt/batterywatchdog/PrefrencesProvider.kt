package com.ch3d.ifttt.batterywatchdog

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.ch3d.ifttt.batterywatchdog.PrefrencesProvider.Companion

class PrefrencesProvider {
    companion object {
        private val DEFAULT_RESULT = ""

        private val IFTTT_KEY = "com.ch3d.ifttt.batterywatchdog.ifttt_key"
        private val CUSTOM_DEVICE_ENABLED = "com.ch3d.ifttt.batterywatchdog.custom_device_enabled"
        private val CUSTOM_EVENT_ENABLED = "com.ch3d.ifttt.batterywatchdog.custom_event_enabled"
        private val CUSTOM_DEVICE_NAME = "com.ch3d.ifttt.batterywatchdog.custom_device_name"
        private val CUSTOM_EVENT_NAME = "com.ch3d.ifttt.batterywatchdog.custom_event_name"

        fun getString(context: Context, key: String, default: String? = DEFAULT_RESULT): String? =
                getDefaultSharedPreferences(context).getString(key, default)

        fun getBool(context: Context, key: String) =
                getDefaultSharedPreferences(context).getBoolean(key, false)

        fun getCustomDeviceName(context: Context) = getString(context, CUSTOM_DEVICE_NAME, null)

        fun getCustomEventName(context: Context) = getString(context, CUSTOM_EVENT_NAME, null)

        fun getIftttKey(context: Context) = getString(context, IFTTT_KEY)

        fun isCustomNameEnabled(context: Context) = getBool(context, CUSTOM_DEVICE_ENABLED)

        fun isCustomEventEnabled(context: Context) = getBool(context, CUSTOM_DEVICE_ENABLED)

        fun setCustomNameEnabled(context: Context, enabled: Boolean) =
                getDefaultSharedPreferences(context).edit()
                        .putBoolean(CUSTOM_DEVICE_ENABLED, enabled).commit()

        fun setCustomEventEnabled(context: Context, enabled: Boolean) =
                getDefaultSharedPreferences(context).edit()
                        .putBoolean(CUSTOM_EVENT_NAME, enabled).commit()

        fun saveIftttKey(context: Context, key: String) =
                getDefaultSharedPreferences(context).edit()
                        .putString(IFTTT_KEY, key).commit()

        fun getDefaultDeviceName() = Build.BRAND + " " + Build.MODEL

        fun saveCustomDeviceName(context: Context, deviceName: String) =
                getDefaultSharedPreferences(context).edit()
                        .putString(CUSTOM_DEVICE_NAME, deviceName).commit()

        fun saveCustomEventName(context: Context, deviceName: String) =
                getDefaultSharedPreferences(context).edit()
                        .putString(CUSTOM_EVENT_NAME, deviceName).commit()
    }
}

fun Context.isCustomNameEnabled() = Companion.isCustomNameEnabled(this)
fun Context.setCustomNameEnabled(enabled: Boolean) = Companion.setCustomNameEnabled(this, enabled)
fun Context.getCustomDeviceName() = Companion.getCustomDeviceName(this)
fun Context.saveCustomDeviceName(name: String) = Companion.saveCustomDeviceName(this, name)

fun Context.isCustomEventEnabled() = Companion.isCustomEventEnabled(this)
fun Context.setCustomEventEnabled(enabled: Boolean) = Companion.setCustomEventEnabled(this, enabled)
fun Context.getCustomEventName() = Companion.getCustomEventName(this)
fun Context.saveCustomEventName(name: String) = Companion.saveCustomEventName(this, name)

fun Context.getIftttKey() = Companion.getIftttKey(this)
fun Context.saveIftttKey(key: String) = Companion.saveIftttKey(this, key)