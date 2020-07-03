package com.burrobuie.cardmania.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.burrobuie.cardmania.models.Mailing;
import com.burrobuie.cardmania.service.MailingService;

@RestController
@RequestMapping("/api/mailing")
public class MailingController {

	@Autowired
	private MailingService mailingService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
    public void create(@Valid @RequestBody Mailing mailing) {
		mailingService.save(mailing);
	}
	
}
