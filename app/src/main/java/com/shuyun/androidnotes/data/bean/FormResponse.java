package com.shuyun.androidnotes.data.bean;

public class FormResponse<T> {

    private int code;
    private String msg;
    private T object;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FormResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                ", description='" + description + '\'' +
                '}';
    }
}
