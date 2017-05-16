package com.codeme.cashflow;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

public class Provider extends ContentProvider {
    private static final int DIR = 0;
    private static final int ITEM = 1;
    private static final int group = 2;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(App.AUTHORITY,"CashFlow",DIR);
        uriMatcher.addURI(App.AUTHORITY,"CashFlow/#",ITEM);
        uriMatcher.addURI(App.AUTHORITY,"CashFlow/group/*",group);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(App.TABLE);
        Cursor cursor;
        String groupBy=null;
        if (uriMatcher.match(uri)==ITEM){queryBuilder.appendWhere("_id=" + uri.getLastPathSegment());}
        if (uriMatcher.match(uri)==group){groupBy=uri.getPathSegments().get(2);}
        cursor = queryBuilder.query(App.db, projection, selection, selectionArgs, groupBy, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = App.db.insert(App.TABLE,null,values);
        Uri newUri = ContentUris.withAppendedId(uri,id);
        getContext().getContentResolver().notifyChange(newUri,null);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id = uri.getLastPathSegment();
        int count;
        count = App.db.delete(App.TABLE,"_id="+id,null);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String id = uri.getLastPathSegment();
        int count;
        count = App.db.update(App.TABLE,values,"_id="+id,null);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }
}
