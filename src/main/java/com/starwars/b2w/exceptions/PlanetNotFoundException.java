package com.starwars.b2w.exceptions;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PlanetNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5604689864625422951L;

	@NonNull
	private String id;
	private String message;

}
