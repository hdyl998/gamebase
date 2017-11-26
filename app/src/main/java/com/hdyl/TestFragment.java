package com.hdyl;

import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyl.baselib.base.BaseFragment;
import com.hdyl.baselib.base.adapterbase.BaseViewHolder;
import com.hdyl.mine.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/15.
 */

public class TestFragment extends BaseFragment {




    private PinnedHeaderListView pinnedHeaderListView;



    public static class TestItem1 extends ExpSuperAdapter.ExpandableItem<String>{
    }


    //    EditText editText;
    @Override
    public void initViews() {

        List<TestItem1>lists=new ArrayList<>();
        for (int i =0; i < 50; ++i) {
            TestItem1 testItem1=new TestItem1();
            String groupName = "mList : " + i;
            testItem1.strTitle=groupName;
            lists.add(testItem1);
            List<String> curChildren = new ArrayList<String>();
            for (int j = 0; j < 5; ++j) {
                curChildren.add("child : " + j);
            }
            testItem1.childLists=curChildren;
        }
        final ExpSuperAdapter adapter= new ExpSuperAdapter<TestItem1,String>(getContext(),R.layout.group_item,R.layout.child_item){

            @Override
            public void onBindGroupView(BaseViewHolder holder, TestItem1 item, boolean is) {
                holder.setText(R.id.text,item.strTitle);
            }

            @Override
            public void onBindChildView(BaseViewHolder holder, String item,  boolean is) {
                holder.setText(R.id.text,item);
            }
        };
        adapter.setDatas(lists);

        pinnedHeaderListView = (PinnedHeaderListView)findViewByID(android.R.id.list);

        pinnedHeaderListView.setAdapter(adapter);

        pinnedHeaderListView.setOnGroupClickListener(new PinnedHeaderListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    parent.expandGroup(groupPosition);
                }

                return true;
            }
        });


        ViewGroup headerViewContainer = (ViewGroup)findViewByID(R.id.header_view_container);
        adapter.initExpSuperAdapter(pinnedHeaderListView,headerViewContainer);

        for (int i = 0; i < 50; ++i) {
            pinnedHeaderListView.expandGroup(i);
        }
    }

    @Override
    public void onClick(View v) {
//        Intent intent=new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(editText.getText().toString()));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }

    @Override
    public int setLayoutID() {
        return R.layout.fragment_test;
    }

//    private class ListAdapter extends BaseExpandableListAdapter implements PinnedHeaderListView.PinnedHeaderListener {
//
//        private List<String> mList;
//        private Map<String, List<String> > children;
//
//        public ListAdapter() {
//            super();
//
//            fillData();
//        }
//
//        private void fillData() {
//            mList = new ArrayList<String>();
//            children = new HashMap<String, List<String>>();
//            for (int i =0; i < 50; ++i) {
//                String groupName = "mList : " + i;
//                mList.add(groupName);
//                List<String> curChildren = new ArrayList<String>();
//                for (int j = 0; j < 5; ++j) {
//                    curChildren.add("child : " + j);
//                }
//                children.put(groupName, curChildren);
//            }
//        }
//
//        @Override
//        public int getGroupCount() {
//            return mList.size();
//        }
//
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            String groupName = mList.get(groupPosition);
//            if (groupName != null) {
//                List<String> currentChildren =  children.get(groupName);
//                if (currentChildren != null) {
//                    return currentChildren.size();
//                }
//            }
//            return 0;
//        }
//
//        @Override
//        public Object getGroup(int groupPosition) {
//            return mList.get(groupPosition);
//        }
//
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            String groupName = mList.get(groupPosition);
//            if (groupName != null) {
//                List<String> currentChildren =  children.get(groupName);
//                if (currentChildren != null) {
//                    return currentChildren.get(childPosition);
//                }
//            }
//
//            return null;
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return getCombinedGroupId(groupPosition);
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return getCombinedChildId(groupPosition, childPosition);
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//            GroupViewHolder holder = null;
//
//            if (convertView == null) {
//                convertView = getActivity().getLayoutInflater().inflate(R.layout.group_item, parent, false);
//                holder = new GroupViewHolder();
//                holder.text = (TextView)convertView.findViewById(R.id.text);
//                convertView.setTag(holder);
//            } else {
//                holder = (GroupViewHolder)convertView.getTag();
//            }
//
//            holder.text.setText(mList.get(groupPosition));
//
//            return convertView;
//        }
//
//        @Override
//        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//            childViewHolder holder = null;
//
//            if (convertView == null) {
//                convertView = getActivity().getLayoutInflater().inflate(R.layout.child_item, parent, false);
//                holder = new childViewHolder();
//                holder.text = (TextView)convertView.findViewById(R.id.text);
//                convertView.setTag(holder);
//            } else {
//                holder = (childViewHolder)convertView.getTag();
//            }
//
//            holder.text.setText((String)getChild(groupPosition, childPosition));
//
//            return convertView;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return false;
//        }
//
//        @Override
//        public void onVisible(PinnedHeaderListView listView, View headerView, int position) {
//
//            long expandableListPosition = listView.getExpandableListPosition(position);
//            int groupPos = PinnedHeaderListView.getPackedPositionGroup(expandableListPosition);
//
//            String text = (String)getGroup(groupPos);
//            headerViewHolder.text.setText(text);
//        }
//
//    }
////    @Override
//    public int[] setClickIDs() {
//        return new int[]{R.id.button5};
//    }
}
