/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.entitiesServices;

import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.una.aeropuertocliente.dtos.RolesDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class RolesService {
     public static List<RolesDTO> allRoles(){
        
        List<RolesDTO> listRolesDTO = new ArrayList<>();
        try {
            listRolesDTO = (List<RolesDTO>) Conection.listFromConnection("rol/",RolesDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRolesDTO;
    }
    public static RolesDTO idRole(Long id){
        
        RolesDTO rolesDTO = new RolesDTO();
        try {
            rolesDTO = (RolesDTO) Conection.oneConnection("rol/"+id,new TypeToken<RolesDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rolesDTO;
    }
    public static int createRol(RolesDTO updateRol){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("rol/",updateRol);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateRol(RolesDTO createRol){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("rol/"+createRol.getId(),RolesDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
}
