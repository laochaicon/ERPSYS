package com.hqyj.erp_sys.util;

import lombok.Data;

import java.util.List;

/*
 * 用于统一控制层方法的返回值
 * */
@Data
public class ResultData<T> {

    //返回状态码  0-成功  其他表示失败
    private Integer code;
    //返回消息文字
    private String msg;
    //返回集合
    private List<T> data;
    //返回对象
    private Object obj;

    /*
     * 条件分页查询返回分页模型对象、添加、修改成功返回对象时
     * */
    public ResultData(Integer code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    /*
     * 查询返回集合
     * */
    public ResultData(Integer code, String msg, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /*
     * 只需设置状态码和消息时
     * */
    public ResultData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultData ok(String msg, Object obj) {
        return new ResultData(0, msg, obj);
    }

    public static <T> ResultData ok(String msg, List<T> data) {
        return new ResultData(0, msg, data);
    }

    public static ResultData ok(String msg) {
        return new ResultData(0, msg);
    }

    public static ResultData error(Integer code, String msg) {
        return new ResultData(code, msg);
    }
}
