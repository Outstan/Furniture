package com.hongwei.basiclib.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.hongwei.basiclib.R;

/**
 * @author xyb
 * @description:自定义的轮播界面
 * @date : 2020-12-22
 */
public class BannerView extends RelativeLayout {
    // 4.自定义BannerView - 轮播的ViewPager
    private BannerViewPager mBannerVp;
    // 4.自定义BannerView - 轮播的描述
    private TextView mBannerDescTv;
    // 4.自定义BannerView - 点的容器
    private LinearLayout mDotContainerView;
    // 4.自定义BannerView - 自定义的BannerAdapter
    private BannerAdapter mAdapter;
    //自定义BannerView - 底部
    private RelativeLayout mBannerButtom;
    //自定义BannerView - 整个Banner
    private RelativeLayout mBanner;

    private Context mContext;
    //初始化点的指示器 - 点选中的Drawable
    private Drawable mIndicatorFocusDrawable;
    //初始化点的指示器 - 点未选中的Drawable
    private Drawable mIndicatorNormalDrawable;
    //指示器当前位置的点
    private int mCurrentPosition;
    //获取点的位置
    private int mDotGravity = 1;
    //获取点的大小
    private int mDotSize = 8;
    //获取点的距离
    private int mDotDistance = 2;
    //底部的颜色
    private int mBottomColor = Color.parseColor("#5d000000");
    //宽高比例
    private float mWidthProportion = 0,mHeightProportion = 0;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttribute(attrs);
        // 把布局加载到这个View里面
        inflate(context, R.layout.ui_banner_layout,this);

        initView();
    }

    /**
     * 8.初始化自定义属性
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        // 获取点的位置
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        // 获取点的颜色（默认、选中）
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if(mIndicatorFocusDrawable == null){
            // 如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if(mIndicatorNormalDrawable == null){
            // 如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        // 获取点的大小和距离
        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize,dip2px(mDotSize));
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance,dip2px(mDotDistance));
        //获取底部颜色
        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor,mBottomColor);
        //获取宽高比例
        mWidthProportion = array.getFloat(R.styleable.BannerView_widthProportion,mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion,mHeightProportion);
        array.recycle();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBannerVp = findViewById(R.id.banner_view);
        mBannerDescTv = findViewById(R.id.banner_desc_tv);
        mDotContainerView = findViewById(R.id.dot_container);
        mBannerButtom = findViewById(R.id.banner_buttom);
        mBanner = findViewById(R.id.banner);

        mBannerButtom.setBackgroundColor(mBottomColor);
    }

    /**
     * 4.设置适配器
     */
    public void setAdapter(BannerAdapter adapter){
        mAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        mBannerVp.setCurrentItem(Integer.MAX_VALUE/2);
        initDotIndicator();

        // 6.Bug修复
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                // 监听当前选中的位置
                pageSelect(position);
            }
        });

        // 6.初始化的时候获取第一条的描述
        String firstDesc = mAdapter.getBannerDesc(0);
        mBannerDescTv.setText(firstDesc);

        mBanner.post(new Runnable() {
            @Override
            public void run() {
                if (mHeightProportion == 0 || mWidthProportion == 0) {
                    return;
                }
                //动态指定宽高
                int width = getMeasuredWidth();
                // 计算高度
                int height = (int) (width * mHeightProportion / mWidthProportion);
                // 指定宽高
                LayoutParams params = new LayoutParams(width,height);
                mBanner.setLayoutParams(params);
            }
        });
    }

    /**
     * 4.开始滚动
     */
    public void startRoll() {
        mBannerVp.startRoll();
    }

    /**
     * 5.初始化点的指示器
     */
    private void initDotIndicator() {
        // 获取广告的数量
        int count = mAdapter.getCount();

        // 点的位置
        if (mDotGravity == 0) {
            mDotContainerView.setGravity(Gravity.CENTER);
        }else if (mDotGravity == 1){
            mDotContainerView.setGravity(Gravity.RIGHT);
        }else {
            mDotContainerView.setGravity(Gravity.LEFT);
        }

        for (int i = 0;i<count;i++){
            // 不断的往点的指示器添加圆点
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            // 设置大小
            LinearLayout.LayoutParams params = new
                    LinearLayout.LayoutParams(dip2px(mDotSize),dip2px(mDotSize));
            // 设置左右间距
            params.leftMargin = params.rightMargin = dip2px(mDotDistance);
            indicatorView.setLayoutParams(params);

            if(i == 0) {
                // 选中位置
                indicatorView.setDrawable(mIndicatorFocusDrawable);
            }else{
                // 未选中的
                indicatorView.setDrawable(mIndicatorNormalDrawable);
            }
            mDotContainerView.addView(indicatorView);
        }
    }

    /**
     * 6.页面切换的回调
     * @param position
     */
    private void pageSelect(int position) {
        // 6.1 把之前亮着的点 设置为默认
        DotIndicatorView oldIndicatorView = (DotIndicatorView)
                mDotContainerView.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mIndicatorNormalDrawable);


        // 6.2 把当前位置的点 点亮  position 0 --> 2的31次方
        mCurrentPosition = position%mAdapter.getCount();
        DotIndicatorView currentIndicatorView = (DotIndicatorView)
                mDotContainerView.getChildAt(mCurrentPosition);
        currentIndicatorView.setDrawable(mIndicatorFocusDrawable);

        // 6.3设置广告描述
        String bannerDesc = mAdapter.getBannerDesc(mCurrentPosition);
        mBannerDescTv.setText(bannerDesc);
    }

    /**
     * 5.把dip转成px
     */
    private int dip2px(int dip) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip,getResources().getDisplayMetrics());
    }
}