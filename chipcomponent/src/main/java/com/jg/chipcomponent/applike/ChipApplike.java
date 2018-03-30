package com.jg.chipcomponent.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * Created by llz on 2018/3/30.
 */

public class ChipApplike implements IApplicationLike {

    UIRouter uiRouter = UIRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI("chip");

    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("chip");
    }
}
