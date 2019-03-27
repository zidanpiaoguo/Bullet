package com.lzy.commonbase.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lzy.commonbase.app.GlobalApplication;


/**
 *
 * Created by bullet on 2018\10\22 0022.
 */

public class NetUtils {


    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)
               GlobalApplication.getInstance().getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable();

    }
}
