package com.hongwei.furniture.modules.main.home.ui;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hongwei.basiclib.adapter.helper.adapterItemTouchHelper;
import com.hongwei.basiclib.adapter.refreshview.DefaultRefreshCreator;
import com.hongwei.basiclib.adapter.refreshview.LoadRefreshCreator;
import com.hongwei.basiclib.adapter.refreshview.LoadRefreshRecyclerView;
import com.hongwei.basiclib.base.BaseFragment;
import com.hongwei.furniture.R;
import com.hongwei.furniture.databinding.FragmentHomeBinding;
import com.hongwei.furniture.modules.main.home.viewmodel.HomeViewModel;
import com.hongwei.furniture.modules.test.adapter.TestAdapter;
import com.hongwei.furniture.modules.test.entity.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: HomeFragment
 * Author: XYB
 * Date: 2021/1/10
 * Description:
 */
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {
    private LoadRefreshRecyclerView mRefreshRecyclerView;
    private List<TestBean> list;
    private TestAdapter mAdapter;
    private DefaultRefreshCreator mDefaultRefreshCreator;
    private LoadRefreshCreator mLoadRefreshCreator;

    @Override
    protected HomeViewModel initViewModel() {
        return ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }

    @Override
    protected int onCreate() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mRefreshRecyclerView = dataBinding.recyclersa;

        mDefaultRefreshCreator = new DefaultRefreshCreator(){
            @Override
            public void onRefreshing() {
                super.onRefreshing();
                //下拉刷新中
                mRefreshRecyclerView.onStopLoad();
                View view = View.inflate(getContext(),R.layout.activity_test,null);
                TextView textView = view.findViewById(R.id.test);
                textView.setText(System.currentTimeMillis()+"");
                mRefreshRecyclerView.addFooterView(view);
            }
        };

        mLoadRefreshCreator = new LoadRefreshCreator(){
            @Override
            public void onLoading() {
                super.onLoading();
                //上拉加载中
                mRefreshRecyclerView.onStopRefresh();
            }
        };
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        for (int i=0;i<20;i++){
            TestBean testBean = new TestBean();
            testBean.setValue(i+"");
            list.add(testBean);
        }
        mAdapter = new TestAdapter(getContext(),list);
        mRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRefreshRecyclerView.setAdapter(mAdapter);
        mRefreshRecyclerView.addRefreshViewCreator(mDefaultRefreshCreator);
        mRefreshRecyclerView.addLoadViewCreator(mLoadRefreshCreator);
        adapterItemTouchHelper.getItemTouchHelper(mRefreshRecyclerView,mAdapter,list).attachToRecyclerView(mRefreshRecyclerView);
    }

    public static HomeFragment getInstance(){
        return new HomeFragment();
    }

}
