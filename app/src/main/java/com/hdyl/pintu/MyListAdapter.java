package com.hdyl.pintu;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdyl.mine.R;

public class MyListAdapter extends BaseAdapter{

	List<UserInfo> list;
	Context context;

	public MyListAdapter(Context context,List<UserInfo> list) {
		this.context = context;
		this.list=list;
	}

	public void setData(List<UserInfo> list){
		this.list=list;
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
			holder.tvDate =  (TextView) convertView.findViewById(R.id.tvDate);
			holder.tvName= (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		UserInfo item = list.get(position);
		holder.tvNum.setText((position+1)+"");
		holder.tvScore.setText(item.timeSecond+"步");
		holder.tvDate.setText(item.createDate);
		holder.tvName.setText(item.username);
		return convertView;
	}

	class ViewHolder {
		TextView tvNum,tvScore,tvDate,tvName;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

}
