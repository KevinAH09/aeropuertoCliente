/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.entitiesServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class UsuariosService {
    
    
    public static List<UsuariosDTO> allUsuarios(){
        
        List<UsuariosDTO> listUsuariosDTOs = new ArrayList<>();
        try {
            listUsuariosDTOs = (List<UsuariosDTO>) Conection.listFromConnection("Usuario/",UsuariosDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsuariosDTOs;
    }
    public static int createUsuario(UsuariosDTO createUsuario){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("Usuario/",createUsuario);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateUsuario(UsuariosDTO createUsuario){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("Usuario/"+createUsuario.getId(),createUsuario);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
}
