package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.payload.response.RoleResponse;
import com.cybersoft.cozaStore.service.imp.RoleServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class ApiRoleController {

    @Autowired
    private RoleServiceIml roleServiceIml;


    @GetMapping("")
    public ResponseEntity<?> getAllRoles(){
        List<RoleResponse> roleResponseList = roleServiceIml.getAllRoles();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all roles");
        baseResponse.setData(roleResponseList);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
