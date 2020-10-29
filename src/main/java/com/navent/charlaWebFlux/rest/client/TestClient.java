package com.navent.charlaWebFlux.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.navent.charlaWebFlux.rest.client.model.OtraRespuestaServicioExterno;
import com.navent.charlaWebFlux.rest.client.model.RespuestaServicioExterno;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TestClient {
	private final WebClient webClient;

	@Autowired
	public TestClient(Builder webClientBuilder) {
		super();
		this.webClient = webClientBuilder.baseUrl("http://localhost:8090")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString())
				.filters(exchangeFilterFunctions -> {
					exchangeFilterFunctions.add(logRequest());
					exchangeFilterFunctions.add(logResponse());
				}).build();
	}

	private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			if (log.isInfoEnabled()) {
				StringBuilder sb = new StringBuilder("Request: ").append(clientRequest.method()).append(" ")
						.append(clientRequest.url());
				log.info(sb.toString());
			}
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			if (log.isInfoEnabled()) {
				StringBuilder sb = new StringBuilder("Response: ").append("Status: ")
						.append(clientResponse.rawStatusCode());
				log.info(sb.toString());
			}
			return Mono.just(clientResponse);
		});
	}

	// Obtiene un objeto de un servicio externo
	public Mono<RespuestaServicioExterno> getFromExternalREST(int delayms) {
		log.info("Entrando a getFromExternalREST({})", delayms);

		Mono<RespuestaServicioExterno> responseMono = this.webClient
				.method(HttpMethod.GET)
				.uri("/test/" + delayms)
				.retrieve()
				.bodyToMono(RespuestaServicioExterno.class);

		log.info("Saliendo de getFromExternalREST()");
		return responseMono;
	}

	// Simula buscar otro objeto a partir del id
	public Mono<OtraRespuestaServicioExterno> getFromAnotherREST(Integer idRespuesta) {
		log.info("Entrando a getFromAnotherREST({})", idRespuesta);
		Mono<OtraRespuestaServicioExterno> responseMono = Mono
				.just(new OtraRespuestaServicioExterno(idRespuesta, "Objeto de servicio simulado", "Un Mensaje"));
		log.info("Saliendo de getFromAnotherREST()");
		return responseMono;
	}
}
