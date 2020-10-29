package com.navent.charlaWebFlux.service;

import com.navent.charlaWebFlux.model.ObjetoDeModelo;

import reactor.core.publisher.Mono;

public interface TestService {
	public Mono<ObjetoDeModelo> getProcessedObjetoDeModelo(int delayms);
}
