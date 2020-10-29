package com.navent.charlaWebFlux.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navent.charlaWebFlux.model.ObjetoDeModelo;
import com.navent.charlaWebFlux.rest.client.TestClient;
import com.navent.charlaWebFlux.rest.client.model.OtraRespuestaServicioExterno;
import com.navent.charlaWebFlux.rest.client.model.RespuestaServicioExterno;
import com.navent.charlaWebFlux.service.TestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class TestServiceImpl implements TestService {
	private final TestClient client;

	@Override
	public Mono<ObjetoDeModelo> getProcessedObjetoDeModelo(int delayms) {
		log.info("Entrando a processDataFromExternalRest({})", delayms);

		// FUNCIONAL:
		return client.getFromExternalREST(delayms) // Solicito el objeto al servicio externo
			.map(RespuestaServicioExterno::getIdRespuesta) // Me quedo sólo con el id
			.flatMap(client::getFromAnotherREST) // Obtengo otro objeto a partir del id del anterior 
			.map(TestServiceImpl::mapearAObjetoDeModelo) // Mapeo a ObjetoDeModelo
		;


//		// TRADICIONAL:
//		
//		// Obtengo el mono con la respuesta del servicio externo
//		Mono<RespuestaServicioExterno> respExternaMono = client.getFromExternalREST(delayms);
//
//		//Mapeo a Integer
//		log.info("Solicito mapear a Integer");
//		Mono<Integer> idRespuestaMono = respExternaMono.map(respExterna -> {
//			log.info("Mapeando a integer {}", respExterna);
//			return respExterna.getIdRespuesta();
//		});
//		
//		// Obtengo otro objeto a partir de id
//		log.info("Solicito obtener objeto de servicio 2");
//		Mono<OtraRespuestaServicioExterno> respExterna2 = idRespuestaMono.flatMap(idResp -> {
//			log.info("Obtengo objeto de servicio 2");
//			return client.getFromAnotherREST(idResp);
//		});
//
//		// Y mapeo a ObjetoDeModelo
//		log.info("Solicito mapear a ObjetoDeModelo");
//		Mono<ObjetoDeModelo> objetoDeModeloMono = respExterna2.map(TestServiceImpl::mapearAObjetoDeModelo);
//		
//		// Retorno el Mono con la cadena de acciones a realizar
//		log.info("Saliendo de processDataFromExternalRest()");
//		return objetoDeModeloMono;
	}
	
	private static ObjetoDeModelo mapearAObjetoDeModelo(OtraRespuestaServicioExterno respuesta) {
		log.info("Mapeando a ObjetoDeModelo: {}", respuesta);
		return new ObjetoDeModelo(respuesta.getIdAviso(), respuesta.getTitulo());
	}
}
