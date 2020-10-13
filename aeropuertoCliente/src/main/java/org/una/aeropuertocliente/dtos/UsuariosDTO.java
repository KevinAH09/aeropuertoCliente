/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import java.util.Date;

import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.RolesDTO;

/**
 *
 * @author colo7
 */

public class UsuariosDTO {

    private Long id;
    private String nombreCompleto;
    private String contrasenaEncriptada;
    private String cedula;
    private String correo;
    private boolean estado;
    private RolesDTO rolId;
    private AreasTrabajosDTO areaTrabajoId;
    private Date fechaRegistro;
    private boolean jefeId;

    public UsuariosDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getContrasenaEncriptada() {
        return contrasenaEncriptada;
    }

    public void setContrasenaEncriptada(String contrasenaEncriptada) {
        this.contrasenaEncriptada = contrasenaEncriptada;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public RolesDTO getRolId() {
        return rolId;
    }

    public void setRolId(RolesDTO rolId) {
        this.rolId = rolId;
    }

    public AreasTrabajosDTO getAreaTrabajoId() {
        return areaTrabajoId;
    }

    public void setAreaTrabajoId(AreasTrabajosDTO areaTrabajoId) {
        this.areaTrabajoId = areaTrabajoId;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isJefeId() {
        return jefeId;
    }

    public void setJefeId(boolean jefeId) {
        this.jefeId = jefeId;
    }

    
    
}

