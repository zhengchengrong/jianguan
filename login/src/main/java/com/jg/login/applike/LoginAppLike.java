package com.jg.login.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * Created by llz on 2018/3/27.
 */

public class LoginAppLike implements IApplicationLike {

    UIRouter uiRouter = UIRouter.getInstance();


    @Override
    public void onCreate() {
        uiRouter.registerUI("login");
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("login");
    }
}
