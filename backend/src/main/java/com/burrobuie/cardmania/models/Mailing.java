package com.burrobuie.cardmania.models;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "Mailings")
public class Mailing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
	@ManyToOne
	@NotNull
	private Card card;
	
	private String message;
	
	//@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
	private java.sql.Timestamp creationDate;
	
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Address senderAddress;
    
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Address recipientAddress;
}
