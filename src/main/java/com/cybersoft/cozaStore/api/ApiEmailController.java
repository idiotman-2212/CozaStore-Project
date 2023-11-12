package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.service.imp.EmailServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mail")
public class ApiEmailController {

    private final EmailServiceImp emailServiceImp;

    public ApiEmailController(EmailServiceImp emailServiceImp) {
        this.emailServiceImp = emailServiceImp;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestParam(value="file", required = false)MultipartFile[] file, String to, String subject, String body){

        String emailResponses = emailServiceImp.sendEmail(file,to,subject,body);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Send email");
        baseResponse.setData(emailResponses);


        //return emailServiceImp.sendEmail(file, to, subject, body);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}

