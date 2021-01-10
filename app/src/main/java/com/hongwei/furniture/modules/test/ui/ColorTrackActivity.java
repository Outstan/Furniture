package com.hongwei.furniture.modules.test.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.hongwei.basiclib.colortracktextview.ColorTrackTextView;
import com.hongwei.basiclib.colortracktextview.IndicatorBaseAdapter;
import com.hongwei.basiclib.colortracktextview.TrackIndicatorView;
import com.hongwei.furniture.R;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: MainActivity1
 * Author: XYB
 * Date: 2020/12/25
 * Description: ColorTrackTextView测试Activity
 */
public class ColorTrackActivity extends AppCompatActivity {

    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private TrackIndicatorView mIndicatorContainer;
    private List<ColorTrackTextView> mIndicators;
    private ViewPager2 mViewPager;
    private String TAG = "ViewPagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mIndicators = new ArrayList<>();
        mIndicatorContainer = findViewById(R.id.indicator_view);
        mViewPager =  findViewById(R.id.view_pager);
        initIndicator();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Log.d(TAG, "createFragment: "+position);
                return ColorTrackFragment.newInstance(items[position]);
            }

            @Override
            public int getItemCount() {
                return items.length;
            }
        });

        /**
         * 添加一个切换的监听
         */
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    // 获取左边
                    ColorTrackTextView left = mIndicators.get(position);
                    // 设置朝向
                    left.setDirection(ColorTrackTextView.Direction.DIRECTION_RIGHT);
                    // 设置进度  positionOffset 是从 0 一直变化到 1 不信可以看打印
                    left.setCurrentProgress(1-positionOffset);

                    // 获取右边
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.DIRECTION_LEFT);
                    right.setCurrentProgress(positionOffset);
                }
            }
        });
    }

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            // 加入集合
            mIndicators.add(colorTrackTextView);
        }

        // 把新的加入容器
        mIndicatorContainer.setAdapter(new IndicatorBaseAdapter<ColorTrackTextView>() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public ColorTrackTextView getView(int position, ViewGroup parent) {
                return mIndicators.get(position);
            }

            @Override
            public void highLightIndicator(ColorTrackTextView indicatorView) {
                indicatorView.setCurrentProgress(1);
            }

            @Override
            public void restoreIndicator(ColorTrackTextView indicatorView) {
                indicatorView.setCurrentProgress(0);
            }

            @Override
            public View getBottomTrackView() {
                View view = new View(ColorTrackActivity.this);
                view.setBackgroundColor(Color.RED);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,8));
                return view;
            }
        },mViewPager);
    }
}
