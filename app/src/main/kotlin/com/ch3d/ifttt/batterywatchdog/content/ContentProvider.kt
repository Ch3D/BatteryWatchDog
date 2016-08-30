package com.ch3d.ifttt.batterywatchdog.content

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.ch3d.ifttt.batterywatchdog.model.Rule
import com.ch3d.ifttt.batterywatchdog.network.ReportApiFactory

class ContentProvider : android.content.ContentProvider() {
    override fun onCreate() = true

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun getType(uri: Uri) = RulesContract.URI_TYPE_RULE_ITEM

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values == null)
            return Uri.EMPTY
        else
            ReportApiFactory.create(context).report(Rule.Companion.create(values))

        return Uri.EMPTY
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        return 0
    }
}
