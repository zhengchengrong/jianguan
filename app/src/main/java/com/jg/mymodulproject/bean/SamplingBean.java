package com.jg.mymodulproject.bean;

/**
 * Created by llz on 2018/3/30.
 */

public class SamplingBean {
    private String id;
    private String gcName; // 工程名称
    private String gcNumber;//工程编号
    private String ypNumber;//样品编号
    private String gcPart;//工程部位

    public String getId() {
        return id;
    }

    public String getGcNumber() {
        return gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public String getYpNumber() {
        return ypNumber;
    }

    public void setYpNumber(String ypNumber) {
        this.ypNumber = ypNumber;
    }

    public String getGcPart() {
        return gcPart;
    }

    public void setGcPart(String gcPart) {
        this.gcPart = gcPart;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }
}
