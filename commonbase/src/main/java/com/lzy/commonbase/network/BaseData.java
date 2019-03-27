package com.lzy.commonbase.network;

import android.util.Log;

import com.lzy.commonbase.network.rsa.RSASupport;


public class BaseData<T> {
    public int code;
    public String data;
    public String message;
    private RSASupport rsa = RSASupport.getRSAInstance();

    public boolean isSuccess() {
        return code == 200;
    }

    public int getStatus() {
        return code;
    }

    public String getData() {
        try {
            data = rsa.decryptByPublicKeyForSpilt(data, rsa.getRSAPubKey());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BaseData", "数据解析失败");
        }
        return data;
    }




    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

}