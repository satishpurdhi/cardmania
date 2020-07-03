package com.burrobuie.cardmania.service;

import java.util.UUID;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.burrobuie.cardmania.models.Role;
import com.burrobuie.cardmania.models.Role.Name;
import com.burrobuie.cardmania.models.User;
import com.burrobuie.cardmania.repository.IRoleRepository;
import com.burrobuie.cardmania.repository.IUserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;

	public User create(User user) {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    final Role userRole = roleRepository.findByName(Name.USER);
	    final Set<Role> userRoles = new HashSet<Role>();
	    userRoles.add(userRole);
	    user.setRoles(userRoles);
	    return userRepository.save(user);
	}
	
	public User update(UUID id, User updatedUser) {
	    return userRepository.findById(id)
	    	      .map(User -> {
	    	        User.setFirstName(updatedUser.getFirstName());
	    	        User.setLastName(updatedUser.getLastName());
	    	        User.setEmail(updatedUser.getEmail());
	    	        return userRepository.save(User);
	    	      })
	    	      .orElse(null);
	}
	
	public List<User> find() {
		return userRepository.findAll();
	}
	
	public User findById(UUID id) {
		return userRepository.findById(id).orElse(null);
	}
}
