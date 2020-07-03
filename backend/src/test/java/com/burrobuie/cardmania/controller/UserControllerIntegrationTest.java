package com.burrobuie.cardmania.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.burrobuie.cardmania.CardmaniaApplication;
import com.burrobuie.cardmania.jwt.JwtTokenUtil;
import com.burrobuie.cardmania.models.Role;
import com.burrobuie.cardmania.models.Role.Name;
import com.burrobuie.cardmania.models.User;
import com.burrobuie.cardmania.repository.IRoleRepository;
import com.burrobuie.cardmania.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@SpringBootTest(classes = CardmaniaApplication.class, 
	webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private ObjectMapper objectMapper;
	
    @Value("${jwt.http.request.header}")
    private String tokenHeader;
    
	@LocalServerPort
    private int port;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private IUserRepository userRepository;
   
	@Autowired
	private IRoleRepository roleRepository;
	
	@BeforeEach
	void setup() {
		UserDetails user = (UserDetails) userRepository.findAll().get(0);
		final String token = jwtTokenUtil.generateToken(user);
		restTemplate.getRestTemplate().setInterceptors(
		        Collections.singletonList((request, body, execution) -> {
		            request.getHeaders()
		                    .add(this.tokenHeader, "Bearer " + token);
		            return execution.execute(request, body);
		        }));
	}
	
	@Test
	@WithMockUser("me@example.com")
	void registrationWorksThroughAllLayers() throws Exception {
		User user = new User();
		final String email = "newuser@test.com";
		final String firstName = "first";
		final String lastName = "last";
		user.setEmail(email);
		user.setEnabled(true);
		user.setFirstName(firstName);
		user.setLastName(lastName);		
		user.setPassword("password");
		final HashSet<Role> roles = new HashSet<Role>();
		final Role role = roleRepository.findByName(Name.USER);
		roles.add(role);
		user.setRoles(roles);
		
		ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/api/users", user, String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    
        final User createdUser = userRepository.findByEmail(email);
        assertNotNull(createdUser);
	}

	@Test
	@WithMockUser("me@example.com")
	void registerWithNonUniqueEmail() throws Exception {
		User user = new User();
		final String email = "me@example.com";
		final String firstName = "first";
		final String lastName = "last";
		user.setEmail(email);
		user.setEnabled(true);
		user.setFirstName(firstName);
		user.setLastName(lastName);		
		user.setPassword("password");
		final HashSet<Role> roles = new HashSet<Role>();
		final Role role = roleRepository.findByName(Name.USER);
		roles.add(role);
		user.setRoles(roles);
		
		ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/api/users", user, String.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Failed", responseEntity.getBody());
	}

	//@Test
	@WithMockUser(username = "me@example.com")
	void updateEndpointForMyself() throws Exception {
		final String email = "me@example.com";
		final User user = userRepository.findByEmail(email);
		final String firstName = "newfirst";
		user.setFirstName(firstName);
		final HashSet<Role> roles = new HashSet<Role>();
		final Role role = roleRepository.findByName(Name.USER);
		roles.add(role);
		user.setRoles(roles);
		
		// Create HttpEntity
		final String userAsJson = objectMapper.writeValueAsString(user);
		
		// Create URL (for eg: localhost:8080/api/users/1)
		final URI url = new URI("http://localhost:" + port + "/api/users/" + user.getId());

		ResponseEntity<User> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, getPostRequestHeaders(userAsJson), User.class);
		
		assertEquals(200, responseEntity.getStatusCodeValue());
		final User updatedUser = responseEntity.getBody();
        assertEquals(firstName, updatedUser.getFirstName());
	}

	
	private HttpEntity getPostRequestHeaders(String jsonPostBody) {
        List acceptTypes = new ArrayList();
        acceptTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(acceptTypes);

        return new HttpEntity(jsonPostBody, reqHeaders);
    }
}
