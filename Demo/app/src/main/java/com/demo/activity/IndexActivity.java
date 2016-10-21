package com.demo.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.demo.R;
import com.demo.adapter.MyAdapter;
import com.demo.fragment.MoreFragment;
import com.demo.fragment.ProjectFragment;
import com.demo.fragment.SearchFragment;
import com.demo.fragment.SynchFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuanwei.tian on 2016/10/18.
 * 主界面
 * fragment + viewpager
 */
public class IndexActivity extends FragmentActivity implements OnTabSelectListener {

    @InjectView(R.id.vp_index)
    ViewPager mVpIndex;
    @InjectView(R.id.bb_tabs)
    BottomBar mBbTabs;

    private MyAdapter mAdapter;
    private SparseArray<Fragment> mSparseArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.inject(this);

        initData();

    }

    private void initData() {
        mSparseArray = new SparseArray();
        mSparseArray.put(0, new ProjectFragment());
        mSparseArray.put(1, new SearchFragment());
        mSparseArray.put(2, new SynchFragment());
        mSparseArray.put(3, new MoreFragment());
        mAdapter = new MyAdapter(getSupportFragmentManager(), mSparseArray);
        mVpIndex.setAdapter(mAdapter);
        mVpIndex.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBbTabs.setDefaultTabPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBbTabs.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_project:
                mVpIndex.setCurrentItem(0, false);
                break;
            case R.id.tab_search:
                mVpIndex.setCurrentItem(1, false);
                break;
            case R.id.tab_synch:
                mVpIndex.setCurrentItem(2, false);

                break;
            case R.id.tab_more:
                mVpIndex.setCurrentItem(3, false);

                break;
        }
    }
}
