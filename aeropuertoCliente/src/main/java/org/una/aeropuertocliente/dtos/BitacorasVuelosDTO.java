/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;



/**
 *
 * @author Bosco
 */
public class BitacorasVuelosDTO {
    private Long id;
    private String tipoBitacora;
    private boolean cargaPasajero;
    private boolean cargaCombustible;
    private boolean horasVuelo;
    private boolean zonaDescarga;
    private boolean autorizacionTorreControl;

    public BitacorasVuelosDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoBitacora() {
        return tipoBitacora;
    }

    public void setTipoBitacora(String tipoBitacora) {
        this.tipoBitacora = tipoBitacora;
    }

    public boolean isCargaPasajero() {
        return cargaPasajero;
    }

    public void setCargaPasajero(boolean cargaPasajero) {
        this.cargaPasajero = cargaPasajero;
    }

    public boolean isCargaCombustible() {
        return cargaCombustible;
    }

    public void setCargaCombustible(boolean cargaCombustible) {
        this.cargaCombustible = cargaCombustible;
    }

    public boolean isHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(boolean horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    public boolean isZonaDescarga() {
        return zonaDescarga;
    }

    public void setZonaDescarga(boolean zonaDescarga) {
        this.zonaDescarga = zonaDescarga;
    }

    public boolean isAutorizacionTorreControl() {
        return autorizacionTorreControl;
    }

    public void setAutorizacionTorreControl(boolean autorizacionTorreControl) {
        this.autorizacionTorreControl = autorizacionTorreControl;
    }
    
    
}
