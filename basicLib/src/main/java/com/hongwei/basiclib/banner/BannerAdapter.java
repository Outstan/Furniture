package com.hongwei.basiclib.banner;

import android.view.View;

/**
 * @author xyb
 * @description:banner适配器
 * @date : 2020-12-21
 */
public abstract class BannerAdapter {
    public abstract View getView(int position,View converView);

    public abstract int getCount();

    public abstract String getBannerDesc(int mCurrentPosition);
}
