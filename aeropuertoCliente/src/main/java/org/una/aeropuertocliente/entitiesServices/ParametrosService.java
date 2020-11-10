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
import org.una.aeropuertocliente.dtos.ParametrosDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class ParametrosService {

    public static List<ParametrosDTO> allParametros() {

        List<ParametrosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ParametrosDTO>) Conection.listFromConnection("parametro/", new TypeToken<ArrayList<ParametrosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }

    public static List<ParametrosDTO> estadoParametros(Boolean estado) {

        List<ParametrosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ParametrosDTO>) Conection.listFromConnection("parametro/estado/" + estado, new TypeToken<ArrayList<ParametrosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }

    public static ParametrosDTO nombreParametros(String nombre) {

        ParametrosDTO dto = new ParametrosDTO();
        try {
            dto = (ParametrosDTO) Conection.oneConnection("parametro/nombre_parametro/" + nombre, new TypeToken<ParametrosDTO>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dto;
    }

    public static ParametrosDTO idParametro(Long id) {

        ParametrosDTO dTO = new ParametrosDTO();
        try {
            dTO = (ParametrosDTO) Conection.oneConnection("parametro/" + id, new TypeToken<ParametrosDTO>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }

    public static int createParametro(ParametrosDTO create) {
        int codeResponse = 0;
        try {
            codeResponse = Conection.createObjectToConnection("parametro/", create);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }

    public static int updateParametro(ParametrosDTO update) {
        int codeResponse = 0;
        try {
            codeResponse = Conection.updateObjectToConnection("parametro/" + update.getId(), update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
}
