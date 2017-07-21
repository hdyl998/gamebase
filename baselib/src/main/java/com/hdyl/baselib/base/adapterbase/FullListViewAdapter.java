package com.hdyl.baselib.base.adapterbase;//package com.hdyl.myapplication.adapterbase;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import com.caiyu.qqsd.R;
//import com.caiyu.qqsd.lib.base.ISComCallBacks;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///***
// * 线性布局适配器，可以复用ITEM ,只支持一种LAYOUT
// *
// * @param <T>
// * @author Administrator
// */
//public abstract class FullListViewAdapter<T> {
//    private int mItemLayoutId;
//    private List<T> mDatas;
//
//    private List<BaseViewHolder> mVHCahces;// ����ViewHolder,����add��˳�򻺴棬
//    private LayoutInflater mInflater;
//
//    private LinearLayout mLayout;
//
//    public FullListViewAdapter(Context mContext, List<T> mDatas, int mItemLayoutId) {
//        this.mItemLayoutId = mItemLayoutId;
//        this.mDatas = mDatas;
//        this.mInflater = LayoutInflater.from(mContext);
//        mVHCahces = new ArrayList<>();
//        initDatas();//初始化一些数据
//    }
//
//    public FullListViewAdapter(LinearLayout mLayout, List<T> mDatas, int mItemLayoutId) {
//        this(mLayout.getContext(), mDatas, mItemLayoutId);
//        bindLinearLayout(mLayout);
//    }
//
//    public FullListViewAdapter(LinearLayout mLayout, int mItemLayoutId) {
//        this(mLayout, null, mItemLayoutId);
//    }
//
//
//    public int getItemLayoutId() {
//        return mItemLayoutId;
//    }
//
//    public void setItemLayoutId(int mItemLayoutId) {
//        this.mItemLayoutId = mItemLayoutId;
//    }
//
//    public List<T> getDatas() {
//        return mDatas;
//    }
//
//    public void setDatas(List<T> mDatas) {
//        this.mDatas = mDatas;
//        notifyDataSetChanged();
//    }
//
//    protected void initDatas() {
//
//    }
//
//    public LinearLayout getLinearLayout() {
//        return mLayout;
//    }
//
//    public void bindLinearLayout(LinearLayout mLayout) {
//        this.mLayout = mLayout;
////		this.mLayout.setOrientation(LinearLayout.VERTICAL);
//        notifyDataSetChanged();
//    }
//
//    public void notifyDataSetChanged() {
//        if (null != mLayout) {
//            if (null != mDatas && !mDatas.isEmpty()) {
//                int childCount = mLayout.getChildCount();
//                int dataSize = mDatas.size();
//                if (dataSize > childCount) {
//
//                } else if (dataSize < childCount) {
//                    mLayout.removeViews(dataSize, childCount - dataSize);
//                    while (mVHCahces.size() > dataSize) {
//                        mVHCahces.remove(mVHCahces.size() - 1);
//                    }
//                }
//                for (int i = 0; i < dataSize; i++) {
//                    BaseViewHolder holder;
//                    if (mVHCahces.size() - 1 >= i) {
//                        holder = mVHCahces.get(i);
//                    } else {
//                        holder = BaseViewHolder.get(mInflater, null, mLayout, getItemLayoutId());
//                        mVHCahces.add(holder);
//                    }
//                    onBind(holder, mDatas.get(i), i);
//                    if (null == holder.getItemView().getParent()) {
//                        mLayout.addView(holder.getItemView());
//                    }
//                    if (onItemClickListener != null) {
//                        if (onClickListener == null) {
//                            onClickListener = new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int position = (int) v.getTag(R.id.iv_default);
//                                    onItemClickListener.call(mDatas.get(position), position);
//                                }
//                            };
//                        }
//                        holder.getItemView().setTag(R.id.iv_default, i);
//                        holder.getItemView().setOnClickListener(onClickListener);
//                    }
//                }
//            } else {
//                mLayout.removeAllViews();
//            }
//        } else {
//            mLayout.removeAllViews();
//        }
//    }
//
//
//    ISComCallBacks<T, Integer> onItemClickListener;
//
//
//    public void setOnItemClickListener(ISComCallBacks<T, Integer> onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    private View.OnClickListener onClickListener;
//
//
//    public abstract void onBind(BaseViewHolder holder, T item, int position);
//
//}