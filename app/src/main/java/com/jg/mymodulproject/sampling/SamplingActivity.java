package com.jg.mymodulproject.sampling;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jg.mymodulproject.bean.SamplingBean;
import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.basiclib.utils.RxToast;
import com.zcr.mymodulproject.R;

import java.util.ArrayList;
import java.util.List;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * Created by llz on 2018/3/30.
 */

public class SamplingActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRvContent;
    private ArrayList<SamplingBean> mSamplingBeans;
    private BaseQuickAdapter mBaseQuickAdapter;

    private static final int PAGE_SIZE = 10;
    private int mNextRequestPage = 1;
    private int mCurrentCounter = 0;
    private int TOTAL_COUNTER = 100;


    @Override
    protected int attachLayoutRes() {
        return R.layout.sampling_activity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initTitle(true,"工程概况");
        mRvContent = this.findViewById(R.id.rv_content);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mSamplingBeans = new ArrayList<SamplingBean>();
        mBaseQuickAdapter = new BaseQuickAdapter<SamplingBean, BaseViewHolder>(R.layout.sampling_rv_item, mSamplingBeans) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, SamplingBean bean) {
                SuperTextView superTextView = baseViewHolder.getView(R.id.super_tv);
                superTextView.getLeftTextView().setText(bean.getGcName());
            }
        };
        mRvContent.setAdapter(mBaseQuickAdapter);
        initRefreshLayout(); // 下拉刷新
        mBaseQuickAdapter.setEnableLoadMore(true);
        mBaseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                mRvContent.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            //数据全部加载完毕
                            mBaseQuickAdapter.loadMoreEnd();
                        } else {
                           // if (isErr) {
                                //成功获取更多数据
                            for(int i=0;i<10;i++){
                                SamplingBean samplingBean = new SamplingBean();
                                samplingBean.setId(i+"");
                                samplingBean.setGcName("刷新综合整治工程("+i+"期)");
                                mSamplingBeans.add(samplingBean);
                            }
                            mBaseQuickAdapter.notifyDataSetChanged();
                            mCurrentCounter = mBaseQuickAdapter.getData().size();
                            mBaseQuickAdapter.loadMoreComplete();
                         /*   } else {
                                //获取更多数据失败
                                isErr = true;
                                Toast.makeText(PullToRefreshUseActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
                                mQuickAdapter.loadMoreFail();

                            }*/
                        }
                    }

                }, 1000);
            }
        }, mRvContent);
        mBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SamplingActivity.this,SamplingDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBaseQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                mSamplingBeans.clear();
                mCurrentCounter = 0;
                for(int i=0;i<10;i++){
                    SamplingBean samplingBean = new SamplingBean();
                    samplingBean.setId(i+"");
                    samplingBean.setGcName("刷新综合整治工程("+i+"期)");
                    mSamplingBeans.add(samplingBean);
                }
                mBaseQuickAdapter.notifyDataSetChanged();
                mBaseQuickAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    @Override
    protected void initData() {
        for(int i=0;i<10;i++){
            SamplingBean samplingBean = new SamplingBean();
            samplingBean.setId(i+"");
            samplingBean.setGcName("综合整治工程("+i+"期)");
            mSamplingBeans.add(samplingBean);
        }
        mBaseQuickAdapter.notifyDataSetChanged();
    }


    @Override
    protected void updateViews(boolean isRefresh) {

    }
}
