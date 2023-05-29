package com.hqyj.erp_sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hqyj.erp_sys.entity.WarehouseArea;
import com.hqyj.erp_sys.mapper.WarehouseAreaMapper;
import com.hqyj.erp_sys.service.IWarehouseAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
@Service
public class WarehouseAreaServiceImpl extends ServiceImpl<WarehouseAreaMapper, WarehouseArea> implements IWarehouseAreaService {

    @Override
    public <P extends IPage<WarehouseArea>> P mySelect(P page, Wrapper<WarehouseArea> queryWrapper) {
        return baseMapper.mySelect(page,queryWrapper);
    }
}
