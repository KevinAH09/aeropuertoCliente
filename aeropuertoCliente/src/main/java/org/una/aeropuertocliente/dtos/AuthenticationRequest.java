/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;



/**
 *
 * @author colo7
 */
public class AuthenticationRequest {

    private String cedula;
    private String contrasena;

    public AuthenticationRequest(String cedula, String contrasena) {
        this.cedula = cedula;
        this.contrasena = contrasena;
    }
    
}
