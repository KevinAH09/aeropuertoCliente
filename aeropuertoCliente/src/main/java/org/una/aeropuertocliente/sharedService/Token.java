/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.sharedService;

import org.una.aeropuertocliente.dtos.AuthenticationResponse;

/**
 *
 * @author colo7
 */
public class Token {
    private static AuthenticationResponse unicaInstancia = new AuthenticationResponse();

    private Token() {
    }
    
    public static AuthenticationResponse getInstance() {
        return unicaInstancia;
    }
    public static void setInstance( AuthenticationResponse instancia) {
        unicaInstancia = instancia;
    }
     
    
}
