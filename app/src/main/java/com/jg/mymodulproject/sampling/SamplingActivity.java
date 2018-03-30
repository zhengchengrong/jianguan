package com.jg.mymodulproject.sampling;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jg.mymodulproject.bean.SamplingBean;
import com.luojilab.component.basiclib.Const;
import com.luojilab.component.basiclib.api.BaseObserver;
import com.luojilab.component.basiclib.api.RetrofitFactory;
import com.luojilab.component.basiclib.api.RxSchedulers;
import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.basiclib.bean.BaseEntity;
import com.luojilab.component.basiclib.bean.SamplingResBean;
import com.luojilab.component.basiclib.utils.RxSPTool;
import com.luojilab.component.basiclib.utils.RxToast;
import com.zcr.mymodulproject.R;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by llz on 2018/3/30.
 */

public class SamplingActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRvContent;
    private ArrayList<SamplingResBean> mSamplingBeans;
    private BaseQuickAdapter mBaseQuickAdapter;

    private static final int PAGE_SIZE = 10;
    private int mNextRequestPage = 1;
    private int mCurrentCounter = 0; //当前条数
    private int TOTAL_COUNTER = 0; // 所有条数


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
        mSwipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mSamplingBeans = new ArrayList<SamplingResBean>();
        mBaseQuickAdapter = new BaseQuickAdapter<SamplingResBean, BaseViewHolder>(R.layout.sampling_rv_item, mSamplingBeans) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, SamplingResBean bean) {
                SuperTextView superTextView = baseViewHolder.getView(R.id.super_tv);
                superTextView.getLeftTextView().setText(bean.getGcName());
            }
        };
        mRvContent.setAdapter(mBaseQuickAdapter);
        initRefreshLayout(); // 下拉刷新
        mBaseQuickAdapter.setEnableLoadMore(true);
        mBaseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                if(TOTAL_COUNTER>PAGE_SIZE) { //小于一页不访问
                    getData(false);
                }
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
                getData(true);
            }
        });
    }
    @Override
    protected void initData() {
        getData(true);
    }

    public void getData(final boolean isRefresh){
        String username = RxSPTool.getString(this, Const.USERNAME);
        String password = RxSPTool.getString(this, Const.PASSWORD);
        Observable<BaseEntity<SamplingResBean>> observable = RetrofitFactory.getInstance().getProject(username,password);
        observable.compose(RxSchedulers.<BaseEntity<SamplingResBean>>compose(
        )).subscribe(new BaseObserver<SamplingResBean>() {
            @Override
            protected void onHandleSuccess(BaseEntity<SamplingResBean> bean) {
                TOTAL_COUNTER =bean.getTotal();
                if(isRefresh == true){
                    mBaseQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                    mSamplingBeans.clear();
                    mCurrentCounter = 0;
                    mSamplingBeans.addAll(bean.getList());
                    mBaseQuickAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }else{
                    if (mCurrentCounter >= TOTAL_COUNTER) {
                        //数据全部加载完毕
                        mBaseQuickAdapter.loadMoreEnd();
                    } else {
                        mSamplingBeans.addAll(bean.getList());
                        mBaseQuickAdapter.notifyDataSetChanged();
                        mCurrentCounter = mBaseQuickAdapter.getData().size();
                        mBaseQuickAdapter.loadMoreComplete();

                    }
                }
            }
            @Override
            protected void onHandleEmpty(BaseEntity<SamplingResBean> t) {
                RxToast.showToast(R.string.network_err);
                mBaseQuickAdapter.loadMoreFail();
            }
        });
    }


    @Override
    protected void updateViews(boolean isRefresh) {

    }
}
