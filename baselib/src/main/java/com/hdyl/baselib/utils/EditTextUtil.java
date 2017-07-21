package com.hdyl.baselib.utils;

import android.widget.EditText;

import com.hdyl.baselib.base.interfaceImpl.TextWatcherImpl;
import com.hdyl.baselib.utils.convert.NumberUtil;

/**
 * 视图工具类
 * Created by liugd on 2017/1/3.
 */

public class EditTextUtil {


    /***
     * 设置编辑框最大限值，如果超过这个值，则取最大值
     *
     * @param editText  编辑框
     * @param maxVarStr 最大值时的显示数字
     * @param maxVar    最大值
     */
    public static void setEditMaxVar(final EditText editText, final String maxVarStr, final double maxVar) {
        editText.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double data = NumberUtil.convertToDouble(s.toString().trim());
                if (data > maxVar) {
                    editText.setText(maxVarStr);
                    editText.setSelection(maxVarStr.length());
                }
            }
        });
    }

    /***
     * 设置编辑框最大限值，如果超过这个值，则取最大值
     *
     * @param editText  编辑框
     * @param maxVarStr 最大值时的显示数字
     * @param maxVar    最大值
     */
    public static void setEditVarRange(final EditText editText, final String minVarStr, final double minVar, final String maxVarStr, final double maxVar) {
        editText.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string=s.toString().trim();
                if(string.length()!=0){//长度不为0才做处理，不然不能删除数据
                    double data = NumberUtil.convertToDouble(string);
                    if (data > maxVar) {
                        editText.setText(maxVarStr);
                        editText.setSelection(maxVarStr.length());
                    } else if (data < minVar) {
                        editText.setText(minVarStr);
                        editText.setSelection(minVarStr.length());
                    }
                }
            }
        });
    }

    /**
     * 限制文只能输入两位小数点
     *
     * @param editText
     */
    public static void setEditPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                    }
                }
            }
        });
    }
}
