/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

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
public class BitacorasVuelosDTO {
    private Long id;
    private String tipoBitacora;
    private boolean cargaPasajero;
    private boolean cargaCombustible;
    private boolean horasVuelo;
    private boolean zonaDescarga;
    private boolean autorizacionTorreControl;
}
