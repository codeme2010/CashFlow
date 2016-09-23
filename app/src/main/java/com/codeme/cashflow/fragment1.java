package com.codeme.cashflow;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class fragment1 extends Fragment {
    private View mMainView;
    App app;
    EditText E_pingtai,E_zhanghu,E_benjin,E_shijian,E_suodingqi,E_piaoli,E_hongbao,E_fanxian,E_nianhua,E_beizhu;
    Date date;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment1, (ViewGroup) getActivity().findViewById(R.id.container), false);

        app = (App)getActivity().getApplication();

        E_pingtai = (EditText) mMainView.findViewById(R.id.et_平台);
        E_zhanghu = (EditText) mMainView.findViewById(R.id.et_账户);
        E_benjin = (EditText) mMainView.findViewById(R.id.et_本金);
        E_shijian = (EditText) mMainView.findViewById(R.id.et_时间);
        E_suodingqi = (EditText) mMainView.findViewById(R.id.et_锁定期);
        E_piaoli = (EditText) mMainView.findViewById(R.id.et_票利);
        E_hongbao = (EditText) mMainView.findViewById(R.id.et_红包);
        E_fanxian = (EditText) mMainView.findViewById(R.id.et_返现);
        E_nianhua = (EditText) mMainView.findViewById(R.id.et_折合年化);
        E_beizhu = (EditText) mMainView.findViewById(R.id.et_备注);

        E_benjin.addTextChangedListener(tw);
        E_suodingqi.addTextChangedListener(tw);
        E_piaoli.addTextChangedListener(tw);
        E_hongbao.addTextChangedListener(tw);
        E_fanxian.addTextChangedListener(tw);

        final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = f.format(new java.util.Date());

        E_shijian.setText(today);
        //et_时间点击显示日期

        E_shijian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        try {
                            date = f.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        E_shijian.setText(f.format(date));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button bt = (Button)mMainView.findViewById(R.id.bt_记入);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DBHelper db=new DBHelper(getActivity(),app.databaseFilename,null,1);
                db.getReadableDatabase().execSQL("insert into cashflow (pingtai, zhanghu, benjin, piaoli, shijian, " +
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
                                "0"});
                MainActivity m = (MainActivity)getActivity();
                m.spa.notifyDataSetChanged();
                Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
//                Log.d("fragment1",E.getText().toString());
            }
        });
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (app.str2int(E_benjin)>0 && app.str2int(E_suodingqi)>0){
                E_nianhua.setText(String.format("%.1f",(app.str2int(E_hongbao)+app.str2int(E_fanxian))/app.str2int(E_benjin)*36500/app.str2int(E_suodingqi)+app.str2int(E_piaoli)));
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup p = (ViewGroup) mMainView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }

        Log.v("huahua", "fragment1-->onCreatView()");
        return mMainView;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.v("huahua", "fragment1-->onPause()");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.v("huahua", "fragment1-->onResume()");
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.v("huahua", "fragment1-->onStart()");
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.v("huahua", "fragment1-->onStop()");
    }

}
