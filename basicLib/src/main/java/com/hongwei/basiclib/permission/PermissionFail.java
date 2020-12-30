package com.hongwei.basiclib.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * FileName: PermissionFail
 * Author: XYB
 * Date: 2020/12/29
 * Description:权限有拒绝的注解回调
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionFail {

    int requestCode();//请求码
}
