package com.ch3d.ifttt.batterywatchdog.provider

import android.net.Uri

/**
 * TODO add javadocs
 */
object RulesContract {
    const val AUTHORITY = "com.ch3d.ifttt"

    val RULES_URI = Uri.parse("content://${AUTHORITY}/rules")
    val DISPATCHER_URI = Uri.parse("content://${AUTHORITY}/dispatcher")

    val PERMISSION_READ = "${AUTHORITY}.provider.permission.READ_RULES"

    object RuleColumns {
        val ID = "_id"
        val KEY = "key"
        val EVENT_NAME = "event_name"
        val VALUE_1 = "value_1"
        val VALUE_2 = "value_2"
        val VALUE_3 = "value_3"
    }

    // Generate URI for a specific task's notes
    fun getTaskNotesUri(ruleId: Int): Uri {
        return Uri.withAppendedPath(RULES_URI, ruleId.toString())
    }

    val URI_TYPE_RULE_ITEM = "vnd.android.cursor.item/vnd.batterwatchdog.rule"
}
