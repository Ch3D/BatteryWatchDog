package com.ch3d.ifttt.batterywatchdog

import android.app.IntentService
import android.content.Intent
import com.ch3d.ifttt.batterywatchdog.model.Rule.Companion.EXTRA_RULE
import com.ch3d.ifttt.batterywatchdog.modelimport.BaseRule
import com.ch3d.ifttt.batterywatchdog.network.ReportApiFactory

class ReportIntentService : IntentService(ReportIntentService::class.java.simpleName) {

    override fun onHandleIntent(intent: Intent?) {
        ReportApiFactory.create(this).report(
                intent?.getParcelableExtra<BaseRule>(EXTRA_RULE) as BaseRule)
    }
}
