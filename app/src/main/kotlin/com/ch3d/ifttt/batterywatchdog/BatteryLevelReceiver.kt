package com.ch3d.ifttt.batterywatchdog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.os.BatteryManager.EXTRA_LEVEL
import android.text.TextUtils
import android.util.Log
import com.ch3d.ifttt.batterywatchdog.PrefrencesProvider.Companion.getDefaultDeviceName
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder.encode

class BatteryLevelReceiver : BroadcastReceiver() {

    companion object {
        val TAG = BatteryLevelReceiver::class.java.simpleName

        private val BASE_URL = "https://maker.ifttt.com/trigger/%s/with/key/%s?value1=%s&value2=%s"
        private val EVENT_BATTERY_LOW = "ANDROID_BATTERY_LOW"
        val BATTERY_LEVEL_UNKNOWN = "Unknown"
        val PARAMS_ENCODING = "UTF-8"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val batteryPercentage = getBatteryPercentage(context)
        val key = context.getIftttKey()

        if (TextUtils.isEmpty(key)) {
            return
        }

        val deviceName = if (context.isCustomNameEnabled())
            context.getCustomDeviceName() else getDefaultDeviceName()

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                try {
                    val strUrl = String.format(BASE_URL, EVENT_BATTERY_LOW, key,
                            encodeParam(deviceName), encodeParam(batteryPercentage))
                    val url = URL(strUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    try {
                        val responseCode = connection.responseCode
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            Log.v(TAG, "Battery report action status = " + responseCode)
                        }
                    } finally {
                        connection.disconnect()
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "", e)
                }
                return null
            }
        }.execute()
    }

    @Throws(UnsupportedEncodingException::class)
    private fun encodeParam(value: String?) = encode(value, PARAMS_ENCODING)

    private fun getBatteryPercentage(context: Context): String {
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, ifilter)
        if (batteryStatus != null) {
            val level = batteryStatus.getIntExtra(EXTRA_LEVEL, -1)
            return Integer.toString(level)
        }
        return BATTERY_LEVEL_UNKNOWN
    }
}
