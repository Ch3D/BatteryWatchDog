package com.ch3d.ifttt.batterywatchdog

import android.app.IntentService
import android.content.Intent
import com.ch3d.ifttt.batterywatchdog.model.Rule
import com.ch3d.ifttt.batterywatchdog.modelimport.BaseRule
import com.ch3d.ifttt.batterywatchdog.network.ReportApiFactory
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class ReportIntentService : IntentService(ReportIntentService::class.java.simpleName) {

    private val PARAMS_ENCODING = "UTF-8"

    @Throws(UnsupportedEncodingException::class)
    private fun encodeParam(value: String?) = URLEncoder.encode(value, PARAMS_ENCODING)

    override fun onHandleIntent(intent: Intent?) {
        val rule = intent?.getParcelableExtra<BaseRule>(Rule.EXTRA_RULE) as BaseRule
        ReportApiFactory.create(this).report(rule)
    }
}
