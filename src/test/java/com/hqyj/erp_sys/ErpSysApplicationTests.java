package com.hqyj.erp_sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.mapper.WarehouseMapper;
import com.hqyj.erp_sys.service.IWarehouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ErpSysApplicationTests {

    @Autowired
    private IWarehouseService warehouseService;
    @Test
    void contextLoads() {
        List<Warehouse> list = warehouseService.list();
        System.out.println(list);

    }
    @Test
    void test1(){
        QueryWrapper<Warehouse> wrapper = new QueryWrapper<>();
        wrapper.like("wh_name","仓库");
        warehouseService.list(wrapper).forEach(System.out::println);
        //System.out.println(wrapper);
    }

}
