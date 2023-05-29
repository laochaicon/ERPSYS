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
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物料编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 物料名称
     */
    private String name;

    /**
     * 当前库存
     */
    private Integer count;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 安全库存
     */
    private Integer safeCount;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 所在库区
     */
    private Integer waId;


}
