package com.hdyl.pintu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdyl.mine.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyListAdapter extends BaseAdapter {

    List<UserInfo> list;
    Context context;

    public MyListAdapter(Context context, List<UserInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<UserInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
            holder.tvScore = (TextView) convertView.findViewById(R.id.tvScore);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserInfo item = list.get(position);
        holder.tvNum.setText((position + 1) + "");


        holder.tvScore.setText(item.steps + "步");
        holder.tvDate.setText(getDateString(item.createDate, "yyyy年MM月dd日 HH时mm分ss秒"));
        holder.tvName.setText(item.username);
        holder.tvTime.setText(item.times + "秒");
        return convertView;
    }

    class ViewHolder {
        TextView tvNum, tvScore, tvDate, tvName, tvTime;
    }

    public String getDateString(String inputString, String formatString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(inputString);
            SimpleDateFormat dateFormatOutput = new SimpleDateFormat(formatString);
            return dateFormatOutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputString;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

}
