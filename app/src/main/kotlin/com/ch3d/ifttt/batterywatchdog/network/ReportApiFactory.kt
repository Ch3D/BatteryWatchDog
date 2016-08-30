package com.ch3d.ifttt.batterywatchdog.network

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.ch3d.android.utils.StringUtils.Companion.EMPTY_STRING
import com.ch3d.ifttt.batterywatchdog.model.Rule
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

internal class ReportApiFactory {

    companion object {
        fun create(context: Context): ReportApi = ReportSender(context)
    }

    protected class ReportSender(val context: Context) : ReportApi {

        override fun report(data: Rule) {
            ReportAsyncTask(context).execute(data)
        }

        protected class ReportAsyncTask(val context: Context) : AsyncTask<Rule, Void, Boolean>() {

            companion object {
                val TAG = ReportAsyncTask::class.java.simpleName
            }

            private val PARAMS_ENCODING = "UTF-8"

            @Throws(UnsupportedEncodingException::class)
            private fun encodeParam(value: String?) = URLEncoder.encode(value, PARAMS_ENCODING)

            override fun doInBackground(vararg params: Rule?): Boolean {
                val rule = params[0]
                if (rule == null) {
                    Log.v(TAG, "Unable to battery report for rule = " + rule)
                    return false
                }

                try {
                    val url = URL("https://maker.ifttt.com/trigger/" +
                            "${rule.eventName()}/with/key/${rule.key()}?" +
                            "value1=${encodeParam(rule.data().value1)}&" +
                            "value2=${encodeParam(rule.data().value2)}")

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
                    Log.e(TAG, EMPTY_STRING, e)
                    return false
                }
            }

            private fun isSuccess(responseCode: Int) = (responseCode % 100) == 2
        }
    }
}
