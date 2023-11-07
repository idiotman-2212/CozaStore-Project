package com.cybersoft.cozaStore.service.imp;

import org.springframework.web.multipart.MultipartFile;

public interface EmailServiceImp {
    String sendEmail(MultipartFile[] file, String to, String subject, String body);
}

