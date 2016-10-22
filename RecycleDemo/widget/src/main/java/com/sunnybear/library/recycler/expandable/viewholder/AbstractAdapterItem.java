package com.sunnybear.library.recycler.expandable.viewholder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

import java.io.Serializable;

/**
 * adapter(can not expand item) item must implments this class
 */
public abstract class AbstractAdapterItem<T extends Serializable> {
    protected Context mContext;

    public AbstractAdapterItem(Context context) {
        mContext = context;
    }

    /**
     * @return item`s layoutId
     */
    @LayoutRes
    public abstract int getLayoutResId();

    /**
     * init views
     *
     * @param itemView item root view
     */
    public abstract void onBindViews(final View itemView);

    /**
     * set data to view
     *
     * @param model    model
     * @param position item index
     */
    public abstract void onUpdateViews(T model, int position);

}