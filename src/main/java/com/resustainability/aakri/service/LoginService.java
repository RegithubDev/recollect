package com.resustainability.aakri.service;

import java.util.List;
import com.resustainability.aakri.entity.UserEntity;

public interface LoginService {
	
    UserEntity findByPhoneNumber(String phoneNumber);
    
	List<UserEntity> findAll();
}
