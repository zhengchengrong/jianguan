package com.jg.chipcomponent.runalone.application;

import com.baidu.mapapi.SDKInitializer;
import com.luojilab.component.basiclib.utils.RxTool;
import com.luojilab.component.basicres.BaseApplication;

/**
 * Created by llz on 2018/3/30.
 */

public class ChipApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        SDKInitializer.initialize(this);
    }
}
