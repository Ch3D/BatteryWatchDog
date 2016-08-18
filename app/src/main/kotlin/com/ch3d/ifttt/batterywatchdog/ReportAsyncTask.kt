package com.ch3d.ifttt.batterywatchdog

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ReportAsyncTask : AsyncTask<String, Void, Boolean>() {

    companion object {
        val TAG = ReportAsyncTask::class.java.simpleName
    }

    private val BASE_URL = "https://maker.ifttt.com/trigger/%s/with/key/%s?value1=%s&value2=%s"
    private val EVENT_BATTERY_LOW = "ANDROID_BATTERY_LOW"
    private val PARAMS_ENCODING = "UTF-8"

    @Throws(UnsupportedEncodingException::class)
    private fun encodeParam(value: String?) = URLEncoder.encode(value, PARAMS_ENCODING)

    override fun doInBackground(vararg params: String?): Boolean {
        val key = params[0]
        val deviceName = params[1]
        val batteryPercentage = params[2]

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
                return responseCode == 200
            } finally {
                connection.disconnect()
            }
        } catch (e: IOException) {
            Log.e(TAG, "", e)
        }
        return false
    }
}
