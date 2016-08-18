package com.ch3d.ifttt.batterywatchdog

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ReportAsyncTask : AsyncTask<ReportData, Void, Boolean>() {

    companion object {
        val TAG = ReportAsyncTask::class.java.simpleName
    }

    private val PARAMS_ENCODING = "UTF-8"

    @Throws(UnsupportedEncodingException::class)
    private fun encodeParam(value: String?) = URLEncoder.encode(value, PARAMS_ENCODING)

    override fun doInBackground(vararg params: ReportData?): Boolean {
        val data = params[0]
        if (data == null) {
            Log.v(TAG, "Unable to battery report for data = " + data)
            return false
        }

        try {
            val url = URL("https://maker.ifttt.com/trigger/" +
                    "${data?.event}/with/key/${data?.key}?" +
                    "value1=${encodeParam(data?.deviceName)}&" +
                    "value2=${encodeParam(data?.batteryPercentage)}")
            
            val connection = url.openConnection() as HttpURLConnection
            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.v(TAG, "Battery report action status = " + responseCode)
                }
                return isSuccess(responseCode)
            } finally {
                connection.disconnect()
            }
        } catch (e: IOException) {
            Log.e(TAG, "", e)
            return false
        }
    }

    private fun isSuccess(responseCode: Int) = (responseCode % 100) == 2
}
