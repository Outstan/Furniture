package com.hongwei.furniture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.hongwei.basiclib.adapter.helper.adapterItemTouchHelper;
import com.hongwei.basiclib.adapter.refreshview.DefaultRefreshCreator;
import com.hongwei.basiclib.adapter.refreshview.LoadRefreshCreator;
import com.hongwei.basiclib.adapter.refreshview.LoadRefreshRecyclerView;
import com.hongwei.furniture.adapter.TestAdapter;
import com.hongwei.furniture.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoadRefreshRecyclerView mRefreshRecyclerView;
    private List<TestBean> list;
    private TestAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initdata();
        mRefreshRecyclerView = findViewById(R.id.recyclersa);

        mAdapter = new TestAdapter(this,list);
        mRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRefreshRecyclerView.setAdapter(mAdapter);
        mRefreshRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator(){
            @Override
            public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {
                super.onPull(currentDragHeight, refreshViewHeight, currentRefreshStatus);
                mRefreshRecyclerView.onStopLoad();
            }
        });
        mRefreshRecyclerView.addLoadViewCreator(new LoadRefreshCreator());
        adapterItemTouchHelper.getItemTouchHelper(mRefreshRecyclerView,mAdapter,list).attachToRecyclerView(mRefreshRecyclerView);
    }

    private void initdata() {
        list = new ArrayList<>();
        for (int i=0;i<20;i++){
            TestBean testBean = new TestBean();
            testBean.setValue(i+"");
            list.add(testBean);
        }
    }
}