package com.ch3d.ifttt.batterywatchdog.model

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import com.ch3d.android.utils.StringUtils.Companion.EMPTY_STRING
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE_1
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE_2
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract.RuleColumns.VALUE_3
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
internal open class RuleData(var value1: String = EMPTY_STRING,
                             var value2: String = EMPTY_STRING,
                             var value3: String = EMPTY_STRING) : RealmObject(), Parcelable {

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

    companion object {
        val EVENT_BATTERY_LOW = "ANDROID_BATTERY_LOW"

        @JvmField val CREATOR: Parcelable.Creator<RuleData> = object : Parcelable.Creator<RuleData> {
            override fun createFromParcel(source: Parcel): RuleData = RuleData(source)
            override fun newArray(size: Int): Array<RuleData?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(),
            source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(value1)
        dest?.writeString(value2)
        dest?.writeString(value3)
    }

}
