package com.hqyj.erp_sys.vo;

import lombok.Data;

@Data
public class CanUseWareHouse {
    private Integer whId;
    private String whName;
    private Integer whCapacity;
    private Integer used;
    private Integer available;
}
