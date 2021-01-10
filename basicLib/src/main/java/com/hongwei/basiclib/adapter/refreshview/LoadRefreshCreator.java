package com.hongwei.basiclib.adapter.refreshview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.hongwei.basiclib.R;

/**
 * @author xyb
 * @description:上拉加载
 * @date : 2021-01-05
 */
public class LoadRefreshCreator extends LoadViewCreator{
    private View mLoadRefreshIv;

    @Override
    public View getLoadView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header_view, parent, false);
        mLoadRefreshIv = refreshView.findViewById(R.id.refresh_iv);
        return refreshView;
    }

    @Override
    public void onPull(int currentDragHeight, int loadViewHeight, int currentLoadStatus) {
        float rotate = ((float) currentDragHeight) / loadViewHeight;
        // 不断上拉拉的过程中不断的旋转图片
        mLoadRefreshIv.setRotation(rotate * 360);
    }

    @Override
    public void onLoading() {
        //加载的时候不断旋转
        RotateAnimation animation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(1000);
        mLoadRefreshIv.startAnimation(animation);
    }

    @Override
    public void onStopLoad() {
        // 停止加载的时候清除动画
        mLoadRefreshIv.setRotation(0);
        mLoadRefreshIv.clearAnimation();
    }
}
