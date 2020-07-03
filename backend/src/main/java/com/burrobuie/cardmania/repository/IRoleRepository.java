package com.burrobuie.cardmania.repository;

import java.util.UUID;

import com.burrobuie.cardmania.models.Role;
import com.burrobuie.cardmania.models.Role.Name;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, UUID> {

	Role findByName(Name name);
	
}
