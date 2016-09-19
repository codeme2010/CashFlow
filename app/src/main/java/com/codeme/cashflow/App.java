package com.codeme.cashflow;

import android.app.Application;
import android.widget.EditText;

import com.shamanland.fonticon.FontIconTypefaceHolder;


public class App extends Application {
    String DATABASE_PATH = System.getenv("EXTERNAL_STORAGE") + "/cashflow/";
    String databaseFilename = DATABASE_PATH + "cashflow1.db";
    @Override
    public void onCreate() {
        super.onCreate();
        FontIconTypefaceHolder.init(getAssets(), "fontawesome-webfont.ttf");
    }

    float str2int(EditText et){
        return et.getText().toString().equals("")?0:Float.parseFloat(et.getText().toString());
    }

}
