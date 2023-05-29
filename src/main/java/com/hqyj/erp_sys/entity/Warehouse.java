package com.hqyj.erp_sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
@Data
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库编号
     */
    @TableId(value = "wh_id", type = IdType.AUTO)
    private Integer whId;

    /**
     * 仓库名称
     */
    private String whName;

    /**
     * 利润中心
     */
    private String whProfitCenter;

    /**
     * 地址
     */
    private String whAddr;

    /**
     * 容量
     */
    private Integer whCapacity;

    /**
     * 备注
     */
    private String whNotes;


}
