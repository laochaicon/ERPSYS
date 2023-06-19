package com.hqyj.erp_sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hqyj.erp_sys.entity.Material;
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
public interface MaterialMapper extends BaseMapper<Material> {

    @Select("select * from material ${ew.customSqlSegment}")
            @Results({
                    @Result(property = "waId",column = "wa_id"),
                    @Result(property = "wa",column = "wa_id",one = @One(select = "com.hqyj.erp_sys.mapper.WarehouseAreaMapper.selectById"))
            })
    <P extends IPage<Material>> P selectPage(P page,@Param("ew") Wrapper<Material> queryWrapper);
}
