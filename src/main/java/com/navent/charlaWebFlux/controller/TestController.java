package com.navent.charlaWebFlux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.navent.charlaWebFlux.model.ControllerResponse;
import com.navent.charlaWebFlux.model.ObjetoDeModelo;
import com.navent.charlaWebFlux.service.TestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
@Slf4j
public class TestController {
	private final TestService service;

	@GetMapping("/test/{delayms}")
	public Mono<ControllerResponse<ObjetoDeModelo>> getTest(@PathVariable(name = "delayms", required = true) int delayms) {
		log.info("Entrando a getTest({})", delayms);

		// FUNCIONAL:
		return this.service.getProcessedObjetoDeModelo(delayms) // Solicito el objeto al sercvicio
				.map(TestController::generateControllerResponse); // Lo mapeo a ControllerResponse

//		// TRADICIONAL:
//	
//		// Solicito el objeto al servicio
//		Mono<ObjetoDeModelo> objetoDeModeloMono =  this.service.getProcessedObjetoDeModelo(delayms);
//		
//		// Lo mapeo a ControllerResponse
//		log.info("Solicitando mapear a ControllerResponse");
//		Mono<ControllerResponse<ObjetoDeModelo>> controllerRespMono = objetoDeModeloMono.map(TestController::generateControllerResponse);
//
//		log.info("Saliendo de getTest()");
//		return controllerRespMono;
	}

	private static ControllerResponse<ObjetoDeModelo> generateControllerResponse(ObjetoDeModelo payload) {
		log.info("Mapeando a ControllerResponse: {}", payload);
		return new ControllerResponse<ObjetoDeModelo>(payload);
	}
}
