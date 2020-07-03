package com.burrobuie.cardmania.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.burrobuie.cardmania.models.Mailing;
import com.burrobuie.cardmania.repository.IMailingRepository;

@Service
public class MailingService {

	@Autowired
	private IMailingRepository mailingRepository;
	
	public Mailing save(Mailing mailing) {
		return mailingRepository.save(mailing);
	}
	
}
