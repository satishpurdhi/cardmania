package com.burrobuie.cardmania.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.burrobuie.cardmania.models.Occasion;

public interface IOccasionRepository extends JpaRepository<Occasion, UUID> {

}
