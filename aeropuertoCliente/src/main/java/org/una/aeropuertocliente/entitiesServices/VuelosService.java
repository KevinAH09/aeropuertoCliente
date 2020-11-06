/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.entitiesServices;

import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
            listsDTOs = (List<VuelosDTO>) Conection.listFromConnection("vuelo/",VuelosDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static VuelosDTO idVuelo(Long id){
        
        VuelosDTO dTO = new VuelosDTO();
        try {
            dTO = (VuelosDTO) Conection.oneConnection("vuelo/"+id,new TypeToken<VuelosDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createVuelo(VuelosDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("vuelo/",create);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateVuelo(VuelosDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("vuelo/"+update.getId(),update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
    public static List<VuelosDTO> Destino(String destino) {

        List<VuelosDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<VuelosDTO>) Conection.listFromConnection("vuelo/destino/" +destino, new TypeToken<ArrayList<VuelosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    public static List<VuelosDTO> Origen(String origen) {

        List<VuelosDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<VuelosDTO>) Conection.listFromConnection("vuelo/origen/" +origen, new TypeToken<ArrayList<VuelosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    
    public static List<VuelosDTO> estado(boolean estado) {

        List<VuelosDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<VuelosDTO>) Conection.listFromConnection("vuelo/estado/" +estado, new TypeToken<ArrayList<VuelosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    
    public static List<VuelosDTO> vuelos(Long id) {

        List<VuelosDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<VuelosDTO>) Conection.listFromConnection("vuelo/avion/" +id, new TypeToken<ArrayList<VuelosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    
    public static List<VuelosDTO> FechaInicio(String fecha) {

        List<VuelosDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<VuelosDTO>) Conection.listFromConnection("vuelo/fechaInicio/" +fecha, new TypeToken<ArrayList<VuelosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    
    
}
