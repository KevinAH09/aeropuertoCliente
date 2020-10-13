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
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class AerolineasService {
    public static List<AerolineasDTO> allAerolineas(){
        
        List<AerolineasDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<AerolineasDTO>) Conection.listFromConnection("Aerolinea/",AerolineasDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static AerolineasDTO idAerolinea(Long id){
        
        AerolineasDTO dTO = new AerolineasDTO();
        try {
            dTO = (AerolineasDTO) Conection.oneConnection("Aerolinea/"+id,new TypeToken<AerolineasDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createAerolinea(AerolineasDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("Aerolinea/",update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateAerolinea(AerolineasDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("Aerolinea/"+create.getId(),AerolineasDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
}
