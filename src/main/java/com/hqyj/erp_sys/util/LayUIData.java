package com.hqyj.erp_sys.util;

import lombok.Data;

import java.util.List;

@Data
public class LayUIData<T> {

    private Integer code;
    private String msg;
    private Long count;
    private List<T> data;

    public LayUIData(Long count, List<T> data) {
        this.code = 0;
        this.msg = "";
        this.count = count;
        this.data = data;
    }

    public static <T> LayUIData getLayUIData(Long count, List<T> data) {
        return new LayUIData(count, data);
    }
}
