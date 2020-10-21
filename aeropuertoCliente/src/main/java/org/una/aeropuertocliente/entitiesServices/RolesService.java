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
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class RolesService {
     public static List<RolesDTO> allRoles(){
        
        List<RolesDTO> listRolesDTO = new ArrayList<>();
        try {
            listRolesDTO = (List<RolesDTO>) Conection.listFromConnection("rol/",new TypeToken<ArrayList<RolesDTO>>() {
            }.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRolesDTO;
    }
     public static List<RolesDTO> estadoRoles(Boolean estado){
        
        List<RolesDTO> listRolesDTO = new ArrayList<>();
        try {
            listRolesDTO = (List<RolesDTO>) Conection.listFromConnection("rol/estado/"+estado,new TypeToken<ArrayList<RolesDTO>>() {
            }.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRolesDTO;
    }
     public static List<RolesDTO> codigoRoles(String codigo){
        
        List<RolesDTO> listRolesDTO = new ArrayList<>();
        try {
            listRolesDTO = (List<RolesDTO>) Conection.listFromConnection("rol/codigo/"+codigo,new TypeToken<ArrayList<RolesDTO>>() {
            }.getType());
            
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
    public static int createRol(RolesDTO createRol){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("rol/",createRol);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateRol(RolesDTO updateRol){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("rol/"+updateRol.getId(),updateRol);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
}
