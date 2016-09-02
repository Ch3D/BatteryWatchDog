package com.ch3d.ifttt.batterywatchdog.utils

import android.content.ContentResolver
import android.database.CharArrayBuffer
import android.database.ContentObserver
import android.database.Cursor
import android.database.DataSetObserver
import android.net.Uri
import android.os.Bundle
import com.ch3d.android.utils.StringUtils

/**
 * Created by Dmitry on 5/16/2016.
 */
open class ListCursor<T>(private val items: List<T>?) : Cursor {

    var currPosition: Int = -1

    override fun getCount() = items?.size ?: 0

    override fun getPosition() = currPosition

    override fun move(offset: Int): Boolean {
        currPosition += offset
        return validatePosition()
    }

    private fun validatePosition() = currPosition >= 0 && currPosition < count

    override fun isLast() = currPosition == count - 1

    override fun getExtras() = Bundle.EMPTY

    override fun moveToPrevious(): Boolean {
        currPosition--
        return validatePosition()
    }

    override fun registerContentObserver(observer: ContentObserver?) {
        // do nothing
    }

    override fun getColumnIndexOrThrow(columnName: String?) = -1

    override fun getColumnName(columnIndex: Int) = StringUtils.EMPTY_STRING

    override fun getFloat(columnIndex: Int) = -1f

    override fun getString(columnIndex: Int) = StringUtils.EMPTY_STRING

    override fun isFirst() = currPosition == 0

    override fun getDouble(columnIndex: Int) = 0.0

    override fun deactivate() {
        // do nothing
    }

    override fun setNotificationUri(cr: ContentResolver?, uri: Uri?) {
        // do nothing
    }

    override fun getShort(columnIndex: Int) = Short.MIN_VALUE

    override fun isNull(columnIndex: Int) = false

    override fun moveToNext(): Boolean {
        currPosition++
        return validatePosition()
    }

    override fun getLong(columnIndex: Int) = 0L

    override fun moveToLast(): Boolean {
        currPosition = count - 1
        return validatePosition()
    }

    override fun getColumnCount() = 0

    private var closed: Boolean = false

    override fun close() {
        closed = true
    }

    override fun getColumnIndex(columnName: String?) = 0

    override fun getInt(columnIndex: Int) = 0

    override fun moveToPosition(position: Int): Boolean {
        currPosition = position
        return validatePosition()
    }

    override fun requery(): Boolean {
        // do nothing
        return validatePosition()
    }

    override fun copyStringToBuffer(columnIndex: Int, buffer: CharArrayBuffer?) {
        // do nothing
    }

    override fun getColumnNames() = arrayOf(StringUtils.EMPTY_STRING)

    override fun getNotificationUri() = Uri.EMPTY

    override fun setExtras(extras: Bundle?) {
        // do nothing
    }

    override fun isClosed() = closed

    override fun getBlob(columnIndex: Int) = null

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        // do nothing
    }

    override fun getWantsAllOnMoveCalls() = false

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        // do nothing
    }

    override fun moveToFirst(): Boolean {
        currPosition = 0
        return validatePosition()
    }

    override fun getType(columnIndex: Int) = 0

    override fun respond(extras: Bundle?) = null

    override fun isBeforeFirst() = currPosition < 0

    override fun isAfterLast() = currPosition > count - 1

    override fun unregisterContentObserver(observer: ContentObserver?) {
        // do nothing
    }

}