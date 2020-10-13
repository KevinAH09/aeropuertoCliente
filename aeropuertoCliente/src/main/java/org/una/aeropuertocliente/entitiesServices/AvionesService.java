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
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class AvionesService {
    public static List<AvionesDTO> allAviones(){
        
        List<AvionesDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<AvionesDTO>) Conection.listFromConnection("avion/",AvionesDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static AvionesDTO idAvion(Long id){
        
        AvionesDTO dTO = new AvionesDTO();
        try {
            dTO = (AvionesDTO) Conection.oneConnection("avion/"+id,new TypeToken<AvionesDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createAvion(AvionesDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("avion/",update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateAvion(AvionesDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("avion/"+create.getId(),AvionesDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
}
