package com.codeme.cashflow

import android.app.DatePickerDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment1.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class fragment1 : Fragment() {
    internal var mMainView: View? = null
    internal lateinit var date: Date
    internal var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainView = activity.layoutInflater.inflate(R.layout.fragment1, activity.container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val m = activity as MainActivity
        et_benjin1.addTextChangedListener(tw)
        et_suodingqi1.addTextChangedListener(tw)
        et_pingtai1.addTextChangedListener(tw)
        et_hongbao1.addTextChangedListener(tw)
        et_fanxian1.addTextChangedListener(tw)

        val f = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = f.format(java.util.Date())

        et_shijian1.setText(today)
        //et_时间点击显示日期

        et_shijian1.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                try {
                    date = f.parse(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                et_shijian1.setText(f.format(date))
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        bt_jiru1.setOnClickListener {
            val s: String
            val values = ContentValues()
            values.put("pingtai", et_pingtai1.text.toString())
            values.put("zhanghu", et_zhanghu1.text.toString())
            values.put("benjin", et_benjin1.text.toString())
            values.put("piaoli", et_piaoli1.text.toString())
            values.put("shijian", et_shijian1.text.toString())
            values.put("suodingqi", et_suodingqi1.text.toString())
            values.put("hongbao", et_hongbao1.text.toString())
            values.put("fanxian", et_fanxian1.text.toString())
            values.put("nianhua", et_zhehenianhua1.text.toString())
            values.put("beizhu", et_beizhu1.text.toString())
            if (bt_jiru1.text.toString() == "记入") {
                values.put("state", "0")
                context.contentResolver.insert(App.CONTENT_URI, values)
                s = "记入成功"
            } else {
                context.contentResolver.update(uri, values, null, null)
                s = "修改成功"
                bt_jiru1.text = "记入"
            }
            /*
                App.db.execSQL("insert into cashflow (pingtai, zhanghu, benjin, piaoli, shijian, " +
                                "suodingqi, hongbao, fanxian, nianhua, beizhu, state) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        new String[]{E_pingtai.getText().toString(),
                                E_zhanghu.getText().toString(),
                                E_benjin.getText().toString(),
                                E_piaoli.getText().toString(),
                                E_shijian.getText().toString(),
                                E_suodingqi.getText().toString(),
                                E_hongbao.getText().toString(),
                                E_fanxian.getText().toString(),
                                E_nianhua.getText().toString(),
                                E_beizhu.getText().toString(),
                                "0"});*/
            App.spa.update(0, null)
            App.spa.update(2, null)
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
        }
    }

    fun sch(id: String) {
        uri = Uri.withAppendedPath(App.CONTENT_URI, id)
        val projection = arrayOf("pingtai", "zhanghu", "benjin", "piaoli", "shijian", "suodingqi", "hongbao", "fanxian", "beizhu", "state")
        val cursor = context.contentResolver.query(App.CONTENT_URI, projection, "_id =$id", null, null)
        cursor!!.moveToFirst()
        et_pingtai1.setText(cursor.getString(0))
        et_zhanghu1.setText(cursor.getString(1))
        et_benjin1.setText(cursor.getString(2))
        et_piaoli1.setText(cursor.getString(3))
        et_shijian1.setText(cursor.getString(4))
        et_suodingqi1.setText(cursor.getString(5))
        et_hongbao1.setText(cursor.getString(6))
        et_fanxian1.setText(cursor.getString(7))
        et_beizhu1.setText(cursor.getString(8))
        cursor.close()
        bt_jiru1.text = "修改"
    }

    internal var tw: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            if (App.str2int(et_benjin1) > 0 && App.str2int(et_suodingqi1) > 0) {
                et_zhehenianhua1.setText(String.format(Locale.getDefault(), "%.1f", (App.str2int(et_hongbao1) + App.str2int(et_fanxian1)) / App.str2int(et_benjin1) * 36500 / App.str2int(et_suodingqi1) + App.str2int(et_piaoli1)))
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
/*        val p = mMainView!!.parent as ViewGroup
        p.removeAllViewsInLayout()*/
        return mMainView
    }
}
