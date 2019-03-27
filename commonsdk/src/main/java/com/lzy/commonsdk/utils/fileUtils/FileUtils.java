package com.lzy.commonsdk.utils.fileUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * Created by bullet on 2018\6\4 0004.
 */

public class FileUtils {

    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param context Context 使用CopyFiles类的Activity
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后路径  如：xx:/bb/cc
     */
    public static void copyFilesAassets(Context context, String oldPath, String newPath) {
        try {
            //获取assets目录下的所有文件及目录名
            String[] fileNames = context.getAssets().list(oldPath);
            //如果是目录
            if (fileNames.length > 0) {
                File file = new File(newPath);
                //如果文件夹不存在，则递归
                file.mkdirs();
                for (String fileName : fileNames) {
                    copyFilesAassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {
                //如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                //循环从输入流读取 buffer字节
                while ((byteCount = is.read(buffer)) != -1) {
                    //将读取的输入流写入到输出流
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
        }
    }


    /**
     * 加载拷贝文件,把 assets 中的文件加载拷贝到内存中 (和上面的没有本质区别，就是单个文件)
     *
     * @param cascadeDir 句柄     例如：  File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
     * @param assetsFilename     输出的文件地址  例如:  "male_M5_image.jpg"  在cash 项目名/app_cascade 下
     * @param outfile   需要拷贝的assets文件地址  例如:   getAssets().open("male/male_M5_image.jpg")  "male/male_M5_image.jpg"   在assets 目录下
     *
     */
    public static void loadCascadeFile(String cascadeDir, String assetsFilename, InputStream outfile) {

        try {
            File cascadeFile = new File(cascadeDir, assetsFilename);
            if (!cascadeFile.exists()) {
                FileOutputStream os = new FileOutputStream(cascadeFile);
                byte[] buffer = new byte[1024];
                int pos;
                while ((pos = outfile.read(buffer)) != -1) {
                    os.write(buffer, 0, pos);
                }
                outfile.close();
                os.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载文件  以上三个本身是一个种方法。
     *
     * @param filename   拷贝的目标地址  例如：context.getFilesDir().getPath()+"/women/out.obj_0_0"
     * @param is  需要拷贝的文件地址  例如：  context.getAssets().open("women/out.obj_1_0")
     */
    public static void loadCascadeFile(String filename, InputStream is) {

        try {
            File cascadeFile = new File(filename);
            if (!cascadeFile.exists()) {
                FileOutputStream os = new FileOutputStream(cascadeFile);
                byte[] buffer = new byte[1024];
                int pos;
                while ((pos = is.read(buffer)) != -1) {
                    os.write(buffer, 0, pos);
                }
                is.close();
                os.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                //删除单个文件成功！
                return true;
            } else {
                //删除单个文件失败！
                return false;
            }
        } else {
            //删除单个文件不存在！
            return false;
        }
    }
    /**
     * 删除文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file == null){
            return;
        }
        //是否是一个目录
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file1 = files[i];
                deleteFile(file1);
            }
        } else if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 保存图片
     *
     * @param bm
     * @param fileName
     */
    public static void saveBitmap(Bitmap bm, String fileName) {
        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 60, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }





}
