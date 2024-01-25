package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.entity.RoleEntity;
import com.cybersoft.cozaStore.payload.response.RoleResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RoleServiceImp {
    List<RoleResponse> getAllRoles();

    RoleEntity getRoleById(Integer idRole);
}
