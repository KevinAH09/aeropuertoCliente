/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import java.util.Date;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.BitacorasVuelosDTO;


/**
 *
 * @author Bosco
 */

public class VuelosDTO {
    private Long id;
    private String origen;
    private String destino;
    private Date fechaInicio;
    private Date fechaFinal;
    private boolean estado;
    private AvionesDTO avionId;
    private BitacorasVuelosDTO bitacoraVueloId;

    public VuelosDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public AvionesDTO getAvionId() {
        return avionId;
    }

    public void setAvionId(AvionesDTO avionId) {
        this.avionId = avionId;
    }

    public BitacorasVuelosDTO getBitacoraVueloId() {
        return bitacoraVueloId;
    }

    public void setBitacoraVueloId(BitacorasVuelosDTO bitacoraVueloId) {
        this.bitacoraVueloId = bitacoraVueloId;
    }
    
    
}
