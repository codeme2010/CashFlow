package com.codeme.cashflow;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class fragment4 extends Fragment {
	View mMainView;
	EditText et;
	ListView lv;
	SimpleCursorAdapter adapter;
	App app;
	DBHelper db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App)getActivity().getApplication();
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment4, (ViewGroup)getActivity().findViewById(R.id.container), false);
		et = (EditText)mMainView.findViewById(R.id.et);
		lv = (ListView)mMainView.findViewById(R.id.lv_account);
		db = new DBHelper(getActivity(),app.databaseFilename,null,1);
		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Cursor c = db.getReadableDatabase().rawQuery("SELECT _id, pingtai, zhanghu from cashflow " +
						"where pingtai like '%" + s.toString() + "%' Group by pingtai,zhanghu",null);
				adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_account,c,
						new String[]{"pingtai","zhanghu"},
						new int[]{R.id.pingtai, R.id.zhanghu},0);
				lv.setAdapter(adapter);
			}

			@Override
			public void afterTextChanged(Editable s) {

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
		Cursor c = db.getReadableDatabase().rawQuery("SELECT _id, pingtai, zhanghu from cashflow " +
				"where pingtai like '%" + et.getText().toString() + "%' Group by pingtai,zhanghu",null);
		adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_account,c,
				new String[]{"pingtai","zhanghu"},
				new int[]{R.id.pingtai, R.id.zhanghu},0);
		lv.setAdapter(adapter);
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

