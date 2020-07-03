package com.burrobuie.cardmania.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.burrobuie.cardmania.models.Card;
import com.burrobuie.cardmania.models.Occasion;
import com.burrobuie.cardmania.repository.ICardRepository;
import com.burrobuie.cardmania.repository.IOccasionRepository;

@Service
@Transactional
public class CardService {

	@Autowired
	private ICardRepository cardRepository;

	@Autowired
	private IOccasionRepository occasionRepository;
	
	public Card save(Card card) {
		return cardRepository.save(card);
	}
	
	public Card update(UUID id, Card updatedCard) {
	    return cardRepository.findById(id)
	    	      .map(card -> {
	    	        card.setOccasion(updatedCard.getOccasion());
	    	        card.setImage(updatedCard.getImage());
	    	        return cardRepository.save(card);
	    	      })
	    	      .orElse(null);
	}
	
	public Card findById(UUID id) {
		return cardRepository.findById(id).orElse(null);
	}
	
	public List<Card> findByOccasion(final UUID occasionId) {
		final Occasion occasion = occasionRepository.findById(occasionId)
				.orElse(null);
		return cardRepository.findByOccasion(occasion);
	}
}
