package com.jg.mymodulproject.home;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jg.mymodulproject.adapter.MenusGridviewAdapter;
import com.jg.mymodulproject.bean.GetMenusListRsp;
import com.jg.mymodulproject.utils.GlideImageLoader;
import com.jg.mymodulproject.widget.NoScrollGridView;
import com.luojilab.component.basiclib.Const;
import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.basiclib.bean.login.UserBean;
import com.luojilab.component.basiclib.utils.RxSPTool;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zcr.mymodulproject.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by llz on 2018/3/29.
 */

public class HomeActivity extends BaseActivity {
    Banner mBanner;
    ArrayList<String> images;
    NoScrollGridView mygridView;
    private MenusGridviewAdapter adapter;
    List<GetMenusListRsp> listRsps;
    @Override
    protected int attachLayoutRes() {
        return R.layout.home_activity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initTitle(false,"首页");
        images = new ArrayList<String>();
        mBanner = this.findViewById(R.id.banner);
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522318062973&di=b178bd7a4de8aabe88447c7003a14a37&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F30%2F37%2F258PICN58PICRBW.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522318062973&di=0a3868ba7642b8691792da42c598b7e1&imgtype=0&src=http%3A%2F%2Fpic12.photophoto.cn%2F20090727%2F0040039482964791_b.jpg");
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mygridView = (NoScrollGridView) findViewById(R.id.mygridView);
        UserBean userBean = new UserBean();
        userBean.setUsername(RxSPTool.getString(this, Const.USERNAME));
        userBean.setPassword(RxSPTool.getString(this, Const.PASSWORD));
        adapter = new MenusGridviewAdapter(this,userBean);
        mygridView.setAdapter(adapter);
        listRsps  = new ArrayList<GetMenusListRsp>();
        GetMenusListRsp getMenusListRsp = new GetMenusListRsp();
        getMenusListRsp.setMenuNameShow("取样端");
        getMenusListRsp.setMenulocUrl(R.drawable.quyang);
        GetMenusListRsp getMenusListRsp2 = new GetMenusListRsp();
        getMenusListRsp2.setMenuNameShow("见证端");
        getMenusListRsp2.setMenulocUrl(R.drawable.jianzheng);
        GetMenusListRsp getMenusListRsp3 = new GetMenusListRsp();
        getMenusListRsp3.setMenuNameShow("见证信息查询");
        getMenusListRsp3.setMenulocUrl(R.drawable.xinxi);
        GetMenusListRsp getMenusListRsp4 = new GetMenusListRsp();
        getMenusListRsp4.setMenuNameShow("真伪查询");
        getMenusListRsp4.setMenulocUrl(R.drawable.zhenwei);
        listRsps.add(getMenusListRsp);
        listRsps.add(getMenusListRsp2);
        listRsps.add(getMenusListRsp3);
        listRsps.add(getMenusListRsp4);
        adapter.DateNotify(listRsps);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }
}
