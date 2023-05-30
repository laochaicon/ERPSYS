package com.hqyj.erp_sys.controller;

import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.mapper.EchartsMapper;
import com.hqyj.erp_sys.service.IWarehouseService;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class EChartsController {
    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private EchartsMapper echartsMapper;

    @GetMapping("/getBarData")
    public ResultData barOfWhNameAndCapacity() {
        //查询所有
        List<Warehouse> list = warehouseService.list();
        //保存x轴和y轴数据的集合
        ArrayList<String> xList = new ArrayList<>();
        ArrayList<Integer> yList = new ArrayList<>();
        for (Warehouse warehouse : list) {
            xList.add(warehouse.getWhName());
            yList.add(warehouse.getWhCapacity());
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("x", xList);
        map.put("y", yList);
        return ResultData.ok("柱状图数据", map);

    }
    @GetMapping("/getPieData/{id}")
    public ResultData getPirData(@PathVariable("id") Integer whId){
        return ResultData.ok("饼图数据",echartsMapper.getPieData(whId));
    }


}
