package com.lzy.commonsdk.utils.imageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Image的工具类
 *
 * @author bullet
 * @date 2019\1\8 0008.
 */

public class ImageTools {


    /**
     * 绘制水印图片
     * 使用方法：例如放在（距离最下面height高度的中间）：
     * paddingLeft： (bitmap.getWidth() - watermark.getWidth())/2
     * paddingTop ：(bitmap.getHeight() - watermark.getHeight())-height)
     *
     *
     * @param bitmap 原图
     * @param watermark 水印
     * @param paddingLeft 距离左边
     * @param paddingTop  距离右边
     */
    public static Bitmap createWaterMaskBitmap(Bitmap bitmap, Bitmap watermark, int paddingLeft, int paddingTop) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //创建一个bitmap
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newBitmap);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(bitmap, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save();
        // 存储
        canvas.restore();
        return newBitmap;
    }


    /**
     * 图片转换成bite
     * @param bmp  图片
     * @param needRecycle 是否回收
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 图片url转bitmap
     */
    public static Bitmap getBitBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            in.close();
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return map;
    }



    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     * @return          Bitmap
     */
    public static Bitmap getSmallBitmap(String filePath, int newWidth, int newHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置只解析图片的边界参数，即宽高
        options.inJustDecodeBounds = true;
        //options.inSampleSize = 2;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        //科学计算图片所需的采样比例
        options.inSampleSize = calculateInSampleSize(options, newWidth, newHeight);
        //设置图片加载的渲染模式为Config.RGB_565，能降低一半内存，但是会影响图片质量
        //options.inPreferredConfig = Bitmap.Config.RGB_565;
        // Decode bitmap with inSampleSize set
        //关闭标记，解析真实的图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        //质量压缩
        Bitmap newBitmap = compressImage(bitmap, 500);
        if (bitmap != null){
            bitmap.recycle();
        }
        return newBitmap;
    }


    /**
     * 计算图片的缩放值
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 质量压缩
     * @param image
     * @param maxSize
     */
    private static Bitmap compressImage(Bitmap image, int maxSize){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 80;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while ( os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        Bitmap bitmap = null;
        byte[] b = os.toByteArray();
        if (b.length != 0) {
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return bitmap;
    }


}
