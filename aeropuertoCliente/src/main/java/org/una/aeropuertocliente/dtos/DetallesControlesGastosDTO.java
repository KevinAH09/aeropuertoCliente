/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;



/**
 *
 * @author colo7
 */

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

    public DetallesControlesGastosDTO() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    public Long getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Long periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public AreasTrabajosDTO getAreaTrabajoId() {
        return areaTrabajoId;
    }

    public void setAreaTrabajoId(AreasTrabajosDTO areaTrabajoId) {
        this.areaTrabajoId = areaTrabajoId;
    }

    public DetallesControlesGastosDTO getDetalleControlGastoId() {
        return detalleControlGastoId;
    }

    public void setDetalleControlGastoId(DetallesControlesGastosDTO detalleControlGastoId) {
        this.detalleControlGastoId = detalleControlGastoId;
    }
    
    
    
}
