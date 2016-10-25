package com.codeme.cashflow;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import com.shamanland.fonticon.FontIconTypefaceHolder;


public class App extends Application {
    public static String DATABASE_PATH = System.getenv("EXTERNAL_STORAGE") + "/cashflow/";
    public static String TABLE = "CashFlow";
    public static String databaseFilename = DATABASE_PATH + "cashflow1.db";
    public static final String AUTHORITY = "com.codeme.cashflow.Provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/CashFlow");
    public static SQLiteDatabase db;
    @Override
    public void onCreate() {
        super.onCreate();
        FontIconTypefaceHolder.init(getAssets(), "fontawesome-webfont.ttf");
    }

    static float str2int(EditText et){
        return et.getText().toString().equals("")?0:Float.parseFloat(et.getText().toString());
    }

}
