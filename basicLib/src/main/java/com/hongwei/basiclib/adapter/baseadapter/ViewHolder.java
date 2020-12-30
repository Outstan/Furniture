package com.hongwei.basiclib.adapter.baseadapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author xyb
 * @description RecycleView的ViewHolder封装
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    // 用来存放子View减少findViewById的次数
    private SparseArray<View> mViews;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 设置TextView文本
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 通过id获取view
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找
        View view = mViews.get(viewId);
        if (view == null) {
            // 直接从ItemView中找
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置View的Visibility
     */
    public ViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 设置ImageView的资源
     */
    public ViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置图片通过网络路径加载
     */
    public ViewHolder setImageByUrl(int viewId, String url) {
        ImageView imageView = getView(viewId);
        if (url == null) {
            throw new NullPointerException("url is null!");
        }
        //设置图片显示

        return this;
    }
}
