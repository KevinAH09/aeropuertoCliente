/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import java.util.Date;
import org.una.aeropuertocliente.dtos.UsuariosDTO;


/**
 *
 * @author colo7
 */


public class RegistrosAccionesDTO {
    
    private Long id;
    private UsuariosDTO usuarioId;
    private String accion;
    private Date fechaRegistro;

    public RegistrosAccionesDTO() {
    }

    public RegistrosAccionesDTO(UsuariosDTO usuarioId, String accion, Date fechaRegistro) {
        this.usuarioId = usuarioId;
        this.accion = accion;
        this.fechaRegistro = fechaRegistro;
    }
   
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuariosDTO getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UsuariosDTO usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    


}
