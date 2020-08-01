package com.starwars.b2w.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.b2w.model.Planet;
import com.starwars.b2w.services.PlanetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("REST API STARWARS PLANETS")
@RestController
@RequestMapping("api/planets")
public class PlanetController {
	
	@Autowired
	PlanetService planetService;
	
	
	@ApiOperation(value = "Creates a planet")
	@PostMapping("/")
	public ResponseEntity<Planet> createPlanet(@Valid @RequestBody Planet planet){
		return ResponseEntity.status(HttpStatus.CREATED).body(planetService.savePlanet(planet));
	}
	
	
	@ApiOperation(value = "search all planets")
	@GetMapping("/")
	public ResponseEntity<List<Planet>> getAllPlanets(){
		return ResponseEntity.status(HttpStatus.OK).body(planetService.findAllPlanets());
	}
	
	
	@ApiOperation(value = "Search for planets by name")
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Planet>> getPlanetsByName(@PathVariable String name){
		return ResponseEntity.status(HttpStatus.OK).body(planetService.findPlanetByName(name));
	}
	
	
	@ApiOperation(value = "Search planet by ID")
	@GetMapping("/{id}")
	public ResponseEntity<Planet> getPlanetById(@PathVariable String id){
		return ResponseEntity.status(HttpStatus.OK).body(planetService.findPlanetById(id));
	}
	
	
	@ApiOperation(value = "Excludes a planet")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePlanet(@PathVariable String id){
		planetService.deletePlanet(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	
	@ApiOperation(value = "Update a planet")
	@PutMapping("/{id}")
	public ResponseEntity<Planet> updatePlanet(@PathVariable String id, @Valid @RequestBody Planet planet){
		return ResponseEntity.status(HttpStatus.OK).body(planetService.updatePlanet(id, planet));
	}
			
}
