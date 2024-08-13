package com.ecommerce.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Role;
import com.ecommerce.model.Users;
import com.ecommerce.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<Users> optionalUsers=usersRepository.findUsersByUsername(username);
		
		if(optionalUsers.isEmpty()) {
			return null;
		}
		Users users=optionalUsers.get();
		Set<Role> roleSet=users.getRoles();
		Set<GrantedAuthority> authorities=roleSet.stream().map(
				role->new SimpleGrantedAuthority(role.getName()))
		        .collect(Collectors.toSet());
		
		return new User(username,users.getPassword(),authorities);
	}
	
	public Users createUsers(Users users) {
       
        users.setPassword(passwordEncoder.encode(users.getPassword()));
         usersRepository.save(users);
        return users;
    }

}

