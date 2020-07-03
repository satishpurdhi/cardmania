package com.burrobuie.cardmania.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.burrobuie.cardmania.models.User;

public interface IUserRepository extends JpaRepository<User, UUID> {

	User findByEmail(final String email);
	
}
