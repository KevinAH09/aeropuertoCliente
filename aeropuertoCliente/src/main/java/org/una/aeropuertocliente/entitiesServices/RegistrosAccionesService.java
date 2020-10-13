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
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class RegistrosAccionesService {
     public static List<RegistrosAccionesDTO> allRegistrosAcciones(){
        
        List<RegistrosAccionesDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<RegistrosAccionesDTO>) Conection.listFromConnection("registroAccion/",RegistrosAccionesDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static RegistrosAccionesDTO idRegistroAccion(Long id){
        
        RegistrosAccionesDTO dTO = new RegistrosAccionesDTO();
        try {
            dTO = (RegistrosAccionesDTO) Conection.oneConnection("registroAccion/"+id,new TypeToken<RegistrosAccionesDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createRegistroAccion(RegistrosAccionesDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("registroAccion/",update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateRegistroAccion(RegistrosAccionesDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("registroAccion/"+create.getId(),RegistrosAccionesDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
}
