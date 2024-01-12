package com.cybersoft.cozaStore.service.imp;

public interface ISecurityUserServiceImp {
    String validatePasswordResetToken(long id, String token);
}
