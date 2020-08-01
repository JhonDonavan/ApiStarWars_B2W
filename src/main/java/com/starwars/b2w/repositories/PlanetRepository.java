package com.starwars.b2w.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.starwars.b2w.model.Planet;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String>{
	
	List<Planet> findByName(String name);

}
