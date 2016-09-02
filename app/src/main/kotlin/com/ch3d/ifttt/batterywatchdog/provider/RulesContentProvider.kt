package com.ch3d.ifttt.batterywatchdog.provider

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.ch3d.ifttt.batterywatchdog.model.BaseRule
import com.ch3d.ifttt.batterywatchdog.model.Rule
import com.ch3d.ifttt.batterywatchdog.utils.SingletonListCursor
import io.realm.Realm

class RulesContentProvider : android.content.ContentProvider() {
    val realmInstance: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate() = true

    override fun getType(uri: Uri) = RulesContract.URI_TYPE_RULE_ITEM

    override fun query(uri: Uri,
                       projection: Array<String>?,
                       selection: String?,
                       selectionArgs: Array<String>?,
                       sortOrder: String?): Cursor? {

        return SingletonListCursor(realmInstance
                .where(BaseRule::class.java)
                .equalTo(RulesContract.RuleColumns.ID, uri.lastPathSegment)
                .findFirst())
    }

    fun getNextKey() =
            realmInstance
                    .where(BaseRule::class.java)
                    .max(RulesContract.RuleColumns.ID).toLong() + 1

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values == null)
            return Uri.EMPTY
        else {
            val insertedRule = Rule.Companion.create(values) as BaseRule
            insertedRule.id = getNextKey()
            realmInstance.beginTransaction()
            realmInstance.copyToRealmOrUpdate(insertedRule)
            realmInstance.commitTransaction()
            // ReportApiFactory.create(context).report(insertedRule)
        }

        return Uri.EMPTY
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?) = 0
}
