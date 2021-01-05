package com.hongwei.basiclib.bean;

/**
 * @author xyb
 * @description:封装的对话框实体类
 * @date : 2021-01-05
 */

public final class DialogBean {

    private boolean isShow;
    private String msg;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
