package com.burrobuie.cardmania.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.burrobuie.cardmania.models.User;
import com.burrobuie.cardmania.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> find() {
	    List<User> foundUsers = userService.find();
	    return ResponseEntity.ok(foundUsers);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("principal.id = #id")
	public ResponseEntity<User> read(@PathVariable("id") UUID id) {
	    User foundUser = userService.findById(id);
	    if (foundUser == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(foundUser);
	    }
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void create(@Valid @RequestBody User user) {
		userService.create(user);
    }
	
	@PutMapping("/{id}")
	@PreAuthorize("authentication.principal.id == #id")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable UUID id) {
	    final User updatedUser = userService.update(id, user);
	    if (updatedUser == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(updatedUser);
	    }
	}

}
