package com.burrobuie.cardmania.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.burrobuie.cardmania.validators.IUniqueEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(value = {
		"authorities", 
		"username", 
		"accountNonExpired", 
		"accountNonLocked",
		"credentialsNonExpired"
})
@Data
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    
    private String firstName;
    private String lastName;
    
    @NotBlank(message = "Email is mandatory")
    @Column(unique=true)
    //@IUniqueEmail
    private String email;
    
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;
    private boolean enabled;
    private boolean tokenExpired;
 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : getRoles()){
	        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
	    }
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}    

	@PrePersist @PreUpdate 
	private void prepare(){
        this.email = this.email.toLowerCase();
    }
}
