package com.jg.mymodulproject.sampling;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jg.mymodulproject.R;
import com.luojilab.component.basiclib.Const;
import com.luojilab.component.basiclib.api.BaseObserver;
import com.luojilab.component.basiclib.api.RetrofitFactory;
import com.luojilab.component.basiclib.api.RxSchedulers;
import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.basiclib.bean.BaseEntity;
import com.luojilab.component.basiclib.bean.SamplingDetailBean;
import com.luojilab.component.basiclib.bean.SamplingResBean;
import com.luojilab.component.basiclib.utils.RxSPTool;
import com.luojilab.component.basiclib.utils.RxToast;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.component.componentlib.service.JsonService;
import com.luojilab.componentservice.share.bean.Author;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by llz on 2018/3/30.
 */

public class SamplingDetailActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRvContent;
    private ArrayList<SamplingDetailBean> mSamplingBeans;
    private BaseQuickAdapter mBaseQuickAdapter;

    private static final int PAGE_SIZE = 10;
    private int mNextRequestPage = 1;
    private int mCurrentCounter = 0; //当前条数
    private int TOTAL_COUNTER = 0; // 所有条数

    private String zljdbm;


    @Override
    protected int attachLayoutRes() {
        return R.layout.sampling_detail_activity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initTitle(true,"工程概况");
        zljdbm = getIntent().getStringExtra(Const.ZLJDBM);
        mRvContent = this.findViewById(R.id.rv_content);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mSamplingBeans = new ArrayList<SamplingDetailBean>();
        mBaseQuickAdapter = new BaseQuickAdapter<SamplingDetailBean, BaseViewHolder>(R.layout.sampling_rv_detail_item, mSamplingBeans) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, SamplingDetailBean bean) {
                TextView item_sampling_sort = baseViewHolder.getView(R.id.item_sampling_sort);
                item_sampling_sort.setText(baseViewHolder.getAdapterPosition() +1+"");
                TextView item_sampling_tv_name = baseViewHolder.getView(R.id.item_sampling_tv_name);
                item_sampling_tv_name.setText(bean.getGcname());
                TextView item_sampling_tv_number = baseViewHolder.getView(R.id.item_sampling_tv_number);
                item_sampling_tv_number.setText(bean.getZljdbm());
                TextView item_sampling_tv_number_02 = baseViewHolder.getView(R.id.item_sampling_tv_number_02);
                item_sampling_tv_number_02.setText(bean.getPhnum());
                TextView item_sampling_tv_part = baseViewHolder.getView(R.id.item_sampling_tv_part);
                item_sampling_tv_part.setText(bean.getGjname());
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

                    Bundle bundle = new Bundle();
                    bundle.putString(Const.ID,mSamplingBeans.get(position).getId());
                    UIRouter.getInstance().openUri(SamplingDetailActivity.this, "DDComp://chip/chipPage", bundle); // share host
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
        Observable<BaseEntity<SamplingDetailBean>> observable = RetrofitFactory.getInstance().getSampleInfo(username,password,zljdbm);
        observable.compose(RxSchedulers.<BaseEntity<SamplingDetailBean>>compose(
        )).subscribe(new BaseObserver<SamplingDetailBean>() {
            @Override
            protected void onHandleSuccess(BaseEntity<SamplingDetailBean> bean) {
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
            protected void onHandleEmpty(BaseEntity<SamplingDetailBean> t) {
                showEmpty();
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
}
