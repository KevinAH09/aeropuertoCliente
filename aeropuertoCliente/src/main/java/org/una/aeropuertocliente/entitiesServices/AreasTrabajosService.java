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
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class AreasTrabajosService {
     public static List<AreasTrabajosDTO> allAreasTrabajos(){
        
        List<AreasTrabajosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<AreasTrabajosDTO>) Conection.listFromConnection("areaTrabajo/",AreasTrabajosDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static AreasTrabajosDTO idAreaTrabajo(Long id){
        
        AreasTrabajosDTO dTO = new AreasTrabajosDTO();
        try {
            dTO = (AreasTrabajosDTO) Conection.oneConnection("areaTrabajo/"+id,new TypeToken<AreasTrabajosDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createAreaTrabajo(AreasTrabajosDTO updateAreasTrabajos){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("areaTrabajo/",updateAreasTrabajos);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateAreaTrabajo(AreasTrabajosDTO createAreaTrabajo){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("areaTrabajo/"+createAreaTrabajo.getId(),AreasTrabajosDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
}
