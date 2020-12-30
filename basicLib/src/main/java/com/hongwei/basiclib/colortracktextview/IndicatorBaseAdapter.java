package com.hongwei.basiclib.colortracktextview;

import android.view.View;
import android.view.ViewGroup;

/**
 * FileName: IndicatorBaseAdapter
 * Author: XYB
 * Date: 2020/12/25
 * Description: 指示器的适配器
 */
public abstract class IndicatorBaseAdapter<Q extends View> {
    // 获取总的条数
    public abstract int getCount();

    // 根据当前的位置获取View
    public abstract Q getView(int position, ViewGroup parent);

    // 高亮当前位置
    public void highLightIndicator(Q indicatorView){

    }

    // 重置当前位置
    public void restoreIndicator(Q indicatorView){

    }

    //添加底部指示器
    public View getBottomTrackView(){
        return null;
    }
}
