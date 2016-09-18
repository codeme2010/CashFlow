package com.st.codeme.cashflow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by codeme on 2016/9/10.
 */

public class DBHelper extends SQLiteOpenHelper {
    //Context mcontext;
    static final String sqlstr = "create table book ("
            + "_id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text)";
    DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(sqlstr);
        //Toast.makeText(MainActivity.this, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
