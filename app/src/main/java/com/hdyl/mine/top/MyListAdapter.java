package com.hdyl.mine.top;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.tools.GameInfo;
import com.hdyl.mine.tools.Tools;

import java.util.List;

public class MyListAdapter extends BaseAdapter {

	List<GameInfo> list;
	Context context;

	int color[];

	public MyListAdapter(Context context, List<GameInfo> list) {
		this.context = context;
		this.list = list;

		for (GameInfo info : list) {
			info.createTime = Tools.getPostTime(info.createTime);
		}
		color = new int[4];
		color[0] = Color.GREEN;
		color[1] = Color.WHITE;
		color[2] = Color.parseColor("#DAB725");
		color[3] = context.getResources().getColor(R.color.gray);
	}

	public void setData(List<GameInfo> list) {
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
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GameInfo item = list.get(position);
		holder.tvNum.setText((position + 1) + "");
		holder.tvScore.setText(item.second / 10f + " 秒");
		holder.tvDate.setText(item.createTime);
		holder.tvName.setText(item.name);
		switch (position) {
			case 0:
				holder.setTextColor(color[0]);
				break;
			case 1:
				holder.setTextColor(color[1]);
				break;
			case 2:
				holder.setTextColor(color[2]);
				break;
			default:
				holder.setTextColor(color[3]);
				break;
		}
		return convertView;
	}

	class ViewHolder {
		TextView tvNum, tvScore, tvDate, tvName;

		public void setTextColor(int color) {
			tvNum.setTextColor(color);
			tvScore.setTextColor(color);
			tvDate.setTextColor(color);
			tvName.setTextColor(color);
		}
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

}
