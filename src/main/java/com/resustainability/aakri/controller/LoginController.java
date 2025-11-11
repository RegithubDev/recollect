package com.resustainability.aakri.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.resustainability.aakri.common.JwtUtil;
import com.resustainability.aakri.entity.UserEntity;
import com.resustainability.aakri.service.LoginService;

@RestController
@RequestMapping("/recollect/v1")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/users")
    public List<UserEntity> findAll() {
        return loginService.findAll();
    }

    
    @PostMapping("/get-customer-token")
    public ResponseEntity<?> getCustomerToken(@RequestBody Map<String, String> request) {
    	Map<String, Object> response = new HashMap<>();
    	try {
	        String phoneNumber = request.get("phoneNumber");
	        if (phoneNumber == null || phoneNumber.isEmpty()) {
	            return ResponseEntity.badRequest().body(Map.of("message", "phoneNumber required"));
	        }
	
	        UserEntity user = loginService.findByPhoneNumber(phoneNumber);
	        if (user == null) {
	            return ResponseEntity.status(404).body(Map.of("message", "User not found"));
	        }
	
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("user_id", user.getId());
	        claims.put("database", "kerala_db");
	        claims.put("state", "Kerala");
	
	        String accessToken = JwtUtil.generateAccessToken(claims);
	        String refreshToken = JwtUtil.generateRefreshToken(claims);
	
	     
	        response.put("message", "Success");
	        response.put("is_existing", true);
	        response.put("is_active", user.isActive());
	        response.put("access_token", accessToken);
	        response.put("refresh_token", refreshToken);
	        response.put("full_name", user.getFullName());
	        response.put("email", user.getEmail());
	        response.put("user_type", user.getUserType());
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        return ResponseEntity.ok(response);
    }

}
