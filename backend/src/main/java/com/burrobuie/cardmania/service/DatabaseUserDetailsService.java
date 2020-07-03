package com.burrobuie.cardmania.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.burrobuie.cardmania.models.Role;
import com.burrobuie.cardmania.models.User;
import com.burrobuie.cardmania.repository.IUserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
	                         throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
	    		user.getPassword(), user.getAuthorities());
	}
	  
}
