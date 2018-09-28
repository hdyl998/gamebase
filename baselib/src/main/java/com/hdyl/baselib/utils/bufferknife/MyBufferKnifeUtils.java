package com.hdyl.baselib.utils.bufferknife;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class MyBufferKnifeUtils {

    //    @MyBindView(R.id.text)
//    private TextView textView;
    public static void inject(Object obj, View rootView) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                MyBindView bindView = field.getAnnotation(MyBindView.class);
                if (bindView != null) {
                    field.setAccessible(true);
                    field.set(obj, rootView.findViewById(bindView.value()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void inject(Fragment fragment) {
        inject(fragment, fragment.getView());
    }


    public static void inject(Activity activity) {
        inject(activity, activity.getWindow().getDecorView());
    }
}
