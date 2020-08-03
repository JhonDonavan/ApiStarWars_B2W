package com.starwars.b2w.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseErrorBuild {

	private final String message;
	private final Integer code;
	private final String status;
	private final String error;

}
