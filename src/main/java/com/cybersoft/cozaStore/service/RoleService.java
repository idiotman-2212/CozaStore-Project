package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.RoleEntity;
import com.cybersoft.cozaStore.payload.response.RoleResponse;
import com.cybersoft.cozaStore.repository.RoleRepository;
import com.cybersoft.cozaStore.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements RoleServiceImp {

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

    @Override
    public RoleEntity getRoleById(Integer idRole) {
        return roleRepository.findById(idRole).orElse(null);
    }


}
