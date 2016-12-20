package com.ch3d.ifttt.batterywatchdog.model

import android.content.ContentValues
import android.os.Bundle
import com.ch3d.ifttt.batterywatchdog.modelimport.BaseRule
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract

internal interface Rule {

    companion object {
        const val EXTRA_RULE = "rule"

        fun create(values: ContentValues): Rule {
            return BaseRule(values.getAsLong(RulesContract.RuleColumns.ID) ?: 0,
                    values.getAsString(RulesContract.RuleColumns.KEY),
                    values.getAsString(RulesContract.RuleColumns.EVENT_NAME),
                    RuleData(values)
            )
        }
    }

    fun setEnabled(enabled: Boolean)

    fun key(): String

    fun eventName(): String

    fun data(): RuleData

    fun contentValues(): ContentValues

    fun bundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(RulesContract.RuleColumns.VALUE1, data().value1)
        bundle.putString(RulesContract.RuleColumns.VALUE2, data().value2)
        bundle.putString(RulesContract.RuleColumns.VALUE3, data().value3)
        return bundle
    }
}