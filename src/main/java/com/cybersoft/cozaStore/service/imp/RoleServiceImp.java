package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.response.RoleResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleServiceImp {
    List<RoleResponse> getAllRoles();

}
