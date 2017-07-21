package com.hdyl.baselib.utils.text;

import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.caiyu.qqsd.util.ohter.Tools;

/**
 * 文本样式的基类
 * Created by liugd on 2017/4/12.
 */

public abstract class ITextStyle {

    protected String mStr;

    public ITextStyle(String mStr) {
        this.mStr = mStr;
    }

    public abstract CharSequence getString();


    public static void setTextStyle(TextView textView, ITextStyle... objs) {
        boolean isLink = false;
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (ITextStyle text : objs) {
            sb.append(text.getString());
            if (text instanceof ClickTextStyle) {
                isLink = true;//可以点击
            }
        }
        textView.setText(sb);
        //最后使用
        if (isLink == true)
            textView.setMovementMethod(Tools.LinkMovementClickMethod.getInstance());// textView的块链接生效
    }

    /***
     * 获得所有的strings集合
     *
     * @param objs
     * @return
     */
    public static CharSequence getTexts(ITextStyle... objs) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (ITextStyle text : objs) {
            sb.append(text.getString());
        }
        return sb;
    }

    /***
     * 创建一个普通的文本
     *
     * @param mStr
     * @return
     */
    public static DefaultTextStyle createDefaultText(String mStr) {
        return new DefaultTextStyle(mStr);
    }

    /***
     * 创建一个上角标的文本
     *
     * @param mStr
     * @return
     */
    public static UpTextStyle createUpText(String mStr) {
        return new UpTextStyle(mStr);
    }

    /***
     * 创建一个可以点击的文本，可以调节颜色
     *
     * @param mStr
     * @return
     */
    public static ClickTextStyle createClickText(String mStr) {
        return new ClickTextStyle(mStr);
    }

    /***
     * 创建一个变色的文本
     *
     * @param mStr
     * @return
     */
    public static ColorTextStyle createColorText(String mStr) {
        return new ColorTextStyle(mStr);
    }

    /***
     * 创建一个复合文本，里面可以调节颜色，字的大小，上角标
     *
     * @param mStr
     * @return
     */
    public static ComplexTextStyle createComplexText(String mStr) {
        return new ComplexTextStyle(mStr);
    }

    /***
     * 创建一个复合文本，里面可以调节颜色，字的大小，上角标
     *
     * @param mStr
     * @return
     */
    public static ComplexTextStyle create(String mStr) {//默认用复合文本，方便使用
        return new ComplexTextStyle(mStr);
    }

    /****
     * 创建一个可变大小的的文本
     *
     * @param mStr
     * @return
     */
    public static SizeTextStyle createSizeText(String mStr) {
        return new SizeTextStyle(mStr);
    }


    public static BoldTextStyle createBoldText(String mStr) {
        return new BoldTextStyle(mStr);
    }

    public static DrawableTextStyle createDrawableText(String mStr) {
        return new DrawableTextStyle(mStr);
    }
    public static ImageTextStyle createImageText(String mStr) {
        return new ImageTextStyle(mStr);
    }


}
