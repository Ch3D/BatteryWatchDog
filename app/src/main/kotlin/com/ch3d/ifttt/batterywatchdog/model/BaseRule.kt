package com.ch3d.ifttt.batterywatchdog.model

import android.content.ContentValues
import com.ch3d.android.utils.StringUtils.Companion.EMPTY_STRING
import com.ch3d.android.utils.add
import com.ch3d.android.utils.addAll
import com.ch3d.ifttt.batterywatchdog.content.RulesContract.RuleColumns
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
internal open class BaseRule(@PrimaryKey
                             var id: Long,
                             var key: String,
                             var event: String,
                             var data: RuleData) : RealmObject(), Rule {

    constructor() : this(0, EMPTY_STRING, EMPTY_STRING, RuleData())

    override fun contentValues(): ContentValues {
        return contentValues()
                .add(RuleColumns.KEY, key())
                .add(RuleColumns.EVENT_NAME, event)
                .addAll(data.contentValues() ?: ContentValues())
    }

    override fun key() = key

    override fun data() = data

    override fun eventName() = EVENT_BATTERY_LOW

    private var enabled: Boolean = true

    override fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    companion object {
        val EVENT_BATTERY_LOW = "ANDROID_BATTERY_LOW"
    }
}
