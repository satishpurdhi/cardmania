package com.burrobuie.cardmania.controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.burrobuie.cardmania.models.Card;
import com.burrobuie.cardmania.models.User;
import com.burrobuie.cardmania.service.CardService;

@RestController
@RequestMapping("/api/cards")
public class CardController {

	@Autowired
	private CardService cardService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Card> read(@PathVariable("id") UUID id) {
	    Card foundCard = cardService.findById(id);
	    if (foundCard == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(foundCard);
	    }
	}

	@GetMapping("/occasion/{id}")
	public ResponseEntity<List<Card>> findByOccasion(@PathVariable("id") UUID occasionId) {
	    List<Card> foundCards = cardService.findByOccasion(occasionId);
	    return ResponseEntity.ok(foundCards);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody Card card, @AuthenticationPrincipal User loggedInUser) {
		card.setAuthor(loggedInUser);
		cardService.save(card);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Card> update(@RequestBody Card card, @PathVariable UUID id) {
	    final Card updatedCard = cardService.update(id, card);
	    if (updatedCard == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(updatedCard);
	    }
	}
	
}
