/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.entitiesServices;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import org.una.aeropuertocliente.dtos.AuthenticationRequest;
import org.una.aeropuertocliente.dtos.AuthenticationResponse;
import org.una.aeropuertocliente.sharedService.Conection;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 *
 * @author colo7
 */
public class LoginService {
    
    
    public static AuthenticationResponse login(AuthenticationRequest authenticationRequest){
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        try {
            authenticationResponse = (AuthenticationResponse) Conection.LoginConexion("login/login", authenticationRequest);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return authenticationResponse;
    }

    
}