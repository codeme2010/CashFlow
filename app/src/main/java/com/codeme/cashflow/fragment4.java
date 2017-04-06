package com.codeme.cashflow;

import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class fragment4 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	View mMainView;
	EditText et;
	ListView lv;
	SimpleCursorAdapter adapter;
	String selection = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment4, (ViewGroup)getActivity().findViewById(R.id.container), false);
		et = (EditText)mMainView.findViewById(R.id.et);
		lv = (ListView)mMainView.findViewById(R.id.lv_account);

		String[] uiBindFrom = {"pingtai","zhanghu"};
		int[] uiBindTo = {R.id.pingtai, R.id.zhanghu};
		getLoaderManager().initLoader(4, null, this);
		adapter = new SimpleCursorAdapter(
				getContext(), R.layout.item_account,
				null, uiBindFrom, uiBindTo,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		lv.setAdapter(adapter);

		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				selection = "pingtai like '%" + s.toString() + "%'";
				getLoaderManager().restartLoader(4, null, fragment4.this);

				/*Cursor c = App.db.rawQuery("SELECT _id, pingtai, zhanghu from cashflow " +
						"where pingtai like '%" + s.toString() + "%' Group by pingtai,zhanghu",null);
				adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_account,c,
						new String[]{"pingtai","zhanghu"},
						new int[]{R.id.pingtai, R.id.zhanghu},0);
				lv.setAdapter(adapter);*/
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		Uri uri = App.CONTENT_URI;
		String[] projection = { "_id", "pingtai", "zhanghu" };
		uri = Uri.withAppendedPath(uri,"group/pingtai,zhanghu");
		return new CursorLoader(getContext(), uri, projection, selection, null, null);
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

