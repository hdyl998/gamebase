package com.hdyl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.hdyl.baselib.base.adapterbase.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */

public abstract class SimpleExpSuperAdapter<T> extends ExpSuperAdapter<ExpandableItem<T>,T>{


    public SimpleExpSuperAdapter(Context mContext, List<ExpandableItem<T>> listGroups, int groupLayoutResId, int childLayoutResId) {
        super(mContext, listGroups, groupLayoutResId, childLayoutResId);
    }

    public SimpleExpSuperAdapter(Context mContext, int groupLayoutResId, int childLayoutResId) {
        super(mContext, groupLayoutResId, childLayoutResId);
    }
}
