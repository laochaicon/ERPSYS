package com.hqyj.erp_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.service.IWarehouseService;
import com.hqyj.erp_sys.util.LayUIData;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
@CrossOrigin
@RestController

public class WarehouseController {
    @Autowired
    private IWarehouseService warehouseService;

    @GetMapping("/warehouse")
    public ResultData queryByCondition(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "5") Integer limit,
                                       @RequestParam(defaultValue = "") String keyword) {
        Page<Warehouse> pageInfo = new Page<>(page, limit);
        QueryWrapper<Warehouse> wrapper = new QueryWrapper<>();
        wrapper.like("wh_name", keyword);
        warehouseService.page(pageInfo, wrapper);
        return ResultData.ok("条件分页查询成功", pageInfo);
    }

    /*添加或修改*/
    @PostMapping("/warehouse")
    public ResultData saveOrUpdate(Warehouse warehouse) {
        warehouseService.saveOrUpdate(warehouse);
        return ResultData.ok("操作成功");
    }

    /*删除*/
    @DeleteMapping("/warehouse")
    public ResultData delete(Warehouse warehouse) {
        warehouseService.removeById(warehouse);
        return ResultData.ok("删除成功");
    }

    /*根据id删除*/
    @DeleteMapping("/warehouse/{id}")
    public ResultData delete(@PathVariable("id")Integer id) {
        boolean b=warehouseService.removeById(id);
        if(b){
            return ResultData.ok("删除成功");
        }
        return ResultData.error(1,"删除失败，请检查ID是否存在");
    }
}
