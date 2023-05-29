package com.hqyj.erp_sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表主键
     */
    @TableId(value = "su_id", type = IdType.AUTO)
    private Integer suId;

    private String suName;

    private String suPassword;

    private String email;

    private String suRole;


}
