package com.hongwei.furniture.modules.test.adapter;

import android.content.Context;

import com.hongwei.basiclib.adapter.baseadapter.CommonRecyclerAdapter;
import com.hongwei.basiclib.adapter.baseadapter.ViewHolder;
import com.hongwei.furniture.R;
import com.hongwei.furniture.modules.test.entity.TestBean;

import java.util.List;

/**
 * @author xyb
 * @description:
 * @date : 2021-01-05
 */
public class TestAdapter extends CommonRecyclerAdapter<TestBean> {
    public TestAdapter(Context context, List<TestBean> datas) {
        super(context, datas, R.layout.test_item);
    }

    @Override
    public void convert(ViewHolder holder, TestBean item) {
        holder.setText(R.id.textciew,item.getValue());
    }
}
