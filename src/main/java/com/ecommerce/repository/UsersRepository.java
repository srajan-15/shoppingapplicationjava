package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Optional<Users> findUsersByUsername(String username);

}