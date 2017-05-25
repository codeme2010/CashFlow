package com.codeme.cashflow

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

class Provider : ContentProvider() {

    override fun onCreate() = true

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = App.TABLE
        val cursor: Cursor
        var groupBy: String? = null
        if (uriMatcher.match(uri) == ITEM) {
            queryBuilder.appendWhere("_id=" + uri.lastPathSegment)
        }
        if (uriMatcher.match(uri) == group) {
            groupBy = uri.pathSegments[2]
        }
        cursor = queryBuilder.query(App.db, projection, selection, selectionArgs, groupBy, null, sortOrder)
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri) = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = App.db!!.insert(App.TABLE, null, values)
        val newUri = ContentUris.withAppendedId(uri, id)
        context!!.contentResolver.notifyChange(newUri, null)
        return newUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val id = uri.lastPathSegment
        val count: Int
        count = App.db!!.delete(App.TABLE, "_id=" + id, null)
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val id = uri.lastPathSegment
        val count: Int
        count = App.db!!.update(App.TABLE, values, "_id=" + id, null)
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    companion object {
        internal val DIR = 0
        internal val ITEM = 1
        internal val group = 2
        internal var uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(App.AUTHORITY, "CashFlow", DIR)
            uriMatcher.addURI(App.AUTHORITY, "CashFlow/#", ITEM)
            uriMatcher.addURI(App.AUTHORITY, "CashFlow/group/*", group)
        }
    }
}
