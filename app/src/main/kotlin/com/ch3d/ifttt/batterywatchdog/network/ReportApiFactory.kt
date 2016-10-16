package com.ch3d.ifttt.batterywatchdog.network

import android.util.Log
import com.ch3d.android.utils.StringUtils.Companion.EMPTY_STRING
import com.ch3d.android.utils.StringUtils.Companion.isEmpty
import com.ch3d.ifttt.batterywatchdog.model.Rule
import com.ch3d.ifttt.batterywatchdog.model.RuleData
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

internal class ReportApiFactory {

    companion object {
        fun create(): ReportApi = ReportSender()
    }

    protected class ReportSender() : ReportApi {

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
            return "?value1=${encodeParam(val1)}&" +
                    "value2=${encodeParam(val2)}&" +
                    "value3=${encodeParam(val3)}"
        }

        @Throws(UnsupportedEncodingException::class)
        private fun encodeParam(value: String?) = URLEncoder.encode(value, PARAMS_ENCODING)

        override fun report(rule: Rule) {
            try {
                val url = URL("https://maker.ifttt.com/trigger/" +
                        "${rule.eventName()}/with/key/${rule.key()}${getQueryString(rule.data())}"
                )

                val connection = url.openConnection() as HttpURLConnection
                try {
                    Log.v(TAG, "Starting report action = " + url)
                    val responseCode = connection.responseCode
                    Log.v(TAG, "Battery report action status = " + responseCode)
                } finally {
                    connection.disconnect()
                }
            } catch (e: IOException) {
                Log.e(TAG, EMPTY_STRING, e)
            }
        }
    }
}
