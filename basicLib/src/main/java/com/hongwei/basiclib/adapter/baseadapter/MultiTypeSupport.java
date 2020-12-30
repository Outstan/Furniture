package com.hongwei.basiclib.adapter.baseadapter;

/**
 * @author xyb
 * @description RecycleView 多布局
 */
public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    public int getLayoutId(T item, int position);
}
