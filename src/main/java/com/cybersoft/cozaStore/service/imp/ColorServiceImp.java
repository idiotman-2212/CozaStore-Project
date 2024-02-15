package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.entity.ColorEntity;
import com.cybersoft.cozaStore.payload.response.ColorResponse;

import java.util.List;

public interface ColorServiceImp {
    List<ColorResponse> getAllColor();
}
