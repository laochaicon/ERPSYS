package com.hqyj.erp_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqyj.erp_sys.entity.SysUser;
import com.hqyj.erp_sys.service.ISysUserService;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
@RestController
@CrossOrigin
public class SysUserController {
    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/login")
    public ResultData login(SysUser sysUser){

        String newPwd= DigestUtils.md5DigestAsHex(sysUser.getSuPassword().getBytes());
        sysUser.setSuPassword(newPwd);

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>(sysUser);

        //System.out.println(wrapper);
        SysUser one = sysUserService.getOne(wrapper);

        if(one!=null){
            System.out.println(one);
            return ResultData.ok("登录成功");
        }
        return ResultData.error(1,"用户名或密码错误");

    }

    /*public static void main(String[] args) {
        String s=DigestUtils.md5DigestAsHex("admin".getBytes());
        System.out.println(s);
    }*/

}
