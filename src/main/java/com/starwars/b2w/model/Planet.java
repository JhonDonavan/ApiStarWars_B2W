package com.starwars.b2w.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "planets")
public class Planet {

	@Id
	private String id;

	@NotBlank(message = "{msg.planet.name.notblank}")
	private String name;

	@NotBlank(message = "{msg.planet.climate.notblank}")
	private String climate;

	@NotBlank(message = "{msg.planet.terrain.notblank}")
	private String terrain;

	private List<Film> films;
	
	@ApiModelProperty(access="hidden", hidden=true)
	private Integer filmAppearances;


}
