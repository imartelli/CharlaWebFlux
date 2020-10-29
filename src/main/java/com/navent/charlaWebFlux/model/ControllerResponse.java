package com.navent.charlaWebFlux.model;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class ControllerResponse<T> {
	private LocalDateTime date;
	private T payload;
	
	public ControllerResponse(T payload) {
		this.date = LocalDateTime.now();
		this.payload = payload;
	}
}
