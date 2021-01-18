package com.hongwei.basiclib.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: PermissionHelper
 * Author: XYB
 * Date: 2020/12/29
 * Description:权限的相关工具类
 */
public class PermissionUtils {
    //所有都是静态方法  不能实例化对象
    private PermissionUtils(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断是不是6.0以上版本
     * @return
     */
    public static boolean isOverMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void executeSuccedMethod(Object reflectObject, int requestCode) {
        //获取class中所有方法 去遍历
        Method[] methods = reflectObject.getClass().getDeclaredMethods();
        for (Method method: methods){
            PermissionSuccess succedMethod = method.getAnnotation(PermissionSuccess.class);
            if (succedMethod!=null){
                int methodCode = succedMethod.requestCode();
                if (methodCode == requestCode){
                    executeMethod(reflectObject,method);
                }
            }
        }
    }

    private static void executeMethod(Object object,Method method) {
        try {
            //第二个是方法里的参数
            method.setAccessible(true);
            method.invoke(object,new Object(){});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取没有授予的权限
     * @param object
     * @param requestPerssions
     * @return 没有授予过的权限
     */
    public static List<String> getDeniedPermissions(Object object, String[] requestPerssions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String requestPermission:requestPerssions){
            if (ContextCompat.checkSelfPermission(getActivity(object),requestPermission)
                == PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(requestPermission);
            }
        }
        return deniedPermissions;
    }

    public static Activity getActivity(Object object) {
        if (object instanceof Activity){
            return (Activity)object;
        }
        if (object instanceof Fragment){
            return ((Fragment)object).getActivity();
        }
        return null;
    }

    /**
     * 执行失败的方法
     * @param object
     * @param requestCode
     */
    public static void executeFailMethod(Object object, int requestCode) {
        //获取class中所有方法 去遍历
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method: methods){
            PermissionFail fail = method.getAnnotation(PermissionFail.class);
            if (fail!=null){
                int methodCode = fail.requestCode();
                if (methodCode == requestCode){
                    executeMethod(object,method);
                }
            }
        }
    }
}
