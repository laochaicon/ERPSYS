package com.hqyj.erp_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.vo.CanUseWareHouse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
public interface IWarehouseService extends IService<Warehouse> {

    List<CanUseWareHouse> getCanUseWarehouse();
}
