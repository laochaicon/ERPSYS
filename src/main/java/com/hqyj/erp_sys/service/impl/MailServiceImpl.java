package com.hqyj.erp_sys.service.impl;

import com.hqyj.erp_sys.service.IMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MailServiceImpl  implements IMailService {
    @Resource
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(String subject,String text ,String to){
        //创建邮件模板
        SimpleMailMessage mail = new SimpleMailMessage();
        //发件人
        mail.setFrom(sender);
        //收件人
        mail.setTo(to);
        //主题
        mail.setSubject(subject);
        //内容
        mail.setText(text);
        //通过邮件工具类发送
        mailSender.send(mail);
    }
}
