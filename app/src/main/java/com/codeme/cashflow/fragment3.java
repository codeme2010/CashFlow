package com.codeme.cashflow;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class fragment3 extends Fragment {
	View mMainView;
	ListView lv,lv_mingxi;
	SimpleCursorAdapter adapter,adapter_mingxi;
	App app;
	DBHelper db;
	String month;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getActivity().getApplication();

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment3, (ViewGroup)getActivity().findViewById(R.id.container), false);
		lv = (ListView)mMainView.findViewById(R.id.lv_baobiao);
		lv_mingxi = (ListView)mMainView.findViewById(R.id.lv_mingxi);
		db = new DBHelper(getActivity(),app.databaseFilename,null,1);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				month = ((TextView)view.findViewById(R.id.month)).getText().toString();
				refresh(1);
			}
		});
	}

	void refresh(int i){
		if (i==0) {
			Cursor c = db.getReadableDatabase().rawQuery("select _id, strftime('%Y-%m',shijian) as month, " +
					"round(sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)) as heji from cashflow group by month", null);
			adapter = new SimpleCursorAdapter(getActivity(),R.layout.item_baobiao,c,
					new String[]{"month","heji"},
					new int[]{R.id.month,R.id.sum},0);
			lv.setAdapter(adapter);
		}
		else {
			Cursor c_mingxi = db.getReadableDatabase().rawQuery("select _id, pingtai, " +
					"round(sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)) as heji from cashflow " +
					"where strftime('%Y-%m',shijian)='" + month + "' group by pingtai", null);
			adapter_mingxi = new SimpleCursorAdapter(getActivity(), R.layout.item_mingxi, c_mingxi,
					new String[]{"pingtai", "heji"},
					new int[]{R.id.pingtai, R.id.sum}, 0);
			lv_mingxi.setAdapter(adapter_mingxi);
		}
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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refresh(0);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}

