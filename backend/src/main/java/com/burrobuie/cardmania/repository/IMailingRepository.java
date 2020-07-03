package com.burrobuie.cardmania.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.burrobuie.cardmania.models.Card;
import com.burrobuie.cardmania.models.Mailing;

public interface IMailingRepository extends JpaRepository<Mailing, UUID> {

}
