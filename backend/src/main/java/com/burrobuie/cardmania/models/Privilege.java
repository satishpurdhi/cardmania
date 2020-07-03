package com.burrobuie.cardmania.models;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Privileges")
public class Privilege {

	public enum PrivilegeName {
		SUPER_ADMIN,
		USER
	}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(unique=true)
    @Enumerated(EnumType.STRING)
    private PrivilegeName name;
 
    //@ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    //private Collection<Role> roles;
}
