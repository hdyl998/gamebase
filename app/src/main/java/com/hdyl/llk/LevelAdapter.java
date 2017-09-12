package com.hdyl.llk;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hdyl.mine.R;

public class LevelAdapter extends BaseAdapter {

	Context context;
	int[] arrPoint = { R.drawable.point0, R.drawable.point1, R.drawable.point2, R.drawable.point3 };
	int[] arrNum = { R.drawable.num_0, R.drawable.num_1, R.drawable.num_2, R.drawable.num_3, R.drawable.num_4, R.drawable.num_5, R.drawable.num_6, R.drawable.num_7, R.drawable.num_8, R.drawable.num_9 };
	int totalCount;
	SaveData saveData = SaveData.getInstance();
	List<Level> list = saveData.getListLevels();

	public LevelAdapter(Context context) {

		this.context = context;
		// UserInfo userInfo = MyApplication.getMyApplicationInstance()
		// .getCurUserInfo();
		// this.context = context;// 上下文
		// this.userLevel = userInfo.getLevel();// 当前关级别，表示可玩多少关
		// DBHelper dbHelper = new DBHelper(context);
		// list = dbHelper.getAllScoreOfUser(userInfo.getUid());// 得到一个用户所有关卡数据
		// this.totalCount = dbHelper.getLevelCount();// 关卡总数
		// // list.add(new ScoreInfo(1, 0));
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_stage, null);
			holder = new ViewHolder();
			holder.ivPoint = (ImageView) convertView.findViewById(R.id.iv_item_point);
			holder.ivNum0 = (ImageView) convertView.findViewById(R.id.iv_item_num0);
			holder.ivNum1 = (ImageView) convertView.findViewById(R.id.iv_item_num1);
			holder.ivNum2 = (ImageView) convertView.findViewById(R.id.iv_item_num2);
			holder.number = convertView.findViewById(R.id.ll_number);
			holder.lvAll = convertView.findViewById(R.id.ll_item_all);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Level item = list.get(position);
		if (item.score > 0 || saveData.currentLevel == position) {// 解锁标志
			holder.number.setVisibility(View.VISIBLE);
			holder.ivPoint.setVisibility(View.VISIBLE);
			holder.lvAll.setBackgroundResource(R.drawable.pass_bg);

			holder.ivNum1.setVisibility(View.VISIBLE);

			int num = position + 1;//
			int bai = (num / 100) % 10;
			int shi = (num / 10) % 10;
			if (bai == 0) {
				holder.ivNum0.setVisibility(View.GONE);
				if (shi == 0) {
					holder.ivNum1.setVisibility(View.GONE);
				}
			} else {
				holder.ivNum0.setVisibility(View.VISIBLE);
				holder.ivNum0.setImageResource(arrNum[bai]);
			}
			holder.ivNum1.setImageResource(arrNum[shi]);
			holder.ivNum2.setImageResource(arrNum[num % 10]);
			holder.ivPoint.setImageResource(arrPoint[getIndex(position, item.score, list.get(position).getValue() / 2)]);// 设置得分
		} else {// 锁标志
			holder.number.setVisibility(View.GONE);
			holder.ivPoint.setVisibility(View.GONE);
			holder.lvAll.setBackgroundResource(R.drawable.lock);
		}

		return convertView;
	}

	//定义的得分规则
	public static int getIndex(int level, int score, int value) {
		if (score == 0)
			return 0;
		int line = value;
		if (score > line * 44)
			return 3;
		if (score > line * 22)
			return 2;
		if (score > line * 11)
			return 1;
		return 0;
	}

	class ViewHolder {
		ImageView ivPoint, ivNum0, ivNum1, ivNum2;// 点,数字1，数字2
		View number;
		View lvAll;// 全部
	}

}
