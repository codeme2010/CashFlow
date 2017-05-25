package com.codeme.cashflow

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v4.widget.CursorAdapter
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment0.*
import kotlinx.android.synthetic.main.item_all.view.*

class fragment0 : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var mMainView: View? = null
    private var isC: Boolean? = false
    private var adapter: SimpleCursorAdapter? = null
    private var cursor: Cursor? = null
    private var state: Int = 0
    private var selection: String? = null
    private var sch_Key: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainView = activity.layoutInflater.inflate(R.layout.fragment0, activity.container, false)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
/*
        val p = mMainView.parent as ViewGroup
        p?.removeAllViewsInLayout()*/
        return mMainView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        lv = mMainView.findViewById(R.id.lv) as ListView
        cb = mMainView.findViewById(R.id.cb) as CheckBox
        tv = mMainView.findViewById(R.id.zongji) as TextView
        et = mMainView.findViewById(R.id.et_sch_Key) as EditText*/
        /*cursor = getContext().getContentResolver().query(App.CONTENT_URI,
                new String[]{"sum(round(benjin*piaoli*suodingqi/36500+benjin+hongbao+(case state " +
                        "when 0 then fanxian else 0 end),1)) as zongji"},"state<>3",null,null);
        cursor.moveToFirst();
        tv.setText("待回合计：" + cursor.getString(0));
        cursor.close();*/
        update()
        val uiBindFrom = arrayOf("_id", "pingtai", "zhanghu", "huikuan", "huikuanriqi", "state", "nianhua", "benjin", "shijian", "beizhu")
        val uiBindTo = intArrayOf(R.id.id0, R.id.pingtai0, R.id.zhanghu0, R.id.huikuan0, R.id.huikuanriqi0, R.id.state0, R.id.nianhua0, R.id.benjin0, R.id.shijian0, R.id.beizhu0)
        loaderManager.initLoader(2, null, this)
        adapter = SimpleCursorAdapter(
                context, R.layout.item_all, null, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        lv0.adapter = adapter
        et_sch_Key0.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                sch_Key = "pingtai like '%$charSequence%'"
                loaderManager.restartLoader(2, null, this@fragment0)
                update()
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })
        cb0.setOnCheckedChangeListener { _, isChecked ->
            isC = isChecked
            loaderManager.restartLoader(2, null, this@fragment0)
        }

        lv0.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
            state = Integer.parseInt(view.state0.text.toString())
            val s = "        状态:"
            val item = arrayOf("刚投资完成$s 0", "已返现成功$s 1", "已申请提现$s 2", "已回款完成$s 3", "修改此记录", "删除此记录")
            val _id = view.id0.text.toString()

            AlertDialog.Builder(activity)
                    .setTitle("请选择对ID$_id 的操作")
                    .setSingleChoiceItems(item, state) { _, which -> state = which }
                    .setPositiveButton("确认") { _, _ ->
                        val uri = Uri.withAppendedPath(App.CONTENT_URI, _id)
                        when (state) {
                            4 -> {
                                App.spa.update(1, _id)
                                val m = activity as MainActivity
                                m.container.currentItem = 1
                            }
                            5 -> {
                                context.contentResolver.delete(uri, null, null)
                                //App.db.execSQL("delete from cashflow where _id = ?", new String[]{_id});
                                update()
                                App.spa.update(2, null)
                            }
                            else -> {
                                val values = ContentValues()
                                values.put("state", state)
                                context.contentResolver.update(uri, values, null, null)
                                //App.db.execSQL("update cashflow set state = ? where _id = ?", new String[]{String.valueOf(state),_id});
                                update()
                                App.spa.update(2, null)
                            }
                        }
                    }
                    .setNegativeButton("取消", null)
                    .show()
        }
    }

    fun update() {
        cursor = context.contentResolver.query(App.CONTENT_URI,
                arrayOf("sum(round(benjin*piaoli*suodingqi/36500+benjin+hongbao+(case state when 0 then fanxian else 0 end),1)) as zongji"), "state<>3" + if (sch_Key == null) "" else " and " + sch_Key!!, null, null)
        if (cursor != null) {
            cursor!!.moveToFirst()
            zongji0.text = "待回合计：${cursor?.getString(0) ?: "0"}"
            cursor!!.close()
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf("_id", "pingtai", "zhanghu", "date(shijian,'+'||suodingqi||' day') as huikuanriqi", "state", "cast(round(nianhua,0)as int)||'%' as nianhua", "'￥'||round(benjin*piaoli*suodingqi/36500+benjin+hongbao+(case state when 0 then fanxian else 0 end),1) as huikuan", "'￥'||cast(round(benjin,0)as int)||' + '||cast(round(hongbao,0)as int)||' + '||cast(round(fanxian,0)as int)||' + '||piaoli||'%' as benjin", "shijian||' + '||suodingqi||'天' as shijian", "beizhu")
        selection = if (sch_Key == null) if (isC!!) "" else "state <> 3" else sch_Key!! + if (isC!!) "" else "and state <> 3"
        //selection = isC ? null : "state <> 3";
        return CursorLoader(context, App.CONTENT_URI, projection, selection, null, " huikuanriqi")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapter!!.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter!!.swapCursor(null)
    }
}
