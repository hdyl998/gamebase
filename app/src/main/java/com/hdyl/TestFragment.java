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

import com.hdyl.baselib.base.BaseFragment;
import com.hdyl.mine.R;

/**
 * Created by Administrator on 2017/11/15.
 */

public class TestFragment extends BaseFragment {

    private TextView text = null;
    private int[] image = { R.drawable.ic_01, R.drawable.ic_02,
            R.drawable.ic_03, R.drawable.ic_03 };
    private String[] item = { "唐僧", "孙悟空 ", "猪八戒", "沙和尚" };
    private String[][] ability = { { "会念紧箍咒", "会说阿弥陀佛" },
            { "会七十二变", "会打妖精", "会腾云驾雾" }, { "会偷懒", "会睡觉" }, { "会挑担子" } };
    private ExpandableListView explandListView;




    //设置ExpandableListView适配器
    ExpandableListAdapter adapter = new BaseExpandableListAdapter()
    {

        //处理子项目的单击事件
        @Override
        public boolean isChildSelectable(int groupPosition,
                                         int childPosition)
        {
            String str = item[groupPosition]
                    + ability[groupPosition][childPosition];
            updateText(str);
            return true;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        //返回父项目的视图控件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent)
        {
            //新建一个线性布局
            LinearLayout ll = new LinearLayout(getContext());
            // 设置布局样式为Horizontal
            ll.setOrientation(0);
            //设置布局左边距为50像素
            ll.setPadding(50, 0, 0, 0);
            //新建一个ImageView对象
            ImageView imageView = new ImageView(getContext());
            //设置ImageView要显示的对象ID
            imageView.setImageResource(image[groupPosition]);
            //将ImageView加到线性布局中
            ll.addView(imageView);
            //使用自定义文本框
            TextView textView = getTextView();
            //设置文本框里显示内容
            textView.setText(getGroup(groupPosition).toString());
            //将TextView加到线性布局中
            ll.addView(textView);
            return ll;
        }

        //返回父控件的ID
        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }

        //返回父控件的总数
        @Override
        public int getGroupCount()
        {

            return ability.length;
        }

        //取得父控件对象
        @Override
        public Object getGroup(int groupPosition)
        {
            return item[groupPosition];
        }

        //取得子控件的数量
        @Override
        public int getChildrenCount(int groupPosition)
        {
            return ability[groupPosition].length;
        }

        //取得子控件的视图
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent)
        {
            //使用自定义TextView控件
            TextView textView = getTextView();
            //设置自定义TextView控件的内容
            textView.setText(getChild(groupPosition, childPosition)
                    .toString());
            return textView;
        }

        //取得子控件的ID
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        //取得子控件的对象
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return ability[groupPosition][childPosition];
        }

        //自定义文本框
        public TextView getTextView()
        {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(lp);
            textView.setPadding(20, 0, 0, 0);
            //设置TextView控件为向左,水平居中对齐
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            return textView;

        }
    };


    private void updateText(String string)
    {
        // 将文本信息设置给TextView控件显示出来
        text.setText(string);
    }




    //    EditText editText;
    @Override
    public void initViews() {
//        editText=findViewByID(R.id.editText);

        // 通过ID查找到main.xml中的TextView控件
        text = (TextView) findViewByID(R.id.text);

        // 通过ID查找到main.xml中的ExpandableListView控件
        explandListView = (ExpandableListView) findViewByID(R.id.expandableListView);

        explandListView.setAdapter(adapter);
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

//    @Override
//    public int[] setClickIDs() {
//        return new int[]{R.id.button5};
//    }
}
