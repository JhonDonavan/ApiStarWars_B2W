package com.starwars.b2w.services;

import java.util.List;

import com.starwars.b2w.model.Planet;

public interface PlanetService {
	
	Planet savePlanet(Planet planet);
	
	List<Planet> findAllPlanets();
	
	Planet findPlanetById(String id);
	
	List<Planet> findPlanetByName(String name);
	
	void deletePlanet(String id);
	
	Planet updatePlanet(String id, Planet planet);

}
