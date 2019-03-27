package com.lzy.bulletproject;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lzy.commonbase.app.GlobalApplication;

/**
 * @author bullet
 * @date 2019\3\21 0021.
 */

public class AppApplication extends GlobalApplication {


    @Override
    public void init() {
        initRouter();
    }

    /**
     * 初始化路由
     */
//    private void initARouter(){
//        //初始化 ARouter
//        if(BuildConfig.DEBUG){
//            ARouter.openLog();
//            ARouter.openDebug();
//        }
//        ARouter.init(AppApplication.this);
//    }
    private void initRouter( ){
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (true) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！
            //线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }

}
