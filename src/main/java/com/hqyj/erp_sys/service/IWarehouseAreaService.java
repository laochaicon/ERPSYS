package com.hqyj.erp_sys.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hqyj.erp_sys.entity.WarehouseArea;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
public interface IWarehouseAreaService extends IService<WarehouseArea> {

    <P extends IPage<WarehouseArea>> P mySelect(P page, Wrapper<WarehouseArea> queryWrapper);
}
