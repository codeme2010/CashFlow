package com.st.codeme.cashflow;

import android.app.Application;

import com.shamanland.fonticon.FontIconTypefaceHolder;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontIconTypefaceHolder.init(getAssets(), "fontawesome-webfont.ttf");
    }
}
