package com.hongwei.basiclib.permission;

import android.app.Activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * FileName: PermissionHelper
 * Author: XYB
 * Date: 2020/12/29
 * Description:权限申请的方法
 */
public class PermissionHelper {
    //传什么参数
    // 1.Object Fragment or Activity 2.int 请求码 3.需要请求的权限 String[]

    private Object mObject;
    private int mRequestCode;
    private String[] mRequestPerssion;

    public PermissionHelper(Object object){
        this.mObject = object;
    }

    //以什么方式传
    //直接传参
    public static void requestPermission(Activity activity,int requestCode,String[] permission){
        PermissionHelper.with(activity).requestCode(requestCode).requestPerssion(permission).request();
    }

    //链式方式传参
    public static PermissionHelper with(Activity activity){
        return new PermissionHelper(activity);
    }

    public static PermissionHelper with(Fragment fragment){
        return new PermissionHelper(fragment);
    }

    public PermissionHelper requestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionHelper requestPerssion(String... permission){
        this.mRequestPerssion = permission;
        return this;
    }

    public void request(){
        //判断当前的版本是不是6.0以上
        if (!PermissionUtils.isOverMarshmallow()){
            //不是6.0以上
            PermissionUtils.executeSuccedMethod(mObject,mRequestCode);
            return;
        }else {
            //6.0以上
            List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(mObject,mRequestPerssion);
            if (deniedPermissions.size() == 0){
                //全部都是授予过的
                PermissionUtils.executeSuccedMethod(mObject,mRequestCode);
            }else {
                //申请权限
                ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject),
                        deniedPermissions.toArray(new String[deniedPermissions.size()]),mRequestCode);
            }
        }
    }


    //处理申请权限的回调
    public static void requestPermissionsResult(Object object,int requestCode, String[] permissions) {
        //再次获取没有授予的权限
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(object,permissions);
        if (deniedPermissions.size() == 0){
            //全部都是授予过的
            PermissionUtils.executeSuccedMethod(object,requestCode);
        }else {
            //申请的权限 有不同意的
            PermissionUtils.executeFailMethod(object,requestCode);
        }
    }

}
