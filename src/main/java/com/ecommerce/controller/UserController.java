package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Users;
import com.ecommerce.service.UserDetailsServiceImpl;

@RestController
public class UserController {
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@PostMapping("/createUsers")
    public ResponseEntity<Users> createPerson(@RequestBody Users createPersonRequest){
         Users users = userDetailsServiceImpl.createUsers(createPersonRequest);
        return ResponseEntity.ok(users);
    }

}
