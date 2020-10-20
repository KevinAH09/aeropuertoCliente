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
            listsDTOs = (List<AerolineasDTO>) Conection.listFromConnection("aerolinea/",new TypeToken<ArrayList<AerolineasDTO>>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static AerolineasDTO idAerolinea(Long id){
        
        AerolineasDTO dTO = new AerolineasDTO();
        try {
            dTO = (AerolineasDTO) Conection.oneConnection("aerolinea/"+id,new TypeToken<AerolineasDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createAerolinea(AerolineasDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("aerolinea/",create);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateAerolinea(AerolineasDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("aerolinea/"+update.getId(),update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
    public static List<AerolineasDTO> estadoAerolinea(Boolean estado) {

        List<AerolineasDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<AerolineasDTO>) Conection.listFromConnection("aerolinea/estado/" +estado, new TypeToken<ArrayList<AerolineasDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    public static List<AerolineasDTO> nombreAerolinea(String nombreAeroliena) {

        List<AerolineasDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<AerolineasDTO>) Conection.listFromConnection("aerolinea/nombre_aerolinea/" +nombreAeroliena, new TypeToken<ArrayList<AerolineasDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    public static List<AerolineasDTO> nombreResponsable(String nombreResponsable) {

        List<AerolineasDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<AerolineasDTO>) Conection.listFromConnection("aerolinea/nombre_responsable/" +nombreResponsable, new TypeToken<ArrayList<AerolineasDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
}
