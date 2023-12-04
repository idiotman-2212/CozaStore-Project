package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.entity.FileEntity;
import com.cybersoft.cozaStore.payload.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileServiceImp {

    String uploadFile(MultipartFile file) throws IOException;

    byte[] downloadFile(String fileName) ;

    boolean deleteFileById(int id);
    String updateFileById(int fileId, MultipartFile file) throws IOException;

    List<FileResponse> getAllFile();
}
