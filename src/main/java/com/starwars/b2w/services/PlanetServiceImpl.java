package com.starwars.b2w.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.starwars.b2w.exceptions.PlanetNotFoundException;
import com.starwars.b2w.model.Film;
import com.starwars.b2w.model.Planet;
import com.starwars.b2w.model.ResponsePlanet;
import com.starwars.b2w.repositories.PlanetRepository;
import com.starwars.b2w.utils.Constants;

@Service
public class PlanetServiceImpl implements PlanetService {

	@Autowired
	private PlanetRepository planetRepository;

	@Override
	public Planet savePlanet(Planet planet) {
		BeanUtils.copyProperties(planet, planet, Constants.ID_FIELD);

		if (planet.getFilms() == null) {
			planet = fimlsAppearences(planet);
		} else {
			planet.setFilmAppearances(planet.getFilms().size());
		}

		// DO HATEOAS

		return planetRepository.save(planet);

	}

	@Override
	public List<Planet> findAllPlanets() {
		return planetRepository.findAll();
	}

	@Override
	public Planet findPlanetById(String id) {
		return planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException(id));
		 
	}

	@Override
	public List<Planet> findPlanetByName(String name) {
		
		List<Planet> AllPlanetForfound = planetRepository.findByName(name);
		
		if(AllPlanetForfound.isEmpty()) {
			throw new PlanetNotFoundException(name);
		}
		
		//TODO HATEOAS!
		return AllPlanetForfound;
	}

	@Override
	public void deletePlanet(String id) {
		planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException(id));
		planetRepository.deleteById(id);
	}

	@Override
	public Planet updatePlanet(String id, Planet planet) {
		Planet returnedPlanet = planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException(id));
		BeanUtils.copyProperties(planet, returnedPlanet, id);
		return planetRepository.save(returnedPlanet);
	}

	private Planet fimlsAppearences(Planet planet) throws RestClientException {
		List<String> filmsURI = new ArrayList<String>();
		List<Film> titleFilmsReturned = new ArrayList<Film>();

		try {
			ResponseEntity<ResponsePlanet> responsePlanet = responsePlanet(Constants.PATH_PLANET + planet.getName());

			responsePlanet.getBody().getResults().forEach(film -> filmsURI.addAll(film.getFilms()));

			if (responsePlanet.getBody().getCount() > 0) {
				filmsURI.forEach(films -> titleFilmsReturned.add(returnTitleFilms(films)));
				planet.setFilmAppearances(titleFilmsReturned.size());
				planet.setFilms(titleFilmsReturned);
				return planet;
			}
			return planet;
		} catch (RestClientException e) {
			e.printStackTrace();
			return planet;
		}
	}
	

	private ResponseEntity<ResponsePlanet> responsePlanet(String path) throws RestClientException {
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		
		ResponseEntity<ResponsePlanet> responsePlanet = restTemplate.getForEntity(path, ResponsePlanet.class);
		
		return responsePlanet;
	}
	
	
	private Film returnTitleFilms(String path) {
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		ResponseEntity<Film> response = restTemplate.getForEntity(path, Film.class);
		return response.getBody();
	}
	
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout)
				.build();
		CloseableHttpClient client = HttpClientBuilder.create()
				.setDefaultRequestConfig(config)
				.build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}
}
