package com.codeme.cashflow;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class fragment3 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	View mMainView;
	ListView lv,lv_mingxi;
	SimpleCursorAdapter adapter,adapter_mingxi;
	String month;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment3, (ViewGroup)getActivity().findViewById(R.id.container), false);
		lv = (ListView)mMainView.findViewById(R.id.lv_baobiao);
		lv_mingxi = (ListView)mMainView.findViewById(R.id.lv_mingxi);
		String[] uiBindFrom0 = {"month","heji"};
		int[] uiBindTo0 = {R.id.month,R.id.sum};
		String[] uiBindFrom1 = {"pingtai","heji"};
		int[] uiBindTo1 = {R.id.pingtai, R.id.sum};
		getLoaderManager().initLoader(30, null, this);
		getLoaderManager().initLoader(31, null, this);
		adapter = new SimpleCursorAdapter(
				getContext(), R.layout.item_baobiao,
				null, uiBindFrom0, uiBindTo0,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		adapter_mingxi = new SimpleCursorAdapter(
				getContext(), R.layout.item_mingxi,
				null, uiBindFrom1, uiBindTo1,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		lv.setAdapter(adapter);
		lv_mingxi.setAdapter(adapter_mingxi);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				month = ((TextView)view.findViewById(R.id.month)).getText().toString();
				getLoaderManager().restartLoader(31,null,fragment3.this);
			}
		});
	}
/*
	void refresh(int i){
		if (i==0) {
			Cursor c = App.db.rawQuery("select _id, strftime('%Y-%m',shijian) as month, " +
					"round(sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)) as heji from cashflow group by month", null);
			adapter = new SimpleCursorAdapter(getActivity(),R.layout.item_baobiao,c,
					new String[]{"month","heji"},
					new int[]{R.id.month,R.id.sum},0);
			lv.setAdapter(adapter);
		}
		else {
			Cursor c_mingxi = App.db.rawQuery("select _id, pingtai, " +
					"round(sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)) as heji from cashflow " +
					"where strftime('%Y-%m',shijian)='" + month + "' group by pingtai", null);
			adapter_mingxi = new SimpleCursorAdapter(getActivity(), R.layout.item_mingxi, c_mingxi,
					new String[]{"pingtai", "heji"},
					new int[]{R.id.pingtai, R.id.sum}, 0);
			lv_mingxi.setAdapter(adapter_mingxi);
		}
	}*/

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

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String selection = null;
		String[] projection = null;
		Uri uri = App.CONTENT_URI;
		switch (id){
			case 30:
				projection = new String[]{"_id", "strftime('%Y-%m',shijian) as month",
						"round(sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)) as heji"};
				uri = Uri.withAppendedPath(uri,"group/month");
				break;
			case 31:
				selection = "strftime('%Y-%m',shijian)='" + month + "'";
				projection = new String[]{"_id", "pingtai",
						"round(sum(benjin*piaoli*suodingqi/36500+hongbao+fanxian)) as heji"};
				uri = Uri.withAppendedPath(uri,"group/pingtai");
				break;
		}
		return new CursorLoader(getContext(), uri, projection, selection, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		switch (loader.getId()){
			case 30:
				adapter.swapCursor(data);
				break;
			case 31:
				adapter_mingxi.swapCursor(data);
				break;
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch (loader.getId()){
			case 30:
				adapter.swapCursor(null);
				break;
			case 31:
				adapter_mingxi.swapCursor(null);
				break;
		}
	}
}

