package com.codeme.cashflow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

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
        String fg = object.toString().substring(0,9);
        if(fg.equals("fragment2")||fg.equals("fragment3")){
            return POSITION_NONE;
        }else{
            return POSITION_UNCHANGED;
        }
    }

}