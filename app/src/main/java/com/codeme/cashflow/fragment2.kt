package com.codeme.cashflow

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v4.widget.CursorAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment2.*
import kotlinx.android.synthetic.main.item_baobiao.view.*

class fragment2 : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    internal lateinit var mMainView: View
    internal lateinit var adapter: SimpleCursorAdapter
    internal lateinit var adapter_mingxi: SimpleCursorAdapter
    internal var month: String? = ""
    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainView = activity.layoutInflater.inflate(R.layout.fragment2, activity.container, false)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
/*        val p = mMainView.parent as ViewGroup
        p.removeAllViewsInLayout()*/
        return mMainView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cursor = context.contentResolver.query(App.CONTENT_URI,
                arrayOf("sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)"), null, null, null)
        cursor?.moveToFirst()
        sum_all.text = "总计：" + cursor?.getString(0)
        cursor?.close()
        val uiBindFrom0 = arrayOf("month", "heji")
        val uiBindTo0 = intArrayOf(R.id.month_baobiao2, R.id.sum_baobiao2)
        val uiBindFrom1 = arrayOf("pingtai", "heji")
        val uiBindTo1 = intArrayOf(R.id.pingtai2, R.id.sum2)
        loaderManager.initLoader(30, null, this)
        loaderManager.initLoader(31, null, this)
        adapter = SimpleCursorAdapter(
                context, R.layout.item_baobiao, null, uiBindFrom0, uiBindTo0,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        adapter_mingxi = SimpleCursorAdapter(
                context, R.layout.item_mingxi, null, uiBindFrom1, uiBindTo1,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        lv_baobiao.adapter = adapter
        lv_mingxi.adapter = adapter_mingxi

        lv_baobiao.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
            month = view.month_baobiao2.text.toString()
            loaderManager.restartLoader(31, null, this@fragment2)
        }
    }

    fun update() {
        cursor = context.contentResolver.query(App.CONTENT_URI,
                arrayOf("sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)"), null, null, null)
        cursor?.moveToFirst()
        sum_all.text = "总计：" + cursor?.getString(0)
        cursor?.close()
        loaderManager.restartLoader(30, null, this)
        loaderManager.restartLoader(31, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        var selection: String? = null
        var projection: Array<String>? = null
        var sortOrder: String? = null
        var uri = App.CONTENT_URI
        when (id) {
            30 -> {
                projection = arrayOf("_id", "strftime('%Y-%m',shijian) as month", "round(sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)) as heji")
                uri = Uri.withAppendedPath(uri, "group/month")
            }
            31 -> {
                selection = "strftime('%Y-%m',shijian)='$month'"
                projection = arrayOf("_id", "pingtai", "round(sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)) as heji")
                uri = Uri.withAppendedPath(uri, "group/pingtai")
                sortOrder = "heji desc"
            }
        }
        return CursorLoader(context, uri, projection, selection, null, sortOrder)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        when (loader.id) {
            30 -> adapter.swapCursor(data)
            31 -> adapter_mingxi.swapCursor(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        when (loader.id) {
            30 -> adapter.swapCursor(null)
            31 -> adapter_mingxi.swapCursor(null)
        }
    }
}

