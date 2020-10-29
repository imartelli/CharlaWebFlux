package com.navent.charlaWebFlux.rest.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtraRespuestaServicioExterno {
	private Integer idAviso;
	private String titulo;
	private String descripcion;
}
