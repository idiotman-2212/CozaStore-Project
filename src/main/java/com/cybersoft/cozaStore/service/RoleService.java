package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.RoleEntity;
import com.cybersoft.cozaStore.entity.SizeEntity;
import com.cybersoft.cozaStore.payload.response.RoleResponse;
import com.cybersoft.cozaStore.payload.response.SizeResponse;
import com.cybersoft.cozaStore.repository.RoleRepository;
import com.cybersoft.cozaStore.repository.SizeRepository;
import com.cybersoft.cozaStore.service.imp.RoleServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements RoleServiceIml {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<RoleResponse> getAllRoles() {
        List<RoleEntity> list = roleRepository.findAll();
        List<RoleResponse> listResponse = new ArrayList<>();

        for (RoleEntity data: list) {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(data.getId());
            roleResponse.setName(data.getName());

            listResponse.add(roleResponse);
        }
        return listResponse;
    }
}
