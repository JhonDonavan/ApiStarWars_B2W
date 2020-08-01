package com.starwars.b2w.exceptions;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TimeOutException extends RuntimeException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 624456139407296769L;
	@NonNull
	private String id;
	private String message;

}
