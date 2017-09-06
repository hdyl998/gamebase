package com.hdyl;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hdyl.baselib.base.BaseActivity;
import com.hdyl.baselib.base.adapterbase.BaseViewHolder;
import com.hdyl.baselib.base.adapterbase.SuperAdapter;
import com.hdyl.m2048.Main2048Activity;
import com.hdyl.mine.MainMineActivity;
import com.hdyl.mine.R;
import com.hdyl.tetris.MainTetrisActivity;
import com.hdyl.tetris2.MainTetris2Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugd on 2017/9/4.
 */

public class MainInActivity extends BaseActivity {
    ListView listView;


    private static class DataItem {
        public String appName;
        public String strNote;
        public int iconRes;
        public int rank = R.drawable.point3;
        public Class<?> goClazz;

        public DataItem setAppName(String appName) {
            this.appName = appName;
            strNote = "经典【" + appName + "】游戏";
            return this;
        }

        public DataItem setStrNote(String strNote) {
            this.strNote = strNote;
            return this;
        }

        public DataItem setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public DataItem setRank(int rank) {
            this.rank = rank;
            return this;
        }

        public DataItem setGoClazz(Class<?> goClazz) {
            this.goClazz = goClazz;
            return this;
        }
    }

    List<DataItem> listDatas;

    @Override
    public void initViews() {
        listView = findViewByID(R.id.listView);
        listDatas = new ArrayList<>();
        listDatas.add(new DataItem().setAppName(getString(R.string.app_name_mine)).setIconRes(R.drawable.icon_mine).setRank(R.drawable.point3).setGoClazz(MainMineActivity.class));
        listDatas.add(new DataItem().setAppName(getString(R.string.app_name_tetris)).setIconRes(R.drawable.icon_tetris).setRank(R.drawable.point2).setGoClazz(MainTetrisActivity.class));
        listDatas.add(new DataItem().setAppName(getString(R.string.app_name_tetris2)).setIconRes(R.drawable.icon_tetris).setRank(R.drawable.point3).setGoClazz(MainTetris2Activity.class));
        listDatas.add(new DataItem().setAppName(getString(R.string.app_name_2048)).setIconRes(R.drawable.icon_2048).setRank(R.drawable.point3).setGoClazz(Main2048Activity.class));


        listView.setAdapter(new SuperAdapter<DataItem>(mContext, listDatas, R.layout.item_app) {
            @Override
            protected void onBind(BaseViewHolder holder, DataItem item, int position) {
                holder.setText(R.id.tvTitle, item.appName);
                holder.setText(R.id.tvNote, item.strNote);
                holder.setImageResource(R.id.imageView, item.iconRes);
                holder.setImageResource(R.id.ivRank, item.rank);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, listDatas.get(position).goClazz));
            }
        });

    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_mainin;
    }
}
