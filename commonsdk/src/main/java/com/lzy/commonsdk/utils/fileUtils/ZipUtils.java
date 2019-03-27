package com.lzy.commonsdk.utils.fileUtils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *  压缩工具
 *  生成压缩包
 *  事例：       ZipUtils.ZipFolder(list, mContext.getFilesDir().getPath() + "/women/","face");
 *
 * Created by bullet on 2018\11\30 0030.
 */

public class ZipUtils {

    private static final String TAG = "ZipUtils";


    /**
     *
     * 压缩文件和文件夹
     *
     *  切记：不要把压缩的地址放在压缩文件夹内，会循环压缩。
     *
     * @param srcFileString 要压缩的文件或文件夹
     * @param zipFileString 压缩完成的Zip路径  例如：、data/
     * @param name  压缩包的名字

     * @throws Exception
     */
    public static void ZipFolder(String srcFileString, String zipFileString, String name ) throws Exception {
        File outFile = new File(zipFileString + name+".zip");
        //创建文件夹
        File filePath = new File(zipFileString);
        if (!filePath.exists())
            filePath.mkdirs();

        //删除旧的文件
        if(outFile.exists())
            outFile.delete();

        File file = new File(srcFileString);

        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(outFile),new CRC32()));
            ZipFiles(file,zipOutputStream,name);
            zipOutputStream.flush();
            zipOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 压缩几个文件
     *
     * @param srcFiles  要压缩的文件地址的list
     * @param zipFileString 压缩完成的Zip路径  例如： data/
     * @param name  压缩包的名字  例如： apk
     * @throws Exception
     */
    public static void ZipFolder(List<String> srcFiles, String zipFileString, String name ) throws Exception {
        File outFile = new File(zipFileString + name+".zip");
        // 创建文件夹
        File filePath = new File(zipFileString);
        if (!filePath.exists())
            filePath.mkdirs();
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(outFile),new CRC32()));
            for (int i = 0; i < srcFiles.size(); i++) {
                File file = new File(srcFiles.get(i));
                if(file.exists()){
                    ZipFiles(file,zipOutputStream,file.getName());
                }
            }
            zipOutputStream.flush();
            zipOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    /**
     * 压缩文件
     *
     * @param fileSrc  要压缩的文件
     * @param zipOutputStream  写入流
     * @param name  压缩包的名字
     * @throws Exception
     */
    private static void ZipFiles(File fileSrc , ZipOutputStream zipOutputStream, String name) throws Exception {
        if (fileSrc.isDirectory()) {
            System.out.println("需要压缩的地址是目录");
            File[] files = fileSrc.listFiles();


            //此处是可以压缩包含文件夹的
//            name = name+"/";
//            zipOutputStream.putNextEntry(new ZipEntry(name));  // 建一个文件夹
//
//            for (File f : files) {
//                ZipFiles( f,zipOutputStream, name + f.getName(),isFile);
//                Log.d(TAG, "ZipFiles: "+name + f.getName());
//            }

            for (File f : files) {
                ZipFiles( f, zipOutputStream,  f.getName());
                Log.d(TAG, "ZipFiles: "+ f.getName());
            }

        }else {
            System.out.println("需要压缩的地址是文件");
            zipOutputStream.putNextEntry(new ZipEntry(name));
            FileInputStream input = new FileInputStream(fileSrc);
            byte[] buf = new byte[1024];
            int len = -1;

            while ((len = input.read(buf)) != -1) {
                zipOutputStream.write(buf, 0, len);
            }

            zipOutputStream.flush();
            input.close();
        }
    }


    /**
     * 解压缩
     * 将zipFile文件解压到folderPath目录下.
     * @param zipFile zip文件
     * @param folderPath 解压到的地址
     * @throws IOException
     */
    public void upZipFile(File zipFile, String folderPath) throws IOException {
        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        Log.d(TAG, "upZipFile: ");
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                Log.d(TAG, "ze.getName() = " + ze.getName());
                String dirstr = folderPath + ze.getName() ;
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                Log.d(TAG, "str = " + dirstr);
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            Log.d(TAG, "ze.getName() = " + ze.getName());
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    private File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length >= 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);
            }
            Log.d(TAG, "1ret = " + ret);
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                Log.d(TAG, "substr = " + substr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            Log.d(TAG, "2ret = " + ret);
            return ret;
        }
        return ret;
    }




}
