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
 * @author colo7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor 
@ToString
public class DetallesControlesGastosDTO {
   
    private Long id;
    private String observacion;
    private String tipoServicio;
    private Long duracion;
    private Long periodicidad;
    private String estado;
    private String estadoPago;
    private AreasTrabajosDTO areaTrabajoId;
    private DetallesControlesGastosDTO detalleControlGastoId;
    
    
}
