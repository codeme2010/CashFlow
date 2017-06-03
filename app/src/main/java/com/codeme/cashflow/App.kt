package com.codeme.cashflow

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.widget.EditText

class App : Application() {

    companion object {
        val DATABASE_PATH = System.getenv("EXTERNAL_STORAGE") + "/cashflow/"
        val TABLE = "CashFlow"
        val databaseFilename = DATABASE_PATH + "cashflow1.db"
        val AUTHORITY = "com.codeme.cashflow.Provider"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/CashFlow")!!
        var db: SQLiteDatabase? = null
        lateinit var spa: SectionsPagerAdapter

        fun str2int(et: EditText): Float {
            return if (et.text.toString() == "") 0F else java.lang.Float.parseFloat(et.text.toString())
        }
    }

}
