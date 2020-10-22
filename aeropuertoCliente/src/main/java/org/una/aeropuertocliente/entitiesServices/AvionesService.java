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
            listsDTOs = (List<AvionesDTO>) Conection.listFromConnection("avion/",new TypeToken<ArrayList<AvionesDTO>>() {}.getType());
            
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
    public static AvionesDTO matriculaUnicaAvion(String matricula){
        
        AvionesDTO dTO = new AvionesDTO();
        try {
            dTO = (AvionesDTO) Conection.oneConnection("avion/matriculaUnica/"+matricula,new TypeToken<AvionesDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createAvion(AvionesDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("avion/",create);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateAvion(AvionesDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("avion/"+update.getId(),update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    
    public static List<AvionesDTO> TipoAvion(String nombre) {

        List<AvionesDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<AvionesDTO>) Conection.listFromConnection("avion/tipo_avion/" +nombre, new TypeToken<ArrayList<AvionesDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    public static List<AvionesDTO> matricula(String codigo) {

        List<AvionesDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<AvionesDTO>) Conection.listFromConnection("avion/matricula/" +codigo, new TypeToken<ArrayList<AvionesDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    public static List<AvionesDTO> aerolinea(Long id) {

        List<AvionesDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<AvionesDTO>) Conection.listFromConnection("avion/aerolinea/" +id, new TypeToken<ArrayList<AvionesDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    public static List<AvionesDTO> estado(boolean estado) {

        List<AvionesDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<AvionesDTO>) Conection.listFromConnection("avion/estado/" +estado, new TypeToken<ArrayList<AvionesDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
    public static List<AvionesDTO> aerolineaMatricula(Long id,String matricula) {

        List<AvionesDTO> dtos = new ArrayList<>();
        try {
            dtos = (List<AvionesDTO>) Conection.listFromConnection("avion/aerolinea/"+id+"/matricula/"+matricula, new TypeToken<ArrayList<AvionesDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtos;
    }
}
