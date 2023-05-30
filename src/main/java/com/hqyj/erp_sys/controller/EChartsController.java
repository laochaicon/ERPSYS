package com.hqyj.erp_sys.controller;

import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.service.IWarehouseService;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class EChartsController {
    @Autowired
    private IWarehouseService warehouseService;

    @GetMapping("/getBarData")
    public ResultData barOfWhNameAndCapacity() {
        List<Warehouse> list = warehouseService.list();
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


}
