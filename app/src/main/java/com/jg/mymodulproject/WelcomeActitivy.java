package com.jg.mymodulproject;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jg.mymodulproject.login.LoginActivity;
import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.router.facade.annotation.RouteNode;

/**
 * Created by llz on 2018/3/28.
 */
@RouteNode(path = "/wel", desc = "欢迎页")
public class WelcomeActitivy extends BaseActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.welcome_activity;
    }

    @Override
    protected void initInjector() {

    }
    @Override
    protected void initViews() {
        TextView textbottom = (TextView) findViewById(R.id.textbottom);
        textbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActitivy.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }
}
