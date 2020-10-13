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
import org.una.aeropuertocliente.dtos.BitacorasVuelosDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class BitacorasVuelosService {
    public static List<BitacorasVuelosDTO> allBitacorasVuelos(){
        
        List<BitacorasVuelosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<BitacorasVuelosDTO>) Conection.listFromConnection("BitacoraVuelo/",BitacorasVuelosDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static BitacorasVuelosDTO idBitacoraVuelo(Long id){
        
        BitacorasVuelosDTO dTO = new BitacorasVuelosDTO();
        try {
            dTO = (BitacorasVuelosDTO) Conection.oneConnection("BitacoraVuelo/"+id,new TypeToken<BitacorasVuelosDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createBitacoraVuelo(BitacorasVuelosDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("BitacoraVuelo/",update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateBitacoraVuelo(BitacorasVuelosDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("BitacoraVuelo/"+create.getId(),BitacorasVuelosDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
}
