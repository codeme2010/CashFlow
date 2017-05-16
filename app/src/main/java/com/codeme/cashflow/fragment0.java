package com.codeme.cashflow;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class fragment0 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private View mMainView;
    private Boolean isC = false;
    private TextView tv;
    private SimpleCursorAdapter adapter;
    private int state;
    private String sch_Key = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment2, (ViewGroup)getActivity().findViewById(R.id.container), false);

        final MainActivity m = (MainActivity)getActivity();
        ListView lv = (ListView) mMainView.findViewById(R.id.lv);
        CheckBox cb = (CheckBox) mMainView.findViewById(R.id.cb);
        tv = (TextView)mMainView.findViewById(R.id.zongji);
        EditText et = (EditText) mMainView.findViewById(R.id.et_sch_Key);
        /*cursor = getContext().getContentResolver().query(App.CONTENT_URI,
                new String[]{"sum(round(benjin*piaoli*suodingqi/36500+benjin+hongbao+(case state " +
                        "when 0 then fanxian else 0 end),1)) as zongji"},"state<>3",null,null);
        cursor.moveToFirst();
        tv.setText("待回合计：" + cursor.getString(0));
        cursor.close();*/
        update();
        String[] uiBindFrom = { "_id","pingtai","zhanghu","huikuan","huikuanriqi","state","nianhua",
                "benjin","shijian","beizhu"};
        int[] uiBindTo = { R.id.id,R.id.pingtai,R.id.zhanghu,R.id.huikuan,R.id.huikuanriqi,R.id.state,R.id.nianhua,
                R.id.benjin,R.id.shijian,R.id.beizhu};
        getLoaderManager().initLoader(2, null, this);
        adapter = new SimpleCursorAdapter(
                getContext(), R.layout.item_all,
                null, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adapter);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sch_Key = "pingtai like '%" + charSequence.toString() + "%'";
                getLoaderManager().restartLoader(2, null, fragment0.this);
                update();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isC = isChecked;
                getLoaderManager().restartLoader(2,null,fragment0.this);
//                refresh();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state = Integer.parseInt(((TextView)view.findViewById(R.id.state)).getText().toString());
                String s = "        状态:";
                final String[] item = {"刚投资完成"+s+" 0", "已返现成功"+s+" 1", "已申请提现"+s+" 2", "已回款完成"+s+" 3", "修改此记录", "删除此记录"};
                final String _id = ((TextView)view.findViewById(R.id.id)).getText().toString();
                new AlertDialog.Builder(getActivity())
                        .setTitle("请选择对ID" + _id + "的操作")
                        .setSingleChoiceItems(item, state, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                state = which;
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.withAppendedPath(App.CONTENT_URI,_id);
                                switch (state){
                                    case 4:
                                        m.spa.update(1,_id);
                                        m.mViewPager.setCurrentItem(1);
                                        break;
                                    case 5:
                                        getContext().getContentResolver().delete(uri,null,null);
//                                        App.db.execSQL("delete from cashflow where _id = ?", new String[]{_id});
                                        update();
                                        m.spa.update(2,null);
                                        break;
                                    default:
                                        ContentValues values = new ContentValues();
                                        values.put("state",state);
                                        getContext().getContentResolver().update(uri,values,null,null);
//                                        App.db.execSQL("update cashflow set state = ? where _id = ?", new String[]{String.valueOf(state),_id});
                                        update();
                                        m.spa.update(2,null);
                                }

                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        ViewGroup p = (ViewGroup) mMainView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }

        return mMainView;
    }

    public void update() {
        Cursor cursor = getContext().getContentResolver().query(App.CONTENT_URI,
                new String[]{"sum(round(benjin*piaoli*suodingqi/36500+benjin+hongbao+(case state " +
                        "when 0 then fanxian else 0 end),1)) as zongji"}, "state<>3" + (sch_Key == null ? "" : " and " + sch_Key), null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            tv.setText("待回合计：" + (cursor.getString(0)==null?"0": cursor.getString(0)));
            cursor.close();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {"_id", "pingtai", "zhanghu", "date(shijian,'+'||suodingqi||' day') as huikuanriqi", "state","cast(round(nianhua,0)as int)||'%' as nianhua",
                "'￥'||round(benjin*piaoli*suodingqi/36500+benjin+hongbao+(case state when 0 then fanxian else 0 end),1) as huikuan",
                "'￥'||cast(round(benjin,0)as int)||' + '||cast(round(hongbao,0)as int)||' + '||cast(round(fanxian,0)as int)||' + '||piaoli||'%' as benjin", "shijian||' + '||suodingqi||'天' as shijian", "beizhu"};
        String selection = sch_Key == null ? (isC ? "" : "state <> 3") : sch_Key + (isC ? "" : "and state <> 3");
        //selection = isC ? null : "state <> 3";
        return new CursorLoader(getContext(), App.CONTENT_URI, projection, selection, null, " huikuanriqi");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
