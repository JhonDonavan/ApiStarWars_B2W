package com.starwars.b2w.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "planets")
public class PlanetModel {
	
	private String id;
	
	@NotBlank(message = "{msg.planet.name.notblank}")
	private String name;
	
	@NotBlank(message = "{msg.planet.climate.notblank}")
	private String terrain;
	
	@NotBlank(message = "{msg.planet.terrain.notblank}")
	private String climate;
	
	private Integer filmAppearances;
	
	private List<Film> films;
	
}
