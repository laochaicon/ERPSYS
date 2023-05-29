package com.hqyj.erp_sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hqyj.erp_sys.entity.WarehouseArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
public interface WarehouseAreaMapper extends BaseMapper<WarehouseArea> {

    /*@Select("select wa.*,wh.wh_name as 'wh.wh_name'\n" +
            "from warehouse wh ,warehouse_area wa\n" +
            "where wa.wh_id=wh.wh_id")*/
    @Select("select wa.*,wh.wh_name as 'wh.wh_name'\n" +
            "from warehouse wh inner join warehouse_area wa\n" +
            "on wa.wh_id=wh.wh_id ${ew.customSqlSegment}")
    <P extends IPage<WarehouseArea>> P mySelect(P page,@Param("ew") Wrapper<WarehouseArea> queryWrapper);

    @Select("select * from warehouse_area ${ew.customSqlSegment}")
    @Results({
            @Result(property = "whId",column ="wh_id"),
            @Result(property = "wh",column = "wh_id",one = @One(select = "com.hqyj.erp_sys.mapper.WarehouseMapper.selectById"))
    })
    @Override
    <P extends IPage<WarehouseArea>> P selectPage(P page, @Param("ew") Wrapper<WarehouseArea> queryWrapper);

    @Override
    int update(WarehouseArea entity,@Param("ew") Wrapper<WarehouseArea> updateWrapper);
}
