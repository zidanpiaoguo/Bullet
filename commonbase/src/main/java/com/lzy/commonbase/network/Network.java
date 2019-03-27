package com.lzy.commonbase.network;



import com.lzy.commonbase.utils.MyToast;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by bullet on 2017/11/10.
 */

public class Network {

    /**
     * 线程调度
     */
    public static <T> ObservableTransformer<T, T> compose() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                //     可添加网络连接判断等
                                if (!NetUtils.isNetworkConnected()) {
                                    MyToast.makeText("网络连接异常，请检查网络");
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * Flowable 防背压处理
     *
     * @param <T>
     * @return
     */

    public static <T> FlowableTransformer<T, T> Fcompose() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(new IoScheduler())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                //     可添加网络连接判断等
                                if (!NetUtils.isNetworkConnected()) {
                                    MyToast.makeText("网络连接异常，请检查网络");
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());

            }
        };

    }


}
