package com.hdyl.baselib.utils.text;//package com.caiyu.qqsd.lib.utils.text;
//
//import android.content.Context;
//import android.support.annotation.ColorRes;
//import android.support.annotation.DimenRes;
//import android.text.SpannableString;
//import android.text.SpannableStringBuilder;
//import android.text.Spanned;
//import android.text.TextPaint;
//import android.text.style.AbsoluteSizeSpan;
//import android.text.style.ClickableSpan;
//import android.text.style.ForegroundColorSpan;
//import android.text.style.SuperscriptSpan;
//import android.view.View;
//import android.widget.TextView;
//
//import com.caiyu.qqsd.util.ohter.Tools;
//
///**
// * 可以定义不同样式的textstring
// * Created by liugd on 2017/2/22.
// */
//@Deprecated
//public class TextStyle {
//    /***
//     * 获得定制样式文本
//     *
//     * @param styleTexts
//     * @return
//     */
//    public static CharSequence getText(TextStyle... styleTexts) {
//        SpannableStringBuilder sb = new SpannableStringBuilder();
//        for (TextStyle text : styleTexts) {
//            sb.append(text.getSpanableString());
//        }
//
//        return sb;
//    }
//
//    /***
//     * 设置文本不同样式
//     *
//     * @param textView
//     * @param textStyles
//     */
//    public static void setTextViewStyle(TextView textView, TextStyle... textStyles) {
//        textView.setText(getText(textStyles));
//    }
//
//    public static void setTextViewStyleWithLink(TextView textView, TextStyle... textStyles) {
//        setTextViewStyle(textView, textStyles);
//        textView.setMovementMethod(Tools.LinkMovementClickMethod.getInstance());// textView的块链接生效
//    }
//
//
//    public int pxSize;//字的大小
//    public int color;//颜色
//    public String text;//文本
//    public boolean isUp;//是否是上角标
//    public View.OnClickListener onClickListener;
//
//    public static TextStyle create(String str) {
//        return new TextStyle(str);
//    }
//
//    public TextStyle(String str) {
//        this.text = str;
//    }
//
//    /***
//     * 设置实际大小
//     *
//     * @param pxSize
//     * @return
//     */
//    public TextStyle setPxSize(int pxSize) {
//        this.pxSize = pxSize;
//        return this;
//    }
//
//    public TextStyle setResSize(Context context, @DimenRes int resSize) {
//        this.pxSize = (int) context.getResources().getDimension(resSize);
//        return this;
//    }
//
//
//    public TextStyle setOnClickListener(View.OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//        return this;
//    }
//
//    public TextStyle setResColor(Context context, @ColorRes int resColor) {
//        this.color = context.getResources().getColor(resColor);
//        return this;
//    }
//
//    public TextStyle setColor(int color) {
//        this.color = color;
//        return this;
//    }
//
//    public TextStyle setUp(boolean up) {
//        isUp = up;
//        return this;
//    }
//
//
//    public TextStyle setText(String text) {
//        this.text = text;
//        return this;
//    }
//
//    public TextStyle(String str, int abSize, int color) {
//        this(str, abSize, color, false);
//    }
//
//    public TextStyle(String str, int abSize, int color, boolean isUP) {
//        this.pxSize = abSize;
//        this.color = color;
//        this.text = str;
//        this.isUp = isUP;
//    }
//
//    public TextStyle(Context context, String str, @DimenRes int resSize, @ColorRes int resColor) {
//        this(context, str, resSize, resColor, false);
//    }
//
//    public TextStyle(Context context, String str, @DimenRes int resSize, @ColorRes int resColor, boolean isUp) {
//        this.setResColor(context, resColor).setResSize(context, resSize).setUp(isUp).setText(str);
//    }
//
//    public SpannableString getSpanableString() {
//        SpannableString ss = new SpannableString(text);
//        int len = ss.length();
//        if (pxSize != 0)
//            ss.setSpan(new AbsoluteSizeSpan(pxSize), 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        if (isUp) {
//            ss.setSpan(new SuperscriptSpan(), 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        if (onClickListener != null) {
//            ss.setSpan(new ClickableSpan() {
//                @Override
//                public void onClick(View widget) {
//                    onClickListener.onClick(widget);
//                }
//
//                @Override
//                public void updateDrawState(TextPaint ds) {
//                    if (color != 0) {
//                        ds.setColor(color);
//                    } else {
//                        super.updateDrawState(ds);
//                    }
//                }
//            }, 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//            if (color != 0)
//                ss.setSpan(new ForegroundColorSpan(color), 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        //更多SPAN 的用法在这里 http://www.2cto.com/kf/201512/455755.html
//        return ss;
//    }
//
//////    使用方法
////    public void use(){
////        TextStyleUtils.setTextViewStyle(tvTitle, TextStyleUtils.TextStyle.createFromCache("您的"),
////                TextStyleUtils.TextStyle.createFromCache("20元").setResColor(mContext, R.color._DD4A4A),
////                TextStyleUtils.TextStyle.createFromCache("礼包已经到账！")
////
////        CharSequence ch= TextStyleUtils.getText(new TextStyleUtils.TextStyle(mContext,datas[i], R.dimen.n48px,R.color._464646),
////                new TextStyleUtils.TextStyle(mContext,"\n"+datasTitle[i],R.dimen.n24px,R.color._999999));
////        textViews[i].setText(ch);
////    }
//}
