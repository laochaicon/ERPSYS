package com.hqyj.erp_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.erp_sys.entity.Warehouse;
import com.hqyj.erp_sys.service.IWarehouseService;
import com.hqyj.erp_sys.util.Auth;
import com.hqyj.erp_sys.util.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
@Api(tags = "仓库模块")
@Auth(roles = "ADMIN,USER")
public class WarehouseController {
    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    //@Resource//二者无区别该注解是根据byName,Autowired是根据byType
    //执行Redis命令的工具对象
    private RedisTemplate redisTemplate;

    //创建记录日志的对象，参数是仓前类的字节码
    Logger logger = LoggerFactory.getLogger(WarehouseController.class);


    /*
     * 假设warehouse模块首页数据(前5条)会被经常访问，
     * 为了减少对Mysql的访问，加快访问速度，将数据保存到redis
     * 流程：
     * 第一次访问，查询mysql，将结果以键值对的形式缓存到redis中，
     * 之后访问，使用键判断redis中是否存在，如果存在直接获取
     *
     * 存入时使用string类型，要将保存的结果转换为JSON字符串
     * 获取到的也是JSON字符串，获取后转换为对象
     * */
    @ApiOperation(value = "条件分页查询所有仓库", notes = "参数page默认为1,size默认为5,keyword默认空字符串")
    @GetMapping("/warehouse")
    public ResultData queryByCondition(@RequestParam(defaultValue = "1") @ApiParam("当前页") Integer page,
                                       @RequestParam(defaultValue = "5") @ApiParam("当前页显示的数据量") Integer size,
                                       @RequestParam(defaultValue = "") @ApiParam("仓库名关键字") String keyword) throws JsonProcessingException {
        //jackson包下JSON与对象互转工具
        ObjectMapper jsonTool = new ObjectMapper();
        //最终返回的数据
        Page<Warehouse> pageInfo;
        //获取操作redis中字符串类型数据的对象
        ValueOperations ops = redisTemplate.opsForValue();
        //查询mysql时的分页模型、条件构造器
        pageInfo = new Page<>(page, size);
        QueryWrapper<Warehouse> wrapper = new QueryWrapper<>();
        wrapper.like("wh_name", keyword);
        //如果要查询首页数据（第1页、前5条，没有关键字）
        if (page == 1 && size == 5 && keyword.length() == 0) {
            //如果redis有，直接获取
            if (ops.get("indexData") != null) {
                pageInfo = jsonTool.readValue(ops.get("indexData").toString(), Page.class);
            } else {
                //如果redis没有，首次访问，查询mysql
                warehouseService.page(pageInfo, wrapper);
                //将要缓存的对象转换为字符串
                String jsonPageInfo = jsonTool.writeValueAsString(pageInfo);
                //将结果缓存到Redis中
                ops.set("indexData", jsonPageInfo);
            }
        } else {
            //非首页数据查询mysql
            warehouseService.page(pageInfo, wrapper);
        }

        logger.info("info级别日志--仓库条件分页查询模块被调用");
        return ResultData.ok("条件分页查询成功", pageInfo);
    }

    /*添加或修改*/
    @PostMapping("/warehouse")
    public ResultData saveOrUpdate(Warehouse warehouse) {
        warehouseService.saveOrUpdate(warehouse);
        //更新了缓存的数据，重新缓存
        redisTemplate.delete("indexData");
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
    public ResultData delete(@PathVariable("id") Integer id) {
        boolean b = warehouseService.removeById(id);
        if (b) {
            //更新了缓存的数据，重新缓存
            redisTemplate.delete("indexData");
            return ResultData.ok("删除成功");
        }
        return ResultData.error(1, "删除失败，请检查ID是否存在");
    }

    @GetMapping("/canUseWarehouse")
    public ResultData canUseWarehouse() {
        return ResultData.ok("查询可用仓库", warehouseService.getCanUseWarehouse());
    }

    /*@Scheduled(cron = "0/5 * * 4W * *")
    public void timeTest(){
        System.out.println("定时任务执行");
    }*/


}
