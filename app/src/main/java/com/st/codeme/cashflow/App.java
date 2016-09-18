package com.st.codeme.cashflow;

import android.app.Application;
import android.widget.EditText;

import com.shamanland.fonticon.FontIconTypefaceHolder;


public class App extends Application {
    String DATABASE_PATH;
    String DATABASE_FILENAME;
    @Override
    public void onCreate() {
        super.onCreate();
        DATABASE_FILENAME = "cashflow.db";
        FontIconTypefaceHolder.init(getAssets(), "fontawesome-webfont.ttf");
    }

    float str2int(EditText et){
        return et.getText().toString().equals("")?0:Float.parseFloat(et.getText().toString());
    }

}
