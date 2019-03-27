package com.lzy.commonsdk.utils.imageload;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lzy.commonsdk.R;

import java.io.File;
import java.security.MessageDigest;


/**
 *
 * Glide图片加载工具类
 * 这里有两张占位图，要替换掉
 * 这里情况当然不全，有功能表，自行扩展
 * 这只是简单使用，复杂的可以使用glide-transformations 地址：https://github.com/wasabeef/glide-transformations
 *
 *  @author bullet
 *  @date 2017\8\30 0008.
 */

public class GlideImageUtils {


    /**
     * 功能加载实例
     * @param context  类
     * @param url   地址
     * @param imageView   view
     */
    private void example(Context context, String url, ImageView imageView){

        RequestOptions options = new RequestOptions()
                //圆图
                .circleCrop()
                //圆角
                .transform(new RoundedCorners(10))
                //加载图片的方式，默认是fitCenter()  加载的类型适配imageView
                .centerCrop()
                //加载尺寸
                .override(500,500)
                //占位图
                .placeholder(R.drawable.img_placeholder)
                //错误图
                .error(R.drawable.img_error)
                //加载的优先级，  Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW。默认为Priority.NORMAL。
                .priority(Priority.HIGH)
                //true表示禁用内存缓存（默认开启，一般不禁用,有头像地址）
                .skipMemoryCache(true)
                // DiskCacheStrategy.NONE：禁用硬盘缓存，
                // 缓存策略 DiskCacheStrategy.SOURCE：缓存原始数据，// DiskCacheStrategy.RESULT：缓存变换(如缩放、裁剪等)后的资源数据，
                // // DiskCacheStrategy.ALL：缓存SOURC和RESULT。
                .diskCacheStrategy(DiskCacheStrategy.NONE);

//        //加载bitmap需要转换一下
//        Drawable drawable = new BitmapDrawable(bitmap);


        Glide.with(context)
                .load(url)
                //先加载缩略图，再加载原图
                .thumbnail(0.1f)
                //渐变动画
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .apply(options)
                .into(imageView);

    }





    /**
     * 根据URl 加载图片  (可加载Gif)
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadBasicImg(Context context, String url, ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);

    }


    /**
     * 根据本地资源图片，自定义占位图（基本不用，用setImageResource（）方法直接赋值就可以，除非是gif）
     *
     * @param context
     * @param resourceId 图片id
     * @param imageView view
     */
    public static void loadBasicImg(Context context, int resourceId, ImageView imageView) {


        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error);

        Glide.with(context)
                .load(resourceId)
                .apply(options)
                .into(imageView);

    }

    /**
     * 根据图片路径 加载图片
     *
     * @param context
     * @param imgFile 图片id
     * @param imageView  view
     */
    public static void loadBasicImg(Context context, File imgFile, ImageView imageView) {


        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error);

        Glide.with(context)
                .load(imgFile)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(imageView);

    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param imgPath
     * @param imageView
     */
    public static void loadCircleImg(Context context, String imgPath, ImageView imageView) {


        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error);

        Glide.with(context)
                .load(imgPath)
                .apply(options)
                .into(imageView);

    }

    /**
     * 加载资源id ，圆形图片
     *
     * @param context
     * @param resourceId 资源id
     * @param imageView
     */
    public static void loadCircleImg(Context context, int resourceId, ImageView imageView) {


        RequestOptions options = new RequestOptions()
                .transform(new CircleCrop())
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error);

        Glide.with(context)
                .load(resourceId)
                .apply(options)
                .into(imageView);

    }




    /**
     * 加载圆角图片
     *
     * @param context
     * @param imgPath
     * @param imageView
     * @param corners
     */
    public static void loadRoundedImg(Context context, String imgPath, ImageView imageView, int corners) {

        RequestOptions options = new RequestOptions()
                .transform(new RoundedCorners(corners))
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error);

        Glide.with(context)
                .load(imgPath)
                .apply(options)
                .into(imageView);
    }






    /**
     * 圆形加载带边框
     *
     * @param mContext
     * @param url url
     * @param imageView view
     * @param borderWidth  边框的宽度
     * @param colorId 边框的颜色
     */
    public static void loadFrameCircleImg(Context mContext, String url, ImageView imageView, int borderWidth, int colorId) {

        RequestOptions options = new RequestOptions()
                .transform(new CircleCrop())
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .transform(new GlideCircleTransform(mContext, borderWidth,
                        mContext.getResources().getColor(colorId)));

        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(imageView);
    }


    /**
     * 圆形加载带边框
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void loadFrameImg(Context mContext, int path, ImageView imageview) {

        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .transform(new GlideCircleTransform(mContext, 4,
                        mContext.getResources().getColor(R.color.blue)));

        Glide.with(mContext)
                .asBitmap()
                .load(path)
                .apply(options)
                .into(imageview);
    }





    /**
     * 加载图片不需要缓存的
     *
     * @param context
     * @param url
     * @param target
     */
    public static void loadNoMemoryImg(Context context, String url, ImageView target) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }






    /**
     * 获取bitmap
     *
     * @param context
     * @param url
     */
    public static void getImgBitmap(Context context, String url, final BitmapLoadCallBack bitmapLoadCallBack) {

        try {
            Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    bitmapLoadCallBack.onResourceReady(resource,transition);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface BitmapLoadCallBack{
        /**
         * 获取到bitmap 的回调
         * @param resource
         * @param transition
         */
        void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition);
    }


    /**
     * 圆形边框的辅助类
     */
    static class GlideCircleTransform extends BitmapTransformation {

        private Paint mBorderPaint;
        private float mBorderWidth;

        public GlideCircleTransform(Context context) {

        }

        public GlideCircleTransform(Context context, int borderWidth, int borderColor) {

            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;

            mBorderPaint = new Paint();
            mBorderPaint.setDither(true);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(borderColor);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }


        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }

            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            if (mBorderPaint != null) {
                float borderRadius = r - mBorderWidth / 2;
                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
            }
            return result;
        }


        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }
    }






    /**
     * 清除所有缓存
     *
     * @param context
     */
    public void clearCache(final Context context) {
        clearMemoryCache(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                clearDiskCache(context);
            }
        }).start();
    }

    /**
     * 清除内存缓存
     *
     * @param context
     */
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }


    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

}
