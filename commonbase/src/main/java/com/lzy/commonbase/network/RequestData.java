package com.lzy.commonbase.network;

import android.util.Log;


import com.lzy.commonbase.network.rsa.RSASupport;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by admin on 2019/1/9.
 */

public class RequestData {

    private String json = null;
    private String data = null;
    public Map<String, Object> map = null;
    private RSASupport rsa = null;

    public RequestData(Map<String, Object> map) {
        this.map = map;
        rsa = RSASupport.getRSAInstance();
        getRSAdata(map);
    }
    public RequestData(String s ) {

        rsa = RSASupport.getRSAInstance();
        getRSAdata(s);
    }


    private String getRSAdata(String json) {

        try {
            data = rsa.encryptByPrivateKeyForSpilt(json, rsa.getRSAPriKey());
        } catch (Exception e) {
            Log.e("RequestData", "加密数据失败");
            e.printStackTrace();
        }
        return data;
    }

    private String getRSAdata(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        json = jsonObject.toString();
        try {
            data = rsa.encryptByPrivateKeyForSpilt(json, rsa.getRSAPriKey());
        } catch (Exception e) {
            Log.e("RequestData", "加密数据失败");
            e.printStackTrace();
        }
        return data;
    }

    public Map<String, Object> getParam() {
        Map<String, Object> param = new HashMap<>();
        param.put("ng", data);
        return param;
    }
}
