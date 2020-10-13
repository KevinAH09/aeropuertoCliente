/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import org.una.aeropuertocliente.dtos.UsuariosDTO;



/**
 *
 * @author colo7
 */

public class AuthenticationResponse {

    private String jwt;
    private UsuariosDTO usuario;

    public AuthenticationResponse() {
    }
    

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }
    
    

}
