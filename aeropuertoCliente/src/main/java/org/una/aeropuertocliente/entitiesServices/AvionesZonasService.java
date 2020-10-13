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
import org.una.aeropuertocliente.dtos.AvionesZonasDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class AvionesZonasService {
    public static List<AvionesZonasDTO> allAvionesZonas(){
        
        List<AvionesZonasDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<AvionesZonasDTO>) Conection.listFromConnection("AvionZona/",AvionesZonasDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static AvionesZonasDTO idAvionZona(Long id){
        
        AvionesZonasDTO dTO = new AvionesZonasDTO();
        try {
            dTO = (AvionesZonasDTO) Conection.oneConnection("AvionZona/"+id,new TypeToken<AvionesZonasDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createAvionZona(AvionesZonasDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("AvionZona/",update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateAvionZona(AvionesZonasDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("AvionZona/"+create.getId(),AvionesZonasDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
}
