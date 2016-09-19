package com.codeme.cashflow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fg = null;
    private ArrayList<String> titleList = null;

    SectionsPagerAdapter(FragmentManager fm, List<Fragment> fg, ArrayList<String> titleList) {
        super(fm);
        this.fg = fg;
        this.titleList = titleList;
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int arg0) {
        return fg.get(arg0);
    }

    @Override
    public int getCount() {
        return fg.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        return titleList.get(position);
    }
    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

}