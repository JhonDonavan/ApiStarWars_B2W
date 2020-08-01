package com.starwars.b2w.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.starwars.b2w.exceptions.PlanetNotFoundException;
import com.starwars.b2w.model.Film;
import com.starwars.b2w.model.Planet;
import com.starwars.b2w.services.PlanetService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetControllerTest {

	@Autowired
	private PlanetController planetController;

	@Autowired
	private PlanetService planetService;

	@Test
	public void getPlanetById() {

		Planet planetModel = getRandomPlanet();

		ResponseEntity<Planet> planetById = planetController.getPlanetById(planetModel.getId());

		assertThat(planetById.getBody().getId()).isEqualTo(planetModel.getId());
		assertThat(planetById.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	public void getAllPlanets() {

		ResponseEntity<List<Planet>> allPlanets = planetController.getAllPlanets();

		assertThat(allPlanets.getBody().size()).isNotEqualTo(0);
		assertThat(allPlanets.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getPlanetByIdNotFound() {

		try {
			planetController.getPlanetById("not found");
			assertTrue("Exception failed to be thrown.", false);
		} catch (PlanetNotFoundException e) {
			assertTrue("Exception thrown successfully.", true);
		}

	}

	@Test
	public void getPlanetsByName() {

		Planet randomPlanet = getRandomPlanet();

		ResponseEntity<List<Planet>> planetsByName = planetController.getPlanetsByName(randomPlanet.getName());

		assertThat(planetsByName.getStatusCode()).isEqualTo(HttpStatus.OK);

		assertThat(planetsByName.getBody().size()).isGreaterThan(0);

	}

	@Test
	public void createPlanetFilmManually() {

		Planet planet = new Planet();

		planet.setId("ID_New_Planet_Created");
		planet.setName("Eriadu");
		planet.setClimate("polluted");
		planet.setTerrain("cityscape");

		var film1 = new Film();
		var film2 = new Film();

		film1.setTitle("A New Hope");
		film2.setTitle("The Force Awakens");

		List<Film> films = Stream.of(film1, film2).collect(Collectors.toList());

		planet.setFilms(films);

		ResponseEntity<Planet> createPlanet = planetController.createPlanet(planet);

		assertThat(createPlanet.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		ResponseEntity<Planet> foundNewPlanet = planetController.getPlanetById(createPlanet.getBody().getId());

		assertThat(foundNewPlanet.getBody().getId()).isEqualTo(planet.getId());
		assertThat(foundNewPlanet.getBody().getName()).isEqualTo(planet.getName());
		assertThat(foundNewPlanet.getBody().getClimate()).isEqualTo(planet.getClimate());
		assertThat(foundNewPlanet.getBody().getTerrain()).isEqualTo(planet.getTerrain());
		assertThat(foundNewPlanet.getBody().getFilms().size()).isEqualTo(planet.getFilms().size());

	}

	@Test
	public void createPlanetConsumingStarWarsApi() {
		Planet planet = new Planet();

		planet.setId("ID_New_Planet_Created");
		planet.setName("Eriadu");
		planet.setClimate("polluted");
		planet.setTerrain("cityscape");

		ResponseEntity<Planet> createPlanet = planetController.createPlanet(planet);

		assertThat(createPlanet.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		ResponseEntity<Planet> foundNewPlanet = planetController.getPlanetById(createPlanet.getBody().getId());

		assertThat(foundNewPlanet.getBody().getId()).isEqualTo(planet.getId());
		assertThat(foundNewPlanet.getBody().getName()).isEqualTo(planet.getName());
		assertThat(foundNewPlanet.getBody().getClimate()).isEqualTo(planet.getClimate());
		assertThat(foundNewPlanet.getBody().getTerrain()).isEqualTo(planet.getTerrain());
		assertThat(foundNewPlanet.getBody().getFilms().size()).isEqualTo(planet.getFilms().size());
	}

	private Planet getRandomPlanet() {
		List<Planet> allPlanets = planetService.findAllPlanets();

		Random random = new Random();
		int result = random.nextInt(allPlanets.size());

		return allPlanets.get(result);
	}

}
