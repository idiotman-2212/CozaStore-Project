package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class ApiFileController {
    @Autowired
    private FileServiceImp fileServiceImp;

    @PostMapping("")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
       String message = fileServiceImp.uploadFile(file);
        BaseResponse baseresponse = new BaseResponse();
        baseresponse.setStatusCode(200);
        baseresponse.setMessage("Thanh cong");
        baseresponse.setData(message);
        return new ResponseEntity<>(baseresponse, HttpStatus.OK);
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName){
        byte[] fileImage =  fileServiceImp.downloadFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<> (fileImage, headers, HttpStatus.OK);
    }
}
