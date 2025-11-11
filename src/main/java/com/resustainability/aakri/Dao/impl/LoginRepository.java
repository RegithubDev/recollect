package com.resustainability.aakri.Dao.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resustainability.aakri.entity.UserEntity;

@Repository
public interface LoginRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByPhoneNumber(String phoneNumber);

	List<UserEntity> findAll(); 
}
