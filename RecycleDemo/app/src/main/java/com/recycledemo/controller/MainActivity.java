package com.recycledemo.controller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.recycledemo.model.User;
import com.recycledemo.view.MainViewBinder;
import com.sunnybear.library.basic.DispatchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * RecycleView + HorizontalScrollView
 */
public class MainActivity extends DispatchActivity<MainViewBinder> {

    @Override
    protected MainViewBinder getViewBinder(Context context) {
        return new MainViewBinder(context);
    }

    @Override
    protected void dispatchModelOnCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void dispatchModelOnStart() {

    }

    public List setData() {
        ArrayList<User> mUserList = new ArrayList<>();
        mUserList.add(0, new User("栏目1", "栏目2", "栏目3", "栏目4", "栏目5", "栏目6", "栏目7"));
        mUserList.add(1, new User("栏目1", "栏目2", "栏目3", "栏目4", "栏目5", "栏目6", "栏目7"));
        mUserList.add(2, new User("栏目1", "栏目2", "栏目3", "栏目4", "栏目5", "栏目6", "栏目7"));
        mUserList.add(3, new User("栏目1", "栏目2", "栏目3", "栏目4", "栏目5", "栏目6", "栏目7"));
        return mUserList;
    }
}
