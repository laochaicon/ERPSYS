package com.hqyj.erp_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqyj.erp_sys.entity.SysUser;
import com.hqyj.erp_sys.service.IMailService;
import com.hqyj.erp_sys.service.ISysUserService;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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

    @Autowired
    private IMailService mailService;
    @RequestMapping("/getCaptcha")
    public ResultData checkMail(String mail){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("email",mail);
        if(sysUserService.getOne(wrapper)==null){
            return ResultData.error(1,"该邮箱不存在");
        }else {
            String captcha= UUID.randomUUID().toString().substring(0,6);
            captcha="验证码为《"+captcha+"》";
            mailService.sendMail("登录验证码",captcha,mail);
            return  ResultData.ok("验证码已发送至"+mail);
        }
    }

}
