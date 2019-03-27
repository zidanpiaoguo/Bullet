package com.lzy.commonsdk.utils.audio;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 录音
 *
 * @author bullet
 * @date 2017\8\30 0008.
 */

public class AudioRecoderUtils {

    /**
     * 最大录音时长1000*60*2;
     */
    private static final int MAX_LENGTH = 1000 * 60 * 2;
    private final String TAG = "AudioRecoderUtils";
    /**
     * 文件路径
     */
    private String filePath;

    //文件夹路径
    private String FolderPath;
    private long startTime;
    private long endTime;

    //录音
    private MediaRecorder mMediaRecorder;
    private OnAudioStatusUpdateListener audioStatusUpdateListener;
    //文件名称
    private String fileName;

    /**
     * 文件存储默认sdcard/record
     */
    public AudioRecoderUtils() {
        //默认保存路径为/sdcard/record/下
        this(Environment.getExternalStorageDirectory() + "/hh/record/");
    }

    public AudioRecoderUtils(String filePath) {
        File path = new File(filePath);
        if (!path.exists()){
            path.mkdirs();
        }
        this.FolderPath = filePath;
    }

    /**
     * 开始录音 使用amr格式
     * 录音文件
     *
     * @return
     */
    public void startRecord() {
        Log.d("1111", "11111----录音");
        // 开始录音
        //1、实例化MediaRecorder对象
        if (mMediaRecorder == null){
            mMediaRecorder = new MediaRecorder();
        }

        try {
            ///2、setAudioSource/setVedioSource
            // 设置麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            /*
             *设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            fileName = getRandomFileName() + ".amr";
            filePath = FolderPath + fileName;

            //3、准备
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.setMaxDuration(MAX_LENGTH);
            mMediaRecorder.prepare();
            //4、开始
            mMediaRecorder.start();
            //获取开始时间
            startTime = System.currentTimeMillis();
            Log.e(TAG, "startTime" + startTime);
        } catch (IllegalStateException e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    /**
     * 获取随机录音名称
     */
    public String getRandomFileName() {
        String rel = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        rel = formatter.format(curDate);
        rel = rel + new Random().nextInt(1000);
        return rel;
    }

    /**
     * 停止录音
     */
    public long stopRecord() {
        if (mMediaRecorder == null){
            return 0L;
        }
        endTime = System.currentTimeMillis();
        //在5.0以上在调用stop的时候会报错,捕获异常清理一下
        try {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            audioStatusUpdateListener.onStop((endTime - startTime), filePath, fileName);
            fileName = "";
            filePath = "";
        } catch (RuntimeException e) {
            audioStatusUpdateListener.onError(true, e.toString());
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            File file = new File(filePath);
            if (file.exists()){
                file.delete();
            }

            filePath = "";
        }
        return endTime - startTime;
    }

    /**
     * 取消录音
     */
    public void cancelRecord() {
        try {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        } catch (RuntimeException e) {
            audioStatusUpdateListener.onError(true, e.toString());
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }

        filePath = "";
    }

    /**
     * 删除录音文件
     */
    public void deleteRecord(String deletefilePath) {
        File file = new File(deletefilePath);
        if (file.exists()){
            file.delete();
        }

        filePath = "";
    }

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }

    public interface OnAudioStatusUpdateListener {
        /**
         * 停止录音
         *
         * @param filePath 保存路径
         */
        void onStop(long time, String filePath, String fileName);

        /**
         * 录音错误
         * 在调用start()后马上调用stop(),时由于没有生成有效的音频或是视频数据。会造成 stop failed错误
         */
        void onError(Boolean isError, String error);
    }

}