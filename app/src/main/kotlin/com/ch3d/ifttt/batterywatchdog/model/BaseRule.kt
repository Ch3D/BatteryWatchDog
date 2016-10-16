package com.ch3d.ifttt.batterywatchdog.modelimport

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import com.ch3d.android.utils.StringUtils
import com.ch3d.android.utils.add
import com.ch3d.android.utils.addAll
import com.ch3d.ifttt.batterywatchdog.model.Rule
import com.ch3d.ifttt.batterywatchdog.model.RuleData
import com.ch3d.ifttt.batterywatchdog.provider.RulesContract
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
internal open class BaseRule(@PrimaryKey var id: Long,
                             var key: String,
                             var event: String,
                             var data: RuleData,
                             private var enabled: Boolean = true) : RealmObject(),
                                                                    Rule,
                                                                    Parcelable {

    override fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    override fun contentValues(): ContentValues {
        return ContentValues()
                .add(RulesContract.RuleColumns.KEY, key)
                .add(RulesContract.RuleColumns.EVENT_NAME, event)
                .addAll(data.contentValues() ?: ContentValues())
    }

    override fun key() = key

    override fun data() = data

    override fun eventName() = event

    companion object {
        val EVENT_BATTERY_LOW = "ANDROID_BATTERY_LOW"

        @JvmField val CREATOR: Parcelable.Creator<BaseRule> = object : Parcelable.Creator<BaseRule> {
            override fun createFromParcel(source: Parcel): BaseRule = BaseRule(source)
            override fun newArray(size: Int): Array<BaseRule?> = arrayOfNulls(size)
        }
    }

    constructor() : this(0, StringUtils.EMPTY_STRING, StringUtils.EMPTY_STRING, RuleData(), false)

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readParcelable<RuleData>(RuleData::class.java.classLoader),
            source.readInt() == 1)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(key)
        dest?.writeString(event)
        dest?.writeParcelable(data, 0)
        dest?.writeInt((if (enabled) 1 else 0))
    }
}
