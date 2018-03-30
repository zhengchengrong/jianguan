package com.jg.mymodulproject.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jg.mymodulproject.home.HomeActivity;
import com.jg.mymodulproject.widget.CodeView;
import com.luojilab.component.basiclib.Const;
import com.luojilab.component.basiclib.api.BaseObserver;
import com.luojilab.component.basiclib.api.RetrofitFactory;
import com.luojilab.component.basiclib.api.RxSchedulers;
import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.basiclib.bean.BaseEntity;
import com.luojilab.component.basiclib.bean.login.UserBean;
import com.luojilab.component.basiclib.utils.RxSPTool;
import com.luojilab.component.basiclib.utils.RxToast;
import com.luojilab.component.basiclib.utils.RxTool;
import com.luojilab.component.basiclib.widget.SuperEditText;
import com.luojilab.router.facade.annotation.RouteNode;
import com.zcr.mymodulproject.R;

import io.reactivex.Observable;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private CodeView mCodeView;
    private ImageView mIvCode;
    private TextView tvLogin;
    private SuperEditText edUserName;
    private SuperEditText edPassword;
    private ProgressDialog dialog;
    @Override
    protected int attachLayoutRes() {
        return  R.layout.login_activity;
    }
    @Override
    protected void initInjector() {
    }
    @Override
    protected void initViews() {
        initTitle(false,"登录");
        mIvCode = this.findViewById( R.id.iv_code);
        tvLogin = this.findViewById( R.id.tv_login);
        edUserName = this.findViewById( R.id.ed_username);
        edPassword = this.findViewById( R.id.ed_password);

        mIvCode.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        mCodeView = CodeView.getInstance();
        Bitmap bitmap = mCodeView.createBitmap();
        mIvCode.setImageBitmap(bitmap);
    }
    @Override
    protected void initData() {
    }
    @Override
    protected void updateViews(boolean isRefresh) {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.iv_code:
                mIvCode.setImageBitmap(mCodeView.createBitmap());
                break;
            case  R.id.tv_login: // 登陆
                String username = edUserName.getText().toString();
                String password = edPassword.getText().toString();
                if(TextUtils.isEmpty(username)){
                    RxToast.showToast(Const.INPUT_ACCOUNT);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    RxToast.showToast(Const.INPUT_PASSWORD);
                    return;
                }
                dialog = new ProgressDialog(this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                dialog.setTitle("正在登录...");
                dialog.show();
                toLogin(username,password);
    /*            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);*/
                break;
        }
    }

    private void toLogin(final String username, final String password) {
        UserBean req = new UserBean();
        req.setUsername(username);
        req.setPassword(password);
        Observable<BaseEntity<UserBean>> observable = RetrofitFactory.getInstance().toLogin(username,password);
        observable.compose(RxSchedulers.<BaseEntity<UserBean>>compose(
        )).subscribe(new BaseObserver<UserBean>() {
            @Override
            protected void onHandleSuccess(BaseEntity<UserBean> t) {
                dialog.dismiss();
                RxToast.showToast(t.getDescription());
                //缓存账号信息
                RxSPTool.putString(LoginActivity.this,Const.USERNAME,username);
                RxSPTool.putString(LoginActivity.this,Const.PASSWORD,password);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            @Override
            protected void onHandleEmpty(BaseEntity<UserBean> t) {
                dialog.dismiss();
                RxToast.showToast(t.getDescription());
            }
        });
    }
}
