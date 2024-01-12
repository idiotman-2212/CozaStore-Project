package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.FileEntity;
import com.cybersoft.cozaStore.entity.OrderEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.response.FileResponse;
import com.cybersoft.cozaStore.payload.response.OrderResponse;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.repository.FileRepository;
import com.cybersoft.cozaStore.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (file != null) {
            if (file.getData() != null) {
                return file.getData();
            } else {
                throw new IllegalStateException("File data is null for file: " + fileName);
            }
        } else {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
    }
    @Override
    public boolean deleteFileById(int id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id); // Xóa sản phẩm
            return true; // Trả về true nếu xóa thành công
        } else {
            return false; // Trả về false nếu không tìm thấy sản phẩm để xóa
        }
    }

    @Override
    public String updateFileById(int fileId, MultipartFile file) throws IOException {
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(fileId);

        if (optionalFileEntity.isPresent()) {
            FileEntity existingFileEntity = optionalFileEntity.get();

            // Cập nhật thông tin mới của file
            existingFileEntity.setName(file.getOriginalFilename());
            existingFileEntity.setType(file.getContentType());
            existingFileEntity.setData(file.getBytes());

            // Lưu file đã cập nhật vào cơ sở dữ liệu
            FileEntity updatedFileEntity = fileRepository.save(existingFileEntity);

            if (updatedFileEntity != null) {
                return "Cập nhật file thành công: " + updatedFileEntity.getName();
            }
        }

        return "Không tìm thấy file với id: " + fileId + " hoặc cập nhật thất bại";
    }

    @Override
    public List<FileResponse> getAllFile() {
        List<FileEntity> list = fileRepository.findAll();
        List<FileResponse> responseList = new ArrayList<>();

        for (FileEntity o: list){
            FileResponse fileResponse = new FileResponse();
            fileResponse.setId(o.getId());
            fileResponse.setName(o.getName());
            fileResponse.setType(o.getType());
            fileResponse.setData(o.getData());

            responseList.add(fileResponse);
        }

        return responseList;
    }


}
