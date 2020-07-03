package com.burrobuie.cardmania.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.burrobuie.cardmania.CardmaniaApplication;
import com.burrobuie.cardmania.jwt.JwtTokenUtil;
import com.burrobuie.cardmania.models.Card;
import com.burrobuie.cardmania.models.Occasion;
import com.burrobuie.cardmania.models.User;
import com.burrobuie.cardmania.repository.ICardRepository;
import com.burrobuie.cardmania.repository.IOccasionRepository;
import com.burrobuie.cardmania.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = CardmaniaApplication.class, 
	webEnvironment = WebEnvironment.RANDOM_PORT)
public class CardControllerIntegrationTest {

	//@Autowired
	//private MockMvc mockMvc;

	//@Autowired
	//private ObjectMapper objectMapper;

	@LocalServerPort
    private int port;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private ICardRepository cardRepository;

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IOccasionRepository occasionRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
    @Value("${jwt.http.request.header}")
    private String tokenHeader;
    
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
	@WithMockUser(authorities = {"ADMIN"})
	void createCard() throws Exception {
		final Card card = new Card();
		final User author = userRepository.findAll().get(0);
		card.setAuthor(author);
		final Occasion occasion = occasionRepository.findAll().get(0);
		card.setOccasion(occasion);
		byte[] image = new byte[] {1, 1, 1, 1};
		card.setImage(image);
		
		/* mockMvc.perform(post("/api/cards").contentType("application/json")
				.content(objectMapper.writeValueAsString(card)))
				.andExpect(status().isOk()); */
	    ResponseEntity<String> responseEntity = this.restTemplate
	            .postForEntity("http://localhost:" + port + "/api/cards", card, String.class);
	        assertEquals(201, responseEntity.getStatusCodeValue());

		List<Card> cards = cardRepository.findByOccasion(occasion);
		assertThat(cards.size()).isEqualTo(1);
		final Card cardEntity = cards.get(0);
		assertThat(cardEntity.getImage()).isEqualTo(image);
	}
}
