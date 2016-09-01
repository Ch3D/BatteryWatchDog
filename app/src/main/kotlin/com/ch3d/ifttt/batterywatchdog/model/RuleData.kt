package com.ch3d.ifttt.batterywatchdog.model

import android.content.ContentValues
import com.ch3d.android.utils.StringUtils.Companion.EMPTY_STRING
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE_1
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE_2
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE_3
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
internal open class RuleData(var value1: String = EMPTY_STRING,
                             var value2: String = EMPTY_STRING,
                             var value3: String = EMPTY_STRING) : RealmObject() {

    constructor() : this(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)

    constructor(values: ContentValues) : this(
            values.getAsString(VALUE_1),
            values.getAsString(VALUE_2),
            values.getAsString(VALUE_3)
    )

    fun contentValues(): ContentValues? {
        val result = ContentValues()
        result.put(VALUE_1, value1)
        result.put(VALUE_2, value2)
        result.put(VALUE_3, value3)
        return result
    }
}
