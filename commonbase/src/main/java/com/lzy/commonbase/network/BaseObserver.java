package com.lzy.commonbase.network;


import android.arch.lifecycle.Observer;
import android.net.ParseException;
import android.util.Log;

import com.bumptech.glide.load.HttpException;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.lzy.commonbase.utils.MyToast;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.disposables.Disposable;


/**
 *
 * Created by bullet on 2017/11/10.
 */

public abstract class BaseObserver<T> implements Observer<BaseData<T>> {
    private static final String TAG = "BaseObserver";
    Type type = null;
    Class b;

    public BaseObserver(Type type) {
        this.type = type;
    }


    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
    }


    @Override
    public void onNext(BaseData<T> tBaseResult) {
        if (tBaseResult.isSuccess()) {
            String s = tBaseResult.getData();
            if (s != null) {
                onHandleSuccess((T) new Gson().fromJson(s, type));
            } else {

            }
        } else {
            onHandleError(tBaseResult.getStatus(), tBaseResult.getMessage());
            Error();
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("test", "onError: " + e.getMessage());
        Error();

        if (e instanceof HttpException) {
            //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof UnknownHostException
                || e instanceof ConnectException || e instanceof SocketException) {
            //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException || e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                ) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
            Log.d(TAG, "onError: " + e.getMessage());

        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(int reason) {
        switch (reason) {
            case ExceptionReason.CONNECT_ERROR:
                MyToast.makeText("网络连接失败,请检查网络");
                break;
            case ExceptionReason.CONNECT_TIMEOUT:
                MyToast.makeText("连接超时,请稍后再试");
                break;
            case ExceptionReason.BAD_NETWORK:
                MyToast.makeText("服务器异常");
                break;
            case ExceptionReason.PARSE_ERROR:
                MyToast.makeText("解析数据失败");
                break;
            case ExceptionReason.UNKNOWN_ERROR:
            default:
                MyToast.makeText("服务器异常");
                break;
        }
    }


    /**
     * 请求网络失败原因
     */
    public static class ExceptionReason {
        /**
         * 解析数据失败
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络问题
         */
        public static final int BAD_NETWORK = 1002;
        /**
         * 连接错误
         */
        public static final int CONNECT_ERROR = 1003;
        /**
         * 连接超时
         */
        public static final int CONNECT_TIMEOUT = 1004;
        /**
         * 未知错误
         */
        public static final int UNKNOWN_ERROR = 1005;
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }


    /**
     * 返回成功
     *
     * @param data
     */
    protected abstract void onHandleSuccess(T data);

    /**
     * 返回状态码不是200
     */
    protected void onHandleError(int code, String msg) {
        MyToast.makeText(msg);
    }


    /**
     * 所有的错误，都会走这个方法
     */
    protected void Error() {
        Log.d(TAG, "Error: ");
    }


}
