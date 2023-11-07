package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.SizeEntity;
import com.cybersoft.cozaStore.payload.response.SizeResponse;
import com.cybersoft.cozaStore.repository.SizeRepository;
import com.cybersoft.cozaStore.service.imp.SizeSeviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizeService implements SizeSeviceImp {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<SizeResponse> getAllSize() {
        List<SizeEntity> list = sizeRepository.findAll();
        List<SizeResponse> listResponse = new ArrayList<>();

        for (SizeEntity data: list) {
            SizeResponse sizeResponse = new SizeResponse();
            sizeResponse.setId(data.getId());
            sizeResponse.setName(data.getName());

            listResponse.add(sizeResponse);
        }
        return listResponse;
    }
}
