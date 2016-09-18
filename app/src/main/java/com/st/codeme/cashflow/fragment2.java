package com.st.codeme.cashflow;

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

public class fragment2 extends Fragment {
	View mMainView;
	CheckBox cb;
	Boolean isC = false;
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
		mMainView = inflater.inflate(R.layout.fragment2, (ViewGroup)getActivity().findViewById(R.id.container), false);

		lv = (ListView)mMainView.findViewById(R.id.lv);
		db = new DBHelper(getActivity(),app.DATABASE_PATH + "/" + app.DATABASE_FILENAME,null,1);

		cb = (CheckBox)mMainView.findViewById(R.id.cb);
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				Log.v("cb",isChecked?"true":"false");
				isC = isChecked;
				Cursor c = db.getReadableDatabase().rawQuery("select * from cashflow " +
						(isC?"":"where state <> 2 ")+"order by _id desc",null);
				adapter = new SimpleCursorAdapter(getActivity(),R.layout.item_all,c,
						new String[]{"pingtai","zhanghu","shijian","benjin","state"},
						new int[]{R.id.pingtai,R.id.zhanghu,R.id.huikuanriqi,R.id.huikuan,R.id.state},0);
				lv.setAdapter(adapter);
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object item = lv.getItemAtPosition(position);
//				Log.v("item",((TextView)item).getText().toString());
				Log.v("item",((TextView)view.findViewById(R.id.huikuanriqi)).getText().toString());
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
		Log.v("huahua", "fragment2-->onCreateView");
		
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
		Log.v("huahua", "fragment2-->onPause()");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Cursor c = db.getReadableDatabase().rawQuery("select * from cashflow " +
				(isC?"":"where state <> 2 ")+"order by _id desc",null);
		adapter = new SimpleCursorAdapter(getActivity(),R.layout.item_all,c,
				new String[]{"pingtai","zhanghu","shijian","benjin","state"},
				new int[]{R.id.pingtai,R.id.zhanghu,R.id.huikuanriqi,R.id.huikuan,R.id.state},0);
		lv.setAdapter(adapter);
		Log.v("huahua", "fragment2-->onResume()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("huahua", "fragment2-->onStart()");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("huahua", "fragment2-->onStop()");
	}

}
