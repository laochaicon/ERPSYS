package com.hqyj.erp_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqyj.erp_sys.entity.SysUser;
import com.hqyj.erp_sys.service.IMailService;
import com.hqyj.erp_sys.service.ISysUserService;
import com.hqyj.erp_sys.util.Auth;
import com.hqyj.erp_sys.util.JWTUtil;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 老茶
 * @since 2023-05-25
 */
@RestController
@CrossOrigin
@Auth(roles = "ADMIN")
public class SysUserController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResultData login(SysUser sysUser) {
        //将获取到的密码加密
        String newPwd = DigestUtils.md5DigestAsHex(sysUser.getSuPassword().getBytes());
        //更改查询的参数
        sysUser.setSuPassword(newPwd);
        //select * from xxx where name = ? and pwd=?
        //如果创建条件构造器时，参数为一个实体对象
        //该对象的所有非空属性就会作为条件，所有条件用and拼接
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>(sysUser);
        /*
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("su_name", sysUser.getSuName());
        wrapper.eq("su_password", sysUser.getSuPassword());
        */
        SysUser one = sysUserService.getOne(wrapper);
        if (one != null) {
            return ResultData.ok("登录成功", jwtUtil.createToken(one));
        }
        return ResultData.error(1, "用户名或密码错误");

    }

   /* public static void main(String[] args) {
        String s=DigestUtils.md5DigestAsHex("guest".getBytes());
        //获取加密密码
        System.out.println(s);
    }*/

    @Autowired
    private IMailService mailService;
    private String captcha;

    //验证码
    @PostMapping("/mailLogin")
    public ResultData mailLogin(String mail, String captcha) {
        if (this.captcha != null && this.captcha.equalsIgnoreCase(captcha)) {
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.eq("email", mail);
            return ResultData.ok("登录成功", sysUserService.getOne(wrapper));
        }
        return ResultData.error(1, "验证码错误");
    }

    @RequestMapping("/getCaptcha")
    public ResultData checkMail(String mail) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("email", mail);
        if (sysUserService.getOne(wrapper) == null) {
            return ResultData.error(1, "该邮箱不存在");
        } else {
            //使用UUID截取6位
            captcha = UUID.randomUUID().toString().substring(0, 6);
            String text = "验证码为：" + captcha;
            mailService.sendMail("登录验证码", text, mail);
            return ResultData.ok("验证码已发送至" + mail);
        }
    }

    @GetMapping("/sysUsers")
    public ResultData queryByCondition(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "5") Integer size,
                                       @RequestParam(defaultValue = "") String keyword) {
        //创建条件构造器
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like("email", keyword);
        //如果没有关键字，将空值一起查询
        if ("".equals(keyword)) {
            wrapper.or();
            wrapper.isNull("email");
        }
        Page<SysUser> pageInfo = new Page<>(page, size);
        return ResultData.ok("条件分页查询管理员", sysUserService.page(pageInfo, wrapper));
    }


    /*
     * 添加或修改
     * */
    @PostMapping("/sysUser")
    public ResultData saveOrUpdate(SysUser sysUser) {
        sysUserService.saveOrUpdate(sysUser);
        return ResultData.ok("操作成功");
    }

}
