package com.hdyl.baselib.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyl.baselib.R;
import com.hdyl.baselib.base.App;

public class ToastUtils {
    /**
     * 显示自定义Toast
     *
     * @param mContext application mContext.Note:don't deliver the Activity Context avoid to memory
     * leak.
     * @param string string you want to show.
     */
    private static Toast toast;
    private static int lastResid;


    public static void makeTextAndShow(String string) {
        makeTextAndShow(string, 0);
    }


    public static void makeTextAndShow(String string, int drawableID) {
        Context context = App.getContext();
        if (toast == null) {
            View toastRoot = View.inflate(context, R.layout.view_mytoast, null);
            toast = new Toast(context);
            toast.setView(toastRoot);// 设置View
            toast.setGravity(Gravity.CENTER, 0, Tools.dip2px(context, 60));
            toast.setDuration(Toast.LENGTH_SHORT);// 设置时间
        }
        if (lastResid != drawableID) {
            lastResid = drawableID;
            TextView textView = (TextView) toast.getView();
            if (drawableID != 0) {
                TextViewUtils.setTextViewDrawable(textView, drawableID, TextViewUtils.DIRECTION_UP);
            } else {
                textView.setCompoundDrawables(null, null, null, null);
            }
        }
        toast.setText(string);// 设置内容
        toast.show();
    }


    public static void makeTextWithOK(String str) {
        makeTextAndShow(str, android.R.drawable.sym_def_app_icon);
    }

    /**
     * show content for res id.
     *
     * @param id res id.
     */
    public static void makeTextAndShow(int id) {
        Context context = App.getContext();
        makeTextAndShow(context.getResources().getString(id));
    }

    public static void showNoifatication(String tag, String title, String message) {
        NotificationManager manager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //API level 11
        Notification.Builder builder = new Notification.Builder(App.getContext());
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        Notification notification = builder.getNotification();
        manager.notify(tag, android.R.drawable.sym_def_app_icon, notification);
    }
}
