package com.lzy.commonsdk.utils.imageload;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

/**
 *  代理模式，此处只是调用GlideImageUtils的方法
 *  可以随时整个替换掉里面的内容
 *  @author bullet
 * @date 2019\1\8 0008.
 */

public class ImageUtils {


    /**
     * 判断context 是否存活
     * @param context  类
     * @return
     */
    private static boolean isValid(Context context){
        if(context != null && !((Activity) context).isDestroyed()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 加载基础的图片 （最常用，这里有占位图，需要替换）
     * @param context  类
     * @param url   url
     * @param imageView  view
     */
    public static void loadBasicImg(Context context, String url , ImageView imageView){

        if(isValid(context)){
            GlideImageUtils.loadBasicImg(context,url,imageView);
        }
    }


    /**
     * 加载圆角图片
     *
     * @param context  类
     * @param url   url
     * @param imageView  view
     */
    public static void loadRoundedImage(Context context, String url, ImageView imageView, int corners) {
        if(isValid(context)){
            GlideImageUtils.loadRoundedImg(context,url,imageView,corners);
        }
    }


    /**
     * 加载圆形图片
     *
     * @param context  类
     * @param url   url
     * @param imageView  view
     */
    public static void loadCircleImg(Context context, String url, ImageView imageView) {
        if(isValid(context)){
            GlideImageUtils.loadCircleImg(context,url,imageView);
        }
    }


    /**
     * 加载不需要缓存的图片
     *
     * @param context  类
     * @param url   url
     * @param imageView  view
     */
    public static void loadNoMemoryImg(Context context, String url, ImageView imageView) {
        if(isValid(context)){
            GlideImageUtils.loadNoMemoryImg(context,url,imageView);
        }
    }

    /**
     * 圆形加载带边框
     *
     * @param mContext 类
     * @param url url
     * @param imageView view
     * @param borderWidth  边框的宽度
     * @param colorId 边框的颜色
     */
    public static void loadFrameCircleImg(Context mContext, String url, ImageView imageView, int borderWidth, int colorId) {
        if(isValid(mContext)){
            GlideImageUtils.loadFrameCircleImg(mContext,url,imageView,borderWidth,colorId);
        }
    }


    /**
     * 通过url 获取bitmap
     * @param mContext 类
     * @param url  url
     * @param bitmapLoadCallBack  成功的回调
     */
    public static void getImgBitmap(Context mContext, String url,GlideImageUtils.BitmapLoadCallBack bitmapLoadCallBack){
        if(isValid(mContext)){
            GlideImageUtils.getImgBitmap(mContext,url,bitmapLoadCallBack);
        }
    }







}
