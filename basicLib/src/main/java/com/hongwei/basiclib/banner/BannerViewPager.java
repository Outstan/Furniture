package com.hongwei.basiclib.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xyb
 * @description:轮播
 * @date : 2020-12-21
 */
public class BannerViewPager extends ViewPager {
    private BannerAdapter mAdapter;

    // 2.实现自动轮播 - 发送消息的msgWhat
    private final int SCROLL_MSG = 0x0011;

    // 2.实现自动轮播 - 页面切换间隔时间
    private int mCutDownTime = 3500;

    private BannerScroller mScroller;

    // 2.实现自动轮播 - 发送消息Handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // 每隔*s后切换到下一页
            setCurrentItem(getCurrentItem() + 1);
            // 不断循环执行
            startRoll();
        }
    };

    private List<View> mConverViews;

    private Activity mActivity;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mActivity = (Activity) context;

        try {
            // 3.改变ViewPager切换的速率
            // 3.1 duration 持续的时间  局部变量
            // 3.2.改变 mScroller private 通过反射设置
            Field field = ViewPager.class.getDeclaredField("mScroller");
            // 设置参数  第一个object当前属性在哪个类  第二个参数代表要设置的值
            mScroller = new BannerScroller(context);
            // 设置为强制改变private
            field.setAccessible(true);
            field.set(this,mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mConverViews = new ArrayList<>();
    }

    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        setAdapter(new BannerPagerAdapter());

        mActivity.getApplication().registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    /**
     * 2.实现自动轮播
     */
    public void startRoll(){
        // 清除消息
        mHandler.removeMessages(SCROLL_MSG);
        // 消息  延迟时间  让用户自定义  有一个默认  3500
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG,mCutDownTime);
    }

    /**
     * 3.设置切换页面动画持续的时间
     */
    public void setScrollerDuration(int scrollerDuration){
        mScroller.setScrollerDuration(scrollerDuration);
    }

    /**
     * 2.销毁Handler停止发送  解决内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
        mActivity.getApplication().unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // 返回一个很大的值，确保可以无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 这么写就对了，看了源码应该就明白
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View bannerView = mAdapter.getView(position%mAdapter.getCount(),getConverView());
            container.addView(bannerView );
            bannerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return bannerView;
        }

        private View getConverView() {
            for (int i=0;i<mConverViews.size();i++){
                if (mConverViews.get(i).getParent()!=null){
                    return mConverViews.get(i);
                }
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 销毁回调的方法  移除页面即可
            container.removeView((View) object);
            mConverViews.add((View) object);
        }
    }

    //管理Activity的生命周期
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new DefaulActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            //是不是监听的当前Activity的生命周期
            if (activity == mActivity) {
                mHandler.sendEmptyMessageDelayed(mCutDownTime, SCROLL_MSG);
            }
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            if (activity == mActivity) {
                mHandler.removeMessages(SCROLL_MSG);
            }
        }
    };
}
