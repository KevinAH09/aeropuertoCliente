/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Bosco
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VuelosDTO {
    private Long id;
    private String origen;
    private String destino;
    private Date fechaInicio;
    private Date fechaFinal;
    private boolean estado;
    private AvionesDTO avionId;
    private BitacorasVuelosDTO bitacoraVueloId;
}
