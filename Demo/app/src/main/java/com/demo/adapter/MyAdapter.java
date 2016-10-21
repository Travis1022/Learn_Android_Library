package com.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * Created by xuanwei.tian on 2016/10/18.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private SparseArray<Fragment> mFragmentSparseArray;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyAdapter(FragmentManager fm, SparseArray<Fragment> fragmentSparseArray) {
        super(fm);
        mFragmentSparseArray = fragmentSparseArray;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentSparseArray.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentSparseArray.size();
    }
}
