package com.navent.charlaWebFlux.rest.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaServicioExterno {
	private Integer idRespuesta;
	private String mensaje;
	private String datoNoRequerido;
}
