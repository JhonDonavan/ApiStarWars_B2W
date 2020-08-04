package com.starwars.b2w.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.b2w.model.Film;
import com.starwars.b2w.model.Planet;
import com.starwars.b2w.repositories.PlanetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetControllerIntegrationTests {
	@Autowired
	private PlanetRepository planetRepository;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mock;
													
	private static final String PLANET_NOT_FOUND =  "aaaaaaaaaaaaaaaaaaaaaaaa";
	
	
	@Before
	public void setup() {
		mock = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	//ok
	@Test
	public void getAllPlanets() throws Exception {
		
		String url = "/api/planets/";
		
		mock.perform(get(url))
			.andExpect(status().isOk());
		
	}
	
	//ok
	@Test
	public void getPlanetByIdNotFound() throws Exception {
		
		String url = "/api/planets/".concat(PLANET_NOT_FOUND);
		
		mock.perform(get(url))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message", containsString("cannot be found")))
			.andExpect(jsonPath("$.code", is(equalTo(404))));

	}
	
	
	//ok
	@Test
	public void getPlanetById() throws Exception {

		Planet randomPlanet = getRandomPlanet();
		
		String url = "/api/planets/".concat(randomPlanet.getId());
		
		mock.perform(get(url))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(equalTo(randomPlanet.getId()))))
			.andExpect(jsonPath("$.name", is(equalTo(randomPlanet.getName()))))
			.andExpect(jsonPath("$.climate", is(equalTo(randomPlanet.getClimate()))))
			.andExpect(jsonPath("$.terrain", is(equalTo(randomPlanet.getTerrain()))));
	}
	
	//ok
	@Test
	public void getPlanetsByName() throws Exception {
		
		Planet randomPlanet = getRandomPlanet();
		
		String url = "/api/planets/name/".concat(randomPlanet.getName());
		
		mock.perform(get(url))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(randomPlanet.getName())));
		
	}
		
	//ok
	@Test
	public void createPlanet() throws Exception {
	
		String url = "/api/planets/";
		
		var film1 = new Film();
		var film2 = new Film();
		
		film1.setTitle("Teste de filme");
		film2.setTitle("Teste de filme 2");
		
		List<Film> films = Stream.of(film1, film2).collect(Collectors.toList());
		
		mock.perform(post(url)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(new Planet(null, "TESTE ", "TESTE", "TESTE", films, null))))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(notNullValue())));
		
	}
	
	//ok
	@Test
	public void createPlanetConsumingStarWarsApi() throws Exception {
	
		String url = "/api/planets/";
				
		mock.perform(post(url)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(new Planet(null, "Eriadu ", "Eriadu", "Eriadu", null, null))))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(notNullValue())));
	}
	
	
	//ok
	@Test
	public void deletePlanet() throws Exception {
		
		Planet planetRandon = getRandomPlanet();
		
		String url = "/api/planets/"+planetRandon.getId();
	
		mock.perform(delete(url))
				.andExpect(status().isNoContent());
	}
	
	
	
	@Test
	public void createPlanetFailNullFields() throws Exception {
	
		String url = "/api/planets/";
		
		mock.perform(post(url)
			.contentType(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(new Planet(null, "Eriadu", null, null, null, null))))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error", containsString("The planet's climate cannot be null or empty")))
			.andExpect(jsonPath("$.error", containsString("The planet's terrain cannot be null or empty.")));
	}
	
		
	private Planet getRandomPlanet() {
		List<Planet> allPlanets = planetRepository.findAll();
		
		Random r = new Random();
		int result = r.nextInt(allPlanets.size()-1) + 1;
		
		return allPlanets.get(result);
	}
	
		
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
