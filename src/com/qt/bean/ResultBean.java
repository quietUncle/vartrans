package com.qt.bean;

public class ResultBean {
    private String key;
    private String value;
    private String type;


    public ResultBean(String key,String value,String type){
        this.key=key;
        this.value=value;
        this.type=type;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
