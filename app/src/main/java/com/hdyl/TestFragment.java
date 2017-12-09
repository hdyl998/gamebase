package com.hdyl;

import android.view.View;

import com.hdyl.baselib.base.BaseFragment;
import com.hdyl.mine.R;

/**
 * Created by Administrator on 2017/11/15.
 */

public class TestFragment extends BaseFragment {


//    public static class TestItem1 extends ExpSuperAdapter.ExpandableItem<String>{
//    }


    MyImageView imageView;

    //    EditText editText;
    @Override
    public void initViews() {
        imageView = $(R.id.imageView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    final int cont = i;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setRate(cont * 0.01f);
                        }
                    });
                }

            }
        }).start();

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
