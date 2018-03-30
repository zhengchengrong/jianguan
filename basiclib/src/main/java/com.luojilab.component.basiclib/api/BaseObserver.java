package com.luojilab.component.basiclib.api;

import com.luojilab.component.basiclib.base.IBaseView;
import com.luojilab.component.basiclib.bean.BaseEntity;
import com.luojilab.component.basiclib.utils.RxLogTool;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhengchengrong on 2017/9/3.
 */
// 基本观察者，所有的观察都实现该类，然后重写onHandleSuccess方法处理消息即可。
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>>{
    private static final String TAG = "BaseObserver";

    private IBaseView mBaseView;
    protected BaseObserver(IBaseView baseView) {
        this.mBaseView = baseView;
    }

    protected BaseObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntity<T> value) {
        if (value.isSuccess()) {
            onHandleSuccess(value);
        } else if(value.isError()){ // 如果数据为空，显示空视图
            onHandleEmpty(value);
        }else{ // 其他，显示网络错误
            mBaseView.showNetError();
        }

    }

    @Override
    public void onError(Throwable e) {
        RxLogTool.e(e.getStackTrace()+":"+e.getMessage());
    }

    @Override
    public void onComplete() {
        RxLogTool.d(TAG, "onComplete");
    }

    protected abstract void onHandleSuccess(BaseEntity<T> t);

    protected abstract void onHandleEmpty(BaseEntity<T> t);

}
