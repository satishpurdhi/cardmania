package com.burrobuie.cardmania.models;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

import lombok.Data;

@Data
@Entity
@Table(name = "Roles")
public class Role {
  
	public enum Name {
	    USER,
	    ADMIN
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
 
    @Column(unique=true)
    @Enumerated(EnumType.STRING)
    private Name name;
 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;   
}
