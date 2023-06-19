package com.hqyj.erp_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.erp_sys.entity.Material;
import com.hqyj.erp_sys.service.IMailService;
import com.hqyj.erp_sys.service.IMaterialService;
import com.hqyj.erp_sys.util.Auth;
import com.hqyj.erp_sys.util.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
@Auth(roles = "ADMIN,USER")
public class MaterialController {

    @Autowired
    private IMailService mailService;
    @Autowired
    private IMaterialService materialService;

    //创建记录日志的对象，参数是仓前类的字节码
    Logger logger = LoggerFactory.getLogger(WarehouseController.class);


    @Scheduled(cron = "0 30 17 * * *")
    public void quartsTasks() {

        String to = "MingMSir@163.com";
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "TG羽绒服");
        //查询
        Material one = materialService.getOne(wrapper);
        try {
            mailService.sendMail("【" + one.getName() + "】库存信息", one.toString(), to);
        } catch (Exception e) {
            logger.error("向" + to + "发送邮件失败");
        }
        logger.info("向" + to + "发送邮件成功");
    }

    @GetMapping("/material")
    public ResultData queryByCondition(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "5") Integer size,
                                       @RequestParam(defaultValue = "") String keyword) {
        //创建条件构造器
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        wrapper.like("name", keyword);
        Page<Material> pageInfo = new Page<>(page, size);
        return ResultData.ok("条件分页查询管理员", materialService.page(pageInfo, wrapper));
    }

    @PostMapping("/material/upload")
    public ResultData upload(Integer id, MultipartFile materialImg) throws IOException {
        String oldName = materialImg.getOriginalFilename();
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String newName = UUID.randomUUID() + suffix;
        File file = new File("D:\\upload\\imgs", newName);
        materialImg.transferTo(file);

        //修改该物料的图片字段
        Material material = new Material();
        material.setId(id);
        material.setImg(newName);
        boolean b = materialService.updateById(material);

        if (file.exists() && b){
            return ResultData.ok("上传成功");
        }
        return ResultData.error(1,"上传失败");

    }
}
