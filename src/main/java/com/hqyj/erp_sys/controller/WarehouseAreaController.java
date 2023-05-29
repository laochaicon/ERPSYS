package com.hqyj.erp_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.entity.WarehouseArea;
import com.hqyj.erp_sys.service.IWarehouseAreaService;
import com.hqyj.erp_sys.service.IWarehouseService;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
@CrossOrigin
@RestController
public class WarehouseAreaController {

    @Autowired
    private IWarehouseAreaService waService;

    @GetMapping("/warehouseArea")
    public ResultData queryByCondition(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "5") Integer limit,
                                       @RequestParam(defaultValue = "") String keyword) {
        Page<WarehouseArea> pageInfo = new Page<>(page, limit);
        QueryWrapper<WarehouseArea> wrapper = new QueryWrapper<>();
        wrapper.like("wa_name",keyword);
        //waService.mySelect(pageInfo,wrapper);

        waService.page(pageInfo,wrapper);
        return ResultData.ok("条件分页查询成功", pageInfo);
    }
}
