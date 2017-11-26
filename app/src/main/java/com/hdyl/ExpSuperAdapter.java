package com.hdyl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.hdyl.baselib.base.adapterbase.BaseViewHolder;
import com.hdyl.baselib.utils.log.LogUitls;

import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */

public abstract class ExpSuperAdapter<T extends ExpSuperAdapter.ExpandableItem<V>,V> extends BaseExpandableListAdapter implements PinnedHeaderListView.PinnedHeaderListener{

    BaseViewHolder firstViewHolder;
    PinnedHeaderListView pinnedHeaderListView;


    /***
     * 初始化适配器
     * @param listView
     * @param headerViewContainer
     */
    public void initExpSuperAdapter(PinnedHeaderListView listView,ViewGroup headerViewContainer){
        pinnedHeaderListView=listView;
        pinnedHeaderListView.setPinnedHeaderListener(this);
        pinnedHeaderListView.setGroupIndicator(null);
        View headerView= createFloatHeadView(headerViewContainer);
        headerViewContainer.addView(headerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int groupPos=position2GroupPos(pinnedHeaderListView.getFirstVisiblePosition());
                pinnedHeaderListView.collapseGroup(groupPos);
                pinnedHeaderListView.setSelectedGroup(groupPos);
            }
        });
        pinnedHeaderListView.setPinnedHeader(headerView);
    }


    public int position2GroupPos(int position){
        long expandableListPosition = pinnedHeaderListView.getExpandableListPosition(position);
        int groupPos = ExpandableListView.getPackedPositionGroup(expandableListPosition);
        return groupPos;
    }

    /***
     * 创建一个浮动头部
     * @param viewGroup
     * @return
     */
    private View createFloatHeadView(ViewGroup viewGroup){
        T item= getGroup(0);
        final BaseViewHolder viewHolder = onCreate(null,viewGroup,groupLayoutResId);
        onBindGroupView(viewHolder, item,pinnedHeaderListView.isGroupExpanded(0));
        firstViewHolder=viewHolder;
        return viewHolder.getItemView();
    }

    public List<T> mList;
    protected LayoutInflater mInflater;

    public int groupLayoutResId, childLayoutResId;


    public ExpSuperAdapter(Context mContext,List<T> listGroups, int groupLayoutResId, int childLayoutResId){
        this.mList =listGroups;
        this.groupLayoutResId=groupLayoutResId;
        this.childLayoutResId=childLayoutResId;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public ExpSuperAdapter(Context mContext, int groupLayoutResId, int childLayoutResId){
        this(mContext,null,groupLayoutResId,childLayoutResId);
    }


    public void setDatas(List<T> items) {
        mList = items;
        notifyDataSetChanged();
    }


    public List<T> getDatas() {
        return mList;
    }

    public abstract void onBindGroupView(BaseViewHolder holder, T item,boolean isExpanded);

    public abstract void onBindChildView(BaseViewHolder holder, V item,boolean isLastChild);


    @Override
    public int getGroupCount() {
        return mList ==null?0: mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getChildLists().size();
    }

    @Override
    public T getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public V getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getChildLists().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return getCombinedGroupId(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    private  BaseViewHolder onCreate( View convertView, ViewGroup parent,int layoutRes) {
        return BaseViewHolder.get(mInflater, convertView, parent, layoutRes);
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        T item= getGroup(groupPosition);
        final BaseViewHolder viewHolder = onCreate(convertView,parent,groupLayoutResId);
        onBindGroupView(viewHolder, item,isExpanded);
        //        TestFragment.GroupViewHolder holder = null;
//
//        if (convertView == null) {
//            convertView = getActivity().getLayoutInflater().inflate(R.layout.group_item, parent, false);
//            holder = new TestFragment.GroupViewHolder();
//            holder.text = (TextView)convertView.findViewById(R.id.text);
//            convertView.setTag(holder);
//        } else {
//            holder = (TestFragment.GroupViewHolder)convertView.getTag();
//        }
//
//        holder.text.setText(mList.get(groupPosition));

        return viewHolder.getItemView();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        V item= getChild(groupPosition,childPosition);
        final BaseViewHolder viewHolder = onCreate(convertView,parent,childLayoutResId);
        onBindChildView(viewHolder, item, isLastChild);

        //        TestFragment.childViewHolder holder = null;
//
//        if (convertView == null) {
//            convertView = getActivity().getLayoutInflater().inflate(R.layout.child_item, parent, false);
//            holder = new TestFragment.childViewHolder();
//            holder.text = (TextView)convertView.findViewById(R.id.text);
//            convertView.setTag(holder);
//        } else {
//            holder = (TestFragment.childViewHolder)convertView.getTag();
//        }
//
//        holder.text.setText((String)getChild(groupPosition, childPosition));

        return viewHolder.getItemView();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onVisible(PinnedHeaderListView listView, View headerView, int position) {
       if(firstViewHolder!=null) {
           int groupPos=position2GroupPos(position);
           T text = getGroup(groupPos);
           onBindGroupView(firstViewHolder, text, listView.isGroupExpanded(groupPos));
       }
    }

    public static class ExpandableItem<ChildItem>{
        public String strTitle;
        public List<ChildItem>childLists;


        public void setStrTitle(String strTitle) {
            this.strTitle = strTitle;
        }

        public String getStrTitle() {
            return strTitle;
        }

        public void setChildLists(List<ChildItem> childLists) {
            this.childLists = childLists;
        }

        public List<ChildItem> getChildLists() {
            return childLists;
        }
    }

}
