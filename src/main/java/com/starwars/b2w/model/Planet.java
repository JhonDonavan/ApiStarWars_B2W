package com.starwars.b2w.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

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

	@MongoId(value = FieldType.OBJECT_ID)
	private String id;

	@NotBlank(message = "{message.planet.name.notblank}")
	private String name;

	@NotBlank(message = "{message.planet.climate.notblank}")
	private String climate;

	@NotBlank(message = "{message.planet.terrain.notblank}")
	private String terrain;

	private List<Film> films;
	
	@ApiModelProperty(access="hidden", hidden=true)
	private Integer filmAppearances;


}
