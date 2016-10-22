package com.recycledemo.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.recycledemo.R;
import com.recycledemo.controller.MainActivity;
import com.recycledemo.view.viewholder.UserViewHolder;
import com.sunnybear.library.basic.ViewBinder;
import com.sunnybear.library.recycler.BasicRecyclerView;
import com.sunnybear.library.recycler.BasicViewHolder;
import com.sunnybear.library.recycler.adapter.BasicAdapter;

import butterknife.Bind;

/**
 * Created by Travis on 2016/10/22.
 */
public class MainViewBinder extends ViewBinder<MainActivity> {
    @Bind(R.id.rv_show)
    BasicRecyclerView rvShow;

    public MainViewBinder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewCreatedFinish() {
        rvShow.setAdapter(new BasicAdapter(mContext, mDispatch.setData()) {
                              @Override
                              public int getLayoutId(int viewType) {
                                  return R.layout.item_recycle;
                              }

                              @Override
                              public UserViewHolder getViewHolder(Context context, View itemView, int viewType) {
                                  return new UserViewHolder(context, itemView);
                              }

                          }
        );
    }
}
