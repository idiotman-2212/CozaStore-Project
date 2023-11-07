package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.response.RoleResponse;
import com.cybersoft.cozaStore.payload.response.SizeResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleServiceIml {
    List<RoleResponse> getAllRoles();

}
