package com.ch3d.ifttt.batterywatchdog.network

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.ch3d.android.utils.StringUtils.Companion.EMPTY_STRING
import com.ch3d.android.utils.StringUtils.Companion.isEmpty
import com.ch3d.ifttt.batterywatchdog.getApp
import com.ch3d.ifttt.batterywatchdog.model.Rule
import com.ch3d.ifttt.batterywatchdog.model.RuleData
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE1
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE2
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE3
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

internal class ReportApiFactory {

    companion object {
        fun create(context: Context): ReportApi = ReportSender(context)
        val ACTION_BATTERY_LOW_FAILED = "ACTION_BATTERY_LOW_FAILED"
        val ACTION_BATTERY_LOW_REPORTED = "ACTION_BATTERY_LOW_REPORTED"
        val ACTION_BATTERY_LOW_REPORT = "ACTION_BATTERY_LOW_REPORT"
    }

    protected class ReportSender(private val context: Context) : ReportApi {

        private val PARAMS_ENCODING = "UTF-8"

        companion object {
            val TAG = ReportSender::class.java.simpleName
        }

        fun getQueryString(data: RuleData): String {
            val val1 = data.value1
            val val2 = data.value2
            val val3 = data.value3
            if (isEmpty(val1) && isEmpty(val2) && isEmpty(val3)) {
                return EMPTY_STRING
            }
            return "?${VALUE1}=${encodeParam(val1)}&" +
                    "${VALUE2}=${encodeParam(val2)}&" +
                    "${VALUE3}=${encodeParam(val3)}"
        }

        @Throws(UnsupportedEncodingException::class)
        private fun encodeParam(value: String?) = URLEncoder.encode(value, PARAMS_ENCODING)

        override fun report(rule: Rule) {
            try {
                val url = URL("https://maker.ifttt.com/trigger/" +
                        "${rule.eventName()}/with/key/${rule.key()}${getQueryString(rule.data())}")

                val connection = url.openConnection() as HttpURLConnection
                try {
                    Log.v(TAG, "Starting report action = " + url)
                    reportSending(rule)

                    val responseCode = connection.responseCode

                    Log.v(TAG, "Battery report action status = " + responseCode)
                    reportSent(responseCode)
                } finally {
                    connection.disconnect()
                }
            } catch (e: IOException) {
                reportException(e)
                Log.e(TAG, EMPTY_STRING, e)
            }
        }

        private fun reportException(e: Exception) {
            context.getApp().firebaseAnalytics.logEvent(ACTION_BATTERY_LOW_FAILED, Bundle())
        }

        private fun reportSent(responseCode: Int) {
            val bundle = Bundle()
            bundle.putInt("responseCode", responseCode)
            context.getApp().firebaseAnalytics.logEvent(ACTION_BATTERY_LOW_REPORTED, bundle)
        }

        private fun reportSending(rule: Rule) {
            context.getApp().firebaseAnalytics.logEvent(ACTION_BATTERY_LOW_REPORT, rule.bundle())
        }
    }
}
