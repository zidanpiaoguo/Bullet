package com.lzy.commonbase.network;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 *
 * Created by zidan on 2017/10/12.
 */

public abstract class BaseDownload implements Consumer<ResponseBody> {
    private static final String TAG = "BaseDownload";

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;


    private Context mContext;



    public BaseDownload(Context context, String destFileDir , String destFileName ) {
        this.mContext=context;
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;

    }

    @Override
    public void accept(ResponseBody responseBody) throws Exception {
        saveFile(responseBody);
        onHandleSuccess();
    }

    //成功返回值
    public void onHandleSuccess(){

    }


    public  void inProgress(float progress){}




    public File saveFile(ResponseBody response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {


            is = response.byteStream();
            final long total = response.contentLength();
            Log.d(TAG, "saveFile: "+total);
            long sum = 0;

            //创建获取缓存目录
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //创建文件
            File file = new File(dir, destFileName);

            fos = new FileOutputStream(file);

            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;



                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {

                        inProgress(finalSum * 1.0f / total);

                    }
                });
                thread.start();
            }
            fos.flush();

            return file;

        } finally {
            try {
                if (is != null){
                    is.close();
                }

            } catch (IOException e) {
            }
            try {
                if (fos != null){
                    fos.close();
                }

            } catch (IOException e) {
            }

        }
    }


}
