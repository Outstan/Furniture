package com.hongwei.basiclib.adapter.refreshview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author xyb
 * @description:上拉加载更多的辅助类为了匹配所有效果
 * @date : 2021-01-05
 */
public abstract class LoadViewCreator {
    /**
     * 获取上拉加载更多的View
     *
     * @param context 上下文
     * @param parent  RecyclerView
     */
    public abstract View getLoadView(Context context, ViewGroup parent);

    /**
     * 正在上拉
     *
     * @param currentDragHeight    当前拖动的高度
     * @param loadViewHeight    总的加载高度
     * @param currentLoadStatus 当前状态
     */
    public abstract void onPull(int currentDragHeight, int loadViewHeight, int currentLoadStatus);

    /**
     * 正在加载中
     */
    public abstract void onLoading();

    /**
     * 停止加载
     */
    public abstract void onStopLoad();
}
