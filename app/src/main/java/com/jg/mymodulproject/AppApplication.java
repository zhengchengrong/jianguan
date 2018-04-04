package com.jg.mymodulproject;

import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.load.engine.Initializable;
import com.luojilab.component.basiclib.utils.RxTool;
import com.luojilab.component.basicres.BaseApplication;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * Created by llz on 2018/3/26.
 */

public class AppApplication extends BaseApplication {
    private static AppApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        UIRouter.getInstance().registerUI("app");
        RxTool.init(this);
        SDKInitializer.initialize(this);
        this.app = this;
    }

    public static AppApplication getInstance() {
        return app;
    }
}
