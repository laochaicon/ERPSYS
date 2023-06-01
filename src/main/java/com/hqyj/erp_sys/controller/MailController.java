package com.hqyj.erp_sys.controller;

import com.hqyj.erp_sys.service.IMailService;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MailController {
    @Autowired
    private IMailService mailService;

    @RequestMapping("/sendMail")
    public ResultData sengMail(String subject, String text, String to) {
        try {
            mailService.sendMail(subject, text, to);
        }catch (Exception e){
            return ResultData.error(1,"邮件发送失败");
        }
        return ResultData.ok("邮件发送成功");
    }



}
