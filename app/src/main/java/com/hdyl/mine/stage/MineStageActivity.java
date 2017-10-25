package com.hdyl.mine.stage;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdyl.baselib.base.adapterbase.BaseViewHolder;
import com.hdyl.baselib.base.adapterbase.SuperAdapter;
import com.hdyl.mine.R;
import com.hdyl.mine.base.MineBaseActivity;

/**
 * Date:2017/10/25 17:59
 * Author:liugd
 * Modification:
 **/


public class MineStageActivity extends MineBaseActivity {

    TextView tvTitle;
    AdapterView gridView;

    int[] arrNum = { R.drawable.num_0, R.drawable.num_1, R.drawable.num_2, R.drawable.num_3, R.drawable.num_4, R.drawable.num_5, R.drawable.num_6, R.drawable.num_7, R.drawable.num_8, R.drawable.num_9 };


    @Override
    public void initViews() {
        tvTitle = findViewByID(R.id.title);
        gridView = findViewByID(R.id.gridView);

        gridView.setAdapter(new SuperAdapter<StageList.StageItem>(mContext,StageList.getLists(),R.layout.item_mine_stage) {
            @Override
            protected void onBind(BaseViewHolder holder, StageList.StageItem item, int position) {
                ImageView imageView=holder.getView(R.id.iv_item_num0);
                ImageView imageView2=holder.getView(R.id.iv_item_num1);
                ImageView imageView3=holder.getView(R.id.iv_item_num2);

                int posi=position+1;
                imageView.setImageResource(arrNum[posi/100%10]);
                imageView2.setImageResource(arrNum[posi/10%10]);
                imageView3.setImageResource(arrNum[posi%10]);

                holder.setText(R.id.tv_info,item.toString());

                holder.setImageResource(R.id.iv_item_point,item.isPass?R.drawable.point3:R.drawable.point0);
            }
        });
    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_mine_select;
    }


    @Override
    public int[] setClickIDs() {
        return new int[]{R.id.textViewBack};
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewBack:
                mContext.finish();
                break;
        }
    }
}
