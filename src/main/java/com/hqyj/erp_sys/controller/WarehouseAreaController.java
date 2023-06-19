package com.hqyj.erp_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.erp_sys.entity.WarehouseArea;
import com.hqyj.erp_sys.service.IWarehouseAreaService;
import com.hqyj.erp_sys.util.Auth;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Auth(roles = "ADMIN,USER")
public class WarehouseAreaController {

    @Autowired
    private IWarehouseAreaService waService;

    /*分页条件查询*/
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

    /*添加或修改*/
    @PostMapping("/warehouseArea")
    public ResultData saveOrUpdate(WarehouseArea warehouseArea){
        waService.saveOrUpdate(warehouseArea);
        return ResultData.ok("添加或修改成功");
    }
    /*删除*/
    @DeleteMapping("/warehouseArea")
    public ResultData delete(WarehouseArea warehouseArea){
        waService.removeById(warehouseArea );
        return ResultData.ok("删除成功");
    }

    /*根据id删除*/
    @DeleteMapping("/warehouseArea/{id}")
    public ResultData delete(@PathVariable("id")Integer id) {
        boolean b=waService.removeById(id);
        if(b){
            return ResultData.ok("删除成功");
        }
        return ResultData.error(1,"删除失败，请检查ID是否存在");
    }

    @PutMapping("/changeStatus")
    public ResultData changeStatus(Integer waId, Integer status) {
        WarehouseArea wa = new WarehouseArea();
        wa.setWaId(waId);
        wa.setWaStatus(Math.abs(1 - status));
        waService.updateById(wa);
        return ResultData.ok("操作成功");
    }


}
