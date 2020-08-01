package com.starwars.b2w.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter  @JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePlanet {
	
	private int count;
	
	private String next;
	
	private String previous;
	
	private String msg;
	
	private List<ResultApiStarSars> results;

}
