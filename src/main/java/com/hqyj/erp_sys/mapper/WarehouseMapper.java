package com.hqyj.erp_sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.vo.CanUseWareHouse;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
public interface WarehouseMapper extends BaseMapper<Warehouse> {

    @Select("SELECT *,( wh_capacity - used ) AS available \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\twh.wh_id,\n" +
            "\t\twh_name,\n" +
            "\t\twh_capacity,\n" +
            "\t\tsum( wa_capacity ) AS used \n" +
            "\tFROM\n" +
            "\t\twarehouse wh\n" +
            "\t\tLEFT JOIN warehouse_area wa ON wh.wh_id = wa.wh_id \n" +
            "\tGROUP BY\n" +
            "\t\twh.wh_id \n" +
            "\t) temp \n" +
            "WHERE\n" +
            "\tused IS NULL \n" +
            "\tOR wh_capacity > used")
    List<CanUseWareHouse> getCanUseWarehouse();
}
