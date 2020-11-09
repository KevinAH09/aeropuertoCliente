/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import java.util.Date;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;


/**
 *
 * @author Bosco
 */

public class AvionesZonasDTO {
    private Long id;
    private Date fechaIngreso;
    private ZonasDTO zona;
    private AvionesDTO avion;

    public AvionesZonasDTO() {
        avion = new AvionesDTO();
        zona = new ZonasDTO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public ZonasDTO getZona() {
        return zona;
    }

    public void setZona(ZonasDTO zona) {
        this.zona = zona;
    }

    public AvionesDTO getAvion() {
        return avion;
    }

    public void setAvion(AvionesDTO avion) {
        this.avion = avion;
    }
    
    
}
