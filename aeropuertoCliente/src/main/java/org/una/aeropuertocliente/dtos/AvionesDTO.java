/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import org.una.aeropuertocliente.dtos.AerolineasDTO;



/**
 *
 * @author Bosco
 */

public class AvionesDTO {
    private Long id;
    private String matricula;
    private String tipoAvion;
    private String horasVuelo;
    private AerolineasDTO aerolineaId;
    private boolean estado;

    public AvionesDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipoAvion() {
        return tipoAvion;
    }

    public void setTipoAvion(String tipoAvion) {
        this.tipoAvion = tipoAvion;
    }

    public String getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(String horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    public AerolineasDTO getAerolineaId() {
        return aerolineaId;
    }

    public void setAerolineaId(AerolineasDTO aerolineaId) {
        this.aerolineaId = aerolineaId;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "AvionesDTO{" + "id=" + id + ", matricula=" + matricula + ", tipoAvion=" + tipoAvion + ", horasVuelo=" + horasVuelo + ", aerolineaId=" + aerolineaId + ", estado=" + estado + '}';
    }
    
    
}
