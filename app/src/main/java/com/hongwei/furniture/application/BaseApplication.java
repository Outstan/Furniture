package com.hongwei.furniture.application;

import android.app.Application;
import android.content.Context;

import com.hongwei.basiclib.log.LogLevel;
import com.hongwei.basiclib.network.HttpMethods;
import com.hongwei.furniture.api.ApiService;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xyb
 * @description:Application
 * @date : 2020-12-31
 */
public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //访问网络初始化
        HttpMethods
                .getInstanceBuilder()
                .setBaseUrl(ApiService.BASE_URL)//设置域名
                .setLogLevel(LogLevel.ERROR)//设置日志打印级别，使用默认的日志打印才需要设置这个
                .setLogName("HttpManager")//设置默认日志打印名字
                .setIsOpenLog(true)//设置是否开启框架默认的日志打印
                .setUseDefaultSSLSocketFactory(true)
//                .setCookieJar(new CookieJarImpl())//设置自定义的cookiejar
//                .setLogger(new HttpLogger())//设置自定义logger，此设置是打印网络请求的数据（如果设置了自定义的，则框架默认的则不需要设置）
//                .setLevel(LoggerLevel.BODY)//设置日志打印级别（自定义logger可设置，框架默认的是BODY级别，如果上架需要关闭日志打印，则设置setIsOpenLog(false)即可）
                .setReadTimeOut(60)
                .setConnectTimeOut(60)
                .setWriteTimeOut(60)
//                .setInterceptor(new CommonParametersInterceptor())//设置单个拦截器
//                .setNetworkInterceptor(new CommonParametersInterceptor())//设置拦截器
                .setFactory(GsonConverterFactory.create())//设置自定义解析器
                .setInterceptors(new HttpLoggingInterceptor());//设置多个拦截器
    }

    public static Context getInstance(){
        return mContext;
    }
}
