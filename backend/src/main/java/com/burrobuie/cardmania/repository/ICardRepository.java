package com.burrobuie.cardmania.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.burrobuie.cardmania.models.Card;
import com.burrobuie.cardmania.models.Occasion;
import com.burrobuie.cardmania.models.User;

@Repository
public interface ICardRepository extends JpaRepository<Card, UUID> {

	List<Card> findByOccasion(Occasion occasion);
	
	List<Card> findByAuthor(User author);
}
