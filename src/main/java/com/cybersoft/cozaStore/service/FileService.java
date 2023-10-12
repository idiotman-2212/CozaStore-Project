package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.FileEntity;
import com.cybersoft.cozaStore.repository.FileRepository;
import com.cybersoft.cozaStore.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class FileService implements FileServiceImp {
@Autowired
private FileRepository fileRepository;
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String type = file.getContentType();
        byte[] data = file.getBytes();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setData(data);
        fileEntity.setType(type);

        FileEntity result = fileRepository.save(fileEntity);
        if(result != null){
            return "Upload file thanh cong " + fileName;
        }
        return "Upload file that bai";
    }

    @Override
    public byte[] downloadFile(String fileName) {
        FileEntity file=  fileRepository.findByName(fileName);
        return file.getData();
    }
}
