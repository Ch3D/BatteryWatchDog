package com.ch3d.ifttt.batterywatchdog.model

import android.content.ContentValues
import com.ch3d.android.utils.StringUtils.Companion.EMPTY_STRING
import com.ch3d.android.utils.add
import com.ch3d.android.utils.addAll
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
internal open class BaseRule(@PrimaryKey var id: Long,
                             var key: String,
                             var event: String,
                             var data: RuleData,
                             private var enabled: Boolean = true) : RealmObject(), Rule {

    constructor() : this(0, EMPTY_STRING, EMPTY_STRING, RuleData())

    override fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    override fun contentValues(): ContentValues {
        return ContentValues()
                .add(RuleColumns.KEY, key)
                .add(RuleColumns.EVENT_NAME, event)
                .addAll(data.contentValues() ?: ContentValues())
    }

    override fun key() = key

    override fun data() = data

    override fun eventName() = event

    companion object {
        val EVENT_BATTERY_LOW = "ANDROID_BATTERY_LOW"
    }
}
