package com.jg.chipcomponent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.basiclib.utils.RxToast;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.componentservice.share.bean.Author;
import com.luojilab.router.facade.annotation.Autowired;
import com.luojilab.router.facade.annotation.RouteNode;

@RouteNode(path = "/chipPage", desc = "芯片扫描")
public class ChipActivity extends BaseActivity {
    @Autowired
    String bookName;

    @Autowired
    Author author;


    @Override
    protected int attachLayoutRes() {
        return R.layout.chip_activity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        if(bookName!=null){
            RxToast.showToast(bookName+":"+author);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }


}
