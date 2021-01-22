package com.hongwei.furniture.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class RequestUtils {

    public static InnerParam newParams() {
        return new InnerParam();
    }

    public static InnerParam newParams(boolean isWithToken) {
        return new InnerParam(isWithToken);
    }

    public static class InnerParam {

        Map<String, Object> mMap;

        public InnerParam() {
            this(true);
        }

        public InnerParam(boolean isWithToken) {
            mMap = new HashMap<>();
//            if (isWithToken) {
//                mMap.put("token", TextUtils.isEmpty((String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, "")) ? "" : (String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, ""));
//            }
        }

        public InnerParam addParams(String key, String value) {
            mMap.put(key, value);
            return this;
        }

        public InnerParam addParams(String key, Object value) {
            mMap.put(key, value);
            return this;
        }

        public InnerParam addParams(String key, int value) {
            mMap.put(key, value + "");
            return this;
        }

        public InnerParam addParams(String key, long value) {
            mMap.put(key, value + "");
            return this;
        }

        public InnerParam addParams(String key, double value) {
            mMap.put(key, value + "");
            return this;
        }

        public Map<String, Object> create() {
            return mMap;
        }

        public RequestBody createRequestBody() {
            return transformToRequestBody(mMap);
        }

    }

    public static RequestBody transformToRequestBody(Map<String, Object> requestDataMap) {
        Log.d("请求参数--》", requestDataMap.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(requestDataMap));
        return requestBody;
    }


    /**
     * 单文件上传
     * 例子： @Multipart
     * #     @POST("api/other/v1/upload") Observable<HttpResult> upLoad(@Part MultipartBody.Part body);
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part transformToRequestMultipartBodyPart(File file) {
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);
        return part;
    }

}
