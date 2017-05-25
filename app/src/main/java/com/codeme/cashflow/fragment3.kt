package com.codeme.cashflow

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v4.widget.CursorAdapter
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment3.*

class fragment3 : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    internal lateinit var mMainView: View
    internal lateinit var adapter: SimpleCursorAdapter
    internal var selection: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainView = activity.layoutInflater.inflate(R.layout.fragment3, activity.container, false)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
/*        val p = mMainView.parent as ViewGroup
        p.removeAllViewsInLayout()*/
        return mMainView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uiBindFrom = arrayOf("pingtai", "zhanghu")
        val uiBindTo = intArrayOf(R.id.pingtai3, R.id.zhanghu3)
        loaderManager.initLoader(4, null, this)
        adapter = SimpleCursorAdapter(
                context, R.layout.item_account, null, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        lv_account.adapter = adapter

        et3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                selection = "pingtai like '%$s%'"
                loaderManager.restartLoader(4, null, this@fragment3)

                /*Cursor c = App.db.rawQuery("SELECT _id, pingtai, zhanghu from cashflow " +
						"where pingtai like '%" + s.toString() + "%' Group by pingtai,zhanghu",null);
				adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_account,c,
						new String[]{"pingtai","zhanghu"},
						new int[]{R.id.pingtai, R.id.zhanghu},0);
				lv.setAdapter(adapter);*/
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        var uri = App.CONTENT_URI
        val projection = arrayOf("_id", "pingtai", "zhanghu")
        uri = Uri.withAppendedPath(uri, "group/pingtai,zhanghu")
        return CursorLoader(context, uri, projection, selection, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.swapCursor(null)
    }
}

