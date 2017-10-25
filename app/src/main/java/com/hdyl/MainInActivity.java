package com.hdyl;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hdyl.baselib.base.BaseActivity;
import com.hdyl.baselib.base.adapterbase.BaseViewHolder;
import com.hdyl.baselib.base.adapterbase.SuperAdapter;
import com.hdyl.llk.MainLlkActivity;
import com.hdyl.llk.StartLlkActivity;
import com.hdyl.m2048.Main2048Activity;
import com.hdyl.mine.MainMineActivity;
import com.hdyl.mine.R;
import com.hdyl.pintu.MainPintuActivity;
import com.hdyl.tetris.MainTetrisActivity;
import com.hdyl.tetris2.MainTetris2Activity;
import com.hdyl.xiaoxiaole.MainXxlActivity;

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
        listDatas.add(new DataItem().setAppName(getString(R.string.app_name_pintu)).setIconRes(R.drawable.icon_tetris).setRank(R.drawable.point3).setGoClazz(MainPintuActivity.class));
        listDatas.add(new DataItem().setAppName(getString(R.string.app_name_xxl)).setIconRes(R.drawable.fruit2).setRank(R.drawable.point3).setGoClazz(MainXxlActivity.class));
        listDatas.add(new DataItem().setAppName(getString(R.string.app_name_llk)).setIconRes(R.drawable.aa42).setRank(R.drawable.point3).setGoClazz(StartLlkActivity.class));
        listDatas.add(null);
        listView.setAdapter(new SuperAdapter<DataItem>(mContext, listDatas, R.layout.item_app) {
            @Override
            protected void onBind(BaseViewHolder holder, DataItem item, int position) {

                if (item != null) {
                    holder.setText(R.id.tvTitle, item.appName);
                    holder.setText(R.id.tvNote, item.strNote);
                    holder.setImageResource(R.id.imageView, item.iconRes);
                    holder.setImageResource(R.id.ivRank, item.rank);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listDatas.get(position) == null) {
//                    PopupMenu popupMenu = new PopupMenu(mContext, view);
//                    Menu menu=popupMenu.getMenu();
//                    menu.add(0, 1, Menu.NONE, "蓝牙发送").setIcon(android.R.drawable.ic_menu_send);
//
//                    //添加子菜单
//                    SubMenu subMenu = menu.addSubMenu(0,2,Menu.NONE, "重要程度>>").setIcon(android.R.drawable.ic_menu_share);
//                    //添加子菜单项
//                    subMenu.add(2, 201, 1, "☆☆☆☆☆");
//                    subMenu.add(2, 202, 2, "☆☆☆");
//                    subMenu.add(2, 203, 3, "☆");
//                    popupMenu.show();
//                    PopupWindow popupWindow = new PopupWindow(mContext);
//                    View viewRoot = View.inflate(mContext, R.layout.layout_test, null);
//                    popupWindow.setContentView(viewRoot);
//                    popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    popupWindow.setFocusable(true);
//                    popupWindow.setOutsideTouchable(false);
//                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//                    popupWindow.showAsDropDown(view);

//                    TextView textView = (TextView) viewRoot.findViewById(R.id.gameView);


//                    Dialog dialog = new AlertDialog.Builder(mContext).setView(R.layout.layout_test).create();
//                    Dialog dialog = new Dialog(mContext);
//                    dialog.setContentView(R.layout.layout_test);
//                    dialog.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);
////                    dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    dialog.show();
//                    View view1 = (View) dialog.findViewById(R.id.ll_main).getParent();
//                    view1.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else
                    startActivity(new Intent(mContext, listDatas.get(position).goClazz));
            }
        });


    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_mainin;
    }
}
