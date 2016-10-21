package com.sunnybear.library.recycler.expandable.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 */
public class BaseAdapterItem extends RecyclerView.ViewHolder {

    protected AbstractAdapterItem<Serializable> mItem;

    public BaseAdapterItem(Context context, ViewGroup parent, AbstractAdapterItem<Serializable> item) {
        super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
        itemView.setClickable(true);
        mItem = item;
        ButterKnife.bind(mItem, itemView);
        mItem.onBindViews(itemView);
//        mItem.onSetViews();
    }

    public AbstractAdapterItem<Serializable> getItem() {
        return mItem;
    }
}
