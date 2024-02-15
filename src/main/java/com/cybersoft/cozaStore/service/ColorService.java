package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.ColorEntity;
import com.cybersoft.cozaStore.entity.SizeEntity;
import com.cybersoft.cozaStore.payload.response.ColorResponse;
import com.cybersoft.cozaStore.repository.ColorRepository;
import com.cybersoft.cozaStore.service.imp.ColorServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColorService implements ColorServiceImp {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<ColorResponse> getAllColor() {
        List<ColorEntity> list = colorRepository.findAll();
        List<ColorResponse> listResponse = new ArrayList<>();

        for (ColorEntity data: list) {
            ColorResponse colorResponse = new ColorResponse();
            colorResponse.setId(data.getId());
            colorResponse.setName(data.getName());

            listResponse.add(colorResponse);
        }
        return listResponse;
    }
}
