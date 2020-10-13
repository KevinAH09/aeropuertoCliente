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
import org.una.aeropuertocliente.dtos.VuelosDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class VuelosService {
    public static List<VuelosDTO> allVuelos(){
        
        List<VuelosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<VuelosDTO>) Conection.listFromConnection("Vuelo/",VuelosDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static VuelosDTO idVuelo(Long id){
        
        VuelosDTO dTO = new VuelosDTO();
        try {
            dTO = (VuelosDTO) Conection.oneConnection("Vuelo/"+id,new TypeToken<VuelosDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createVuelo(VuelosDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("Vuelo/",update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateVuelo(VuelosDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("Vuelo/"+create.getId(),VuelosDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
}
