package com.lzy.commonbase.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.commonbase.constant.ConstantPath;


import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Util.UTF_8;

/**
 *
 * Created by bullet on 2017/11/10.
 */

public class RetrofitFactory {

    private static final String TAG = "RetrofitFactory";

    private final static long CONNECT_TIMEOUT = 6;
    //请求超时6秒

    public final static int READ_TIMEOUT = 10;
    //读取超时10秒

    public final static int WRITE_TIMEOUT = 10;
    //写入数差

    //Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request request1 = chain.request();
                    //构建新body。 MediaType根据实际情况更换
                    RequestBody newBody = null;
                    if(request1!=null){
                        JSONObject json =new JSONObject(printParams(request1.body()));
                        newBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
                    }
                    Request request = chain.request().newBuilder()
                            .post(newBody)
                            .build();
                    return chain.proceed(request);
                }
            })
            //添加头文件，目前接口不用加，就
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

//                    String s = UserInfoPreference.getInstance().getMyGender();
//                    Log.d(TAG, "intercept:  "+s);
//
                    Request request = chain.request().newBuilder()
                            .addHeader("version", "1")
                            .addHeader("device", "android")
//                            .addHeader("gender", UserInfoPreference.getInstance().getMyGender())
//                            .addHeader("user-id", UserInfoPreference.getInstance().getUserid())
//                            .addHeader("store", UserInfoPreference.getInstance().getStore())
                            .build();

//                    Response response = chain.proceed(chain.request());
//                    Log.d(TAG, "intercept: "+response.toString());
//                    Log.d(TAG, "intercept: "+response.body().string());
                    return chain.proceed(request);
                }
            })
            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {

                }
            }).setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            //连接超时时间
            .retryOnConnectionFailure(true)
            //失败重拨。可以取消掉
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            //读取超时
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            //写入超时
            .build();


    private static Map<String, Object> printParams(RequestBody body) {

        Map<String, Object> map = new HashMap<>();
        Buffer buffer = new Buffer();
            try {
                body.writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF_8);
                }
                String params = buffer.readString(charset);

                map = new RequestData(params).getParam();
                Log.d(TAG, "请求参数: "+params);
               
 
            } catch (IOException e) {
                e.printStackTrace();
            }
        return  map;

    }



    private static RetrofitService retrofitService = new Retrofit.Builder()
            .baseUrl(ConstantPath.BASE_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RetrofitService.class);

    public static RetrofitService getInstance() {
        return retrofitService;
    }


    public static RetrofitService getInstance(String url) {

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(RetrofitService.class);
    }


    /**
     * 解决gson 对于空字符，抛出异常的情况
     *
     * @return
     */
    private static Gson buildGson() {
        return new GsonBuilder().serializeNulls().create();
    }


    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) {
                        return null;
                    }
                    return delegate.convert(body);
                }
            };
        }
    }


}
