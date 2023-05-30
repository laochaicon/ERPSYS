package com.hqyj.erp_sys.mapper;

import com.hqyj.erp_sys.vo.DataOfECharts;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EchartsMapper {
    @Select("select wa_name as name,wa_capacity as value from warehouse wh,warehouse_area wa\n" +
            "where wh.wh_id=wa.wh_id and wa_status=1 and wh.wh_id=#{whId}")
    List<DataOfECharts> getPieData(Integer whId);

}
