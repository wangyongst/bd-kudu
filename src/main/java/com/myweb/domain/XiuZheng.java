package com.myweb.domain;

import java.io.Serializable;

public class XiuZheng implements Serializable {

    private String name;
    private String mobile;
    private String line;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}