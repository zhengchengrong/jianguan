package com.luojilab.component.basiclib.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhengchengrong on 2017/9/3.
 */

public class BaseEntity<E> {
    private int code;
    private String msg;
    private E data;

    private String status;
    private String description;
    private List<E> list;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    // 根据后台给的信息，看什么代码代表成功，我的是 001
    public boolean isSuccess() {
        return status.equals("200");
    }
    public boolean isError() {
        return status.equals("400");
    }
    public boolean isParamError() {
        return code == 003;
    }
    public boolean isTokenError() {
        return code == 004;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

}
