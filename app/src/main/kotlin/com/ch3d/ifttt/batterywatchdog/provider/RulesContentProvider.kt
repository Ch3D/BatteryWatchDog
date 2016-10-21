package com.ch3d.ifttt.batterywatchdog.provider

import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ch3d.ifttt.batterywatchdog.model.BaseRule
import com.ch3d.ifttt.batterywatchdog.model.Rule
import com.ch3d.ifttt.batterywatchdog.utils.ListCursor
import com.ch3d.ifttt.batterywatchdog.utils.SingletonListCursor
import com.ch3d.ifttt.batterywatchdog.utils.perform
import io.realm.Realm

private const val MATCH_RULES = 1
private const val MATCH_RULE = 2
private const val MATCH_DISPATCHER = 3

class RulesContentProvider : android.content.ContentProvider() {

    val sUriMatcher: UriMatcher

    init {
        sUriMatcher = UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(RulesContract.AUTHORITY, "rules", MATCH_RULES)
        sUriMatcher.addURI(RulesContract.AUTHORITY, "rules/#", MATCH_RULE)
        sUriMatcher.addURI(RulesContract.AUTHORITY, "dispacther/#", MATCH_DISPATCHER)
    }

    val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate() = true

    override fun getType(uri: Uri) = RulesContract.URI_TYPE_RULE_ITEM

    override fun query(uri: Uri,
                       projection: Array<String>?,
                       selection: String?,
                       selectionArgs: Array<String>?,
                       sortOrder: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            MATCH_RULES -> ListCursor(getAllRules())
            MATCH_RULE -> SingletonListCursor(getRule(uri.lastPathSegment.toLong()))
            MATCH_DISPATCHER -> null
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (sUriMatcher.match(uri)) {
            MATCH_RULES -> {
                val insertedRule = Rule.Companion.create(values!!) as BaseRule
                insertedRule.id = getNextKey()
                realm.perform { copyToRealmOrUpdate(insertedRule) }
                ContentUris.withAppendedId(uri, insertedRule.id)
            }
            else -> Uri.EMPTY
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

    override fun update(uri: Uri, values: ContentValues?, select: String?, args: Array<String>?) = 0

    private fun getAllRules() =
            realm.where(BaseRule::class.java)
                    .findAll()
                    .toList()

    private fun getRule(id: Long) =
            realm.where(BaseRule::class.java)
                    .equalTo(RulesContract.RuleColumns.ID, id)
                    .findFirst()

    private fun getNextKey() =
            realm.where(BaseRule::class.java)
                    .max(RulesContract.RuleColumns.ID)
                    .toLong() + 1
}
