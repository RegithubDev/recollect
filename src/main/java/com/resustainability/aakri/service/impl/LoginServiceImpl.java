package com.resustainability.aakri.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resustainability.aakri.Dao.impl.LoginRepository;
import com.resustainability.aakri.entity.UserEntity;
import com.resustainability.aakri.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserEntity findByPhoneNumber(String phoneNumber) {
        return loginRepository.findByPhoneNumber(phoneNumber);
    }

	@Override
	public List<UserEntity> findAll() {
		return loginRepository.findAll();
	} 

}
