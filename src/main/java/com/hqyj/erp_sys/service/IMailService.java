package com.hqyj.erp_sys.service;

public interface IMailService {

    void sendMail(String subject,String text,String to);
}
