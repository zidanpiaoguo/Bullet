/*
 * The author is bullet
 */

package com.lzy.commonbase.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by bullet on 2017/12/16.
 */

public class ActivityCollector {
    private static List<Activity> activities;

    /**
     * 静态内部类实现单例
     */
    private static class CollectorHold{
        private static final ActivityCollector INSTANCE_APP = new ActivityCollector();
    }

    public static ActivityCollector getAcitivityCollector(){

        return CollectorHold.INSTANCE_APP;
    }


    /**
     * 添加activity
     * @param activity 句柄
     */

    public void addActivty(Activity activity){
        if (activities == null) {
            activities = new ArrayList<>();
        }
        activities.add(activity);
    }

    public void removeActivty(Activity activity){
        activities.remove(activity);
    }

    public void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
