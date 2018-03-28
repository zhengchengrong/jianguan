package com.luojilab.component.basiclib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.github.ybq.android.spinkit.SpinKitView;
import com.luojilab.component.basiclib.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by long on 2016/8/23.
 * 加载、空视图
 */
public class EmptyLayout extends FrameLayout implements View.OnClickListener {

    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_NET = 2;
    public static final int STATUS_NO_DATA = 3;
    private Context mContext;
    private OnRetryListener mOnRetryListener;
    private int mEmptyStatus = STATUS_HIDE;
    private int mBgColor;

    TextView mTvErrorMessage;
    
    TextView mTvEmptyMessage;
    
    View mRlEmptyContainer;
    com.github.ybq.android.spinkit.SpinKitView mEmptyLoading;
    FrameLayout mEmptyLayout;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
      View view =   View.inflate(mContext, R.layout.layout_empty_loading, this);
        mTvErrorMessage = view.findViewById(R.id.tv_net_error);
        mTvErrorMessage.setOnClickListener(this);
        mTvEmptyMessage = view.findViewById(R.id.tv_net_empty);
        mRlEmptyContainer = view.findViewById(R.id.rl_empty_container);
        mEmptyLoading = view.findViewById(R.id.empty_loading);
        mEmptyLoading = (SpinKitView)view.findViewById(R.id.empty_layout);
        mEmptyLayout.setBackgroundColor(mBgColor);
        _switchEmptyView();
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        _switchEmptyView();
    }

    /**
     * 获取状态
     * @return  状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    public void setEmptyMessage(String msg) {
        mTvErrorMessage.setText(msg);
    }

    public void hideErrorIcon() {
        mTvErrorMessage.setCompoundDrawables(null, null, null, null);
    }

    public void setLoadingIcon(com.github.ybq.android.spinkit.sprite.Sprite d) {
        mEmptyLoading.setIndeterminateDrawable(d);
    }

    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                mEmptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
                setVisibility(VISIBLE);
                mEmptyLoading.setVisibility(GONE);
                mTvEmptyMessage.setVisibility(VISIBLE);
                mTvErrorMessage.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                mEmptyLoading.setVisibility(GONE);
                mTvErrorMessage.setVisibility(VISIBLE);
                mTvEmptyMessage.setVisibility(GONE
                );
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }
    }

    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.mOnRetryListener = retryListener;
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tv_net_error){
            if (mOnRetryListener != null) {
                mOnRetryListener.onRetry();
            }
        }
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA})
    public @interface EmptyStatus{}
}