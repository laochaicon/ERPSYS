package com.hqyj.erp_sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.mapper.WarehouseMapper;
import com.hqyj.erp_sys.service.IWarehouseService;
import com.hqyj.erp_sys.vo.CanUseWareHouse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    @Override
    public List<CanUseWareHouse> getCanUseWarehouse() {
        return baseMapper.getCanUseWarehouse();
    }
}
