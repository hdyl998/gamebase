package com.hdyl.mine.stage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdyl.baselib.base.adapterbase.BaseViewHolder;
import com.hdyl.baselib.base.adapterbase.SuperAdapter;
import com.hdyl.mine.MineItem;
import com.hdyl.mine.R;
import com.hdyl.mine.base.LoadingDialog;
import com.hdyl.mine.base.MineBaseActivity;
import com.hdyl.mine.game.GameActivity;
import com.hdyl.mine.tools.Tools;

/**
 * Date:2017/10/25 17:59
 * Author:liugd
 * Modification:
 **/


public class MineStageActivity extends MineBaseActivity implements AdapterView.OnItemClickListener {

    TextView tvTitle;
    AdapterView gridView;

    int[] arrNum = {R.drawable.num_0, R.drawable.num_1, R.drawable.num_2, R.drawable.num_3, R.drawable.num_4, R.drawable.num_5, R.drawable.num_6, R.drawable.num_7, R.drawable.num_8, R.drawable.num_9};

    SuperAdapter adapter;

    @Override
    public void initViews() {
        tvTitle = findViewByID(R.id.title);
        gridView = findViewByID(R.id.gridView);

        gridView.setAdapter(adapter = new SuperAdapter<StageList.StageItem>(mContext, StageList.getLists(), R.layout.item_stage) {
            @Override
            protected void onBind(BaseViewHolder holder, StageList.StageItem item, int position) {

                if (StageList.getPassIndex() + 1 > position) {//通过
                    holder.setImageResource(R.id.iv_item_point, R.drawable.point3);
                    holder.setBackgroundRes(R.id.ll_item_all, R.drawable.pass_bg);
                } else if (StageList.getPassIndex() + 1 == position) {//当前
                    holder.setVisibility(R.id.iv_item_point, View.VISIBLE);
                    holder.setImageResource(R.id.iv_item_point, R.drawable.point0);

                    holder.setBackgroundRes(R.id.ll_item_all, R.drawable.pass_bg);
                } else {//lock
                    holder.setImageBitmap(R.id.iv_item_point, null);
                    holder.setBackgroundRes(R.id.ll_item_all, R.drawable.lock);


                }
                ImageView imageView = holder.getView(R.id.iv_item_num0);
                ImageView imageView2 = holder.getView(R.id.iv_item_num1);
                ImageView imageView3 = holder.getView(R.id.iv_item_num2);

                int posi = position + 1;
                imageView.setImageResource(arrNum[posi / 100 % 10]);
                imageView2.setImageResource(arrNum[posi / 10 % 10]);
                imageView3.setImageResource(arrNum[posi % 10]);
//
//                if(StageList.getPassIndex() + 1 >= position) {
//
////                    holder.setVisibility(R.id.ll_number,View.VISIBLE);
//                }
//                else {
////                    holder.setVisibility(R.id.ll_number,View.GONE);
//                }

            }
        });
        gridView.setOnItemClickListener(this);
        updataProgress();
    }


    public void updataProgress() {
        TextView textView = findViewByID(R.id.title);
        textView.setText(String.format("当前进行到%d/%d", (StageList.getPassIndex() + 1), StageList.getLists().size()));
    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_mine_select;
    }


    @Override
    public int[] setClickIDs() {
        return new int[]{R.id.textViewBack,R.id.btn_start};
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewBack:
                mContext.finish();
                break;
            case R.id.btn_start:
                int index=StageList.getPassIndex()+1;
                if(StageList.getLists().size()<=index){
                    onItemClick(null,null,StageList.getLists().size()-1,0);
                }
                else {
                    onItemClick(null, null, index, 0);
                }
                break;
        }
    }

    int curIndex = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (StageList.getPassIndex() + 1 >= position) {
            curIndex = position;
            final StageList.StageItem item = StageList.getLists().get(position);
            new AlertDialog.Builder(this).setTitle("关卡信息").setMessage(item.toString()).setPositiveButton("立即挑战", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GameActivity.launch(mContext, new MineItem(item), requestCode);
                }
            }).setNegativeButton("取消",null).show();
        } else{
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("当前关卡还未解锁!\n请依次解锁后挑战!").setNegativeButton("我知道了", null).show();
//            GameActivity.launch(mContext, new MineItem(item), 1);
        }
    }

    int requestCode = 88;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {
            StageList.getLists().get(curIndex).isPass = true;
            StageList.getLists().get(curIndex).solveDate= Tools.getDateString();
            StageList.passIndex++;
            StageList.saveData();
            StageList.calcPassIndex();
            adapter.notifyDataSetChanged();
            updataProgress();
            if (curIndex == StageList.getLists().size() - 1) {
                LoadingDialog dialog = new LoadingDialog(mContext, "恭喜您完成所有的关卡的试炼！你是扫雷天才！特封赐予你扫雷英雄称号！截屏留下此时的荣誉时刻吧！^_^!");
                dialog.show();
            } else{
                curIndex++;
                String msg=String.format("您刚刚通过了%d关的关卡，还差%d关，即可通关！加油么么哒！^_^!",curIndex,StageList.getLists().size()-curIndex);
                new AlertDialog.Builder(this).setTitle("温馨提示").setMessage(msg).setNegativeButton("我知道了", null).show();
            }
        }
    }
}
