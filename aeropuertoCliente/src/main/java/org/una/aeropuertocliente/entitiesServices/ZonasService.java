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
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class ZonasService {
    public static List<ZonasDTO> allZonas(){
        
        List<ZonasDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ZonasDTO>) Conection.listFromConnection("zona/",ZonasDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static ZonasDTO idZona(Long id){
        
        ZonasDTO dTO = new ZonasDTO();
        try {
            dTO = (ZonasDTO) Conection.oneConnection("zona/"+id,new TypeToken<ZonasDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createZona(ZonasDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("zona/",update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateZona(ZonasDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("zona/"+create.getId(),ZonasDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
}
