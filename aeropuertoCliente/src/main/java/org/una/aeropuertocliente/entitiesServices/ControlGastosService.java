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
import org.una.aeropuertocliente.dtos.ControlesGastosDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class ControlGastosService {

    public static List<ControlesGastosDTO> allControlesGastos() {

        List<ControlesGastosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ControlesGastosDTO>) Conection.listFromConnection("controlGasto/", new TypeToken<ArrayList<ControlesGastosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }

    public static List<ControlesGastosDTO> empresaControlesGastos(String empresa) {

        List<ControlesGastosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ControlesGastosDTO>) Conection.listFromConnection("controlGasto/empresa/" + empresa, new TypeToken<ArrayList<ControlesGastosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }

    public static List<ControlesGastosDTO> contratoControlesGastos(String contrato) {

        List<ControlesGastosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ControlesGastosDTO>) Conection.listFromConnection("controlGasto/numeroContrato/" + contrato, new TypeToken<ArrayList<ControlesGastosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }

    public static List<ControlesGastosDTO> tipoControlesGastos(String tipo) {

        List<ControlesGastosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ControlesGastosDTO>) Conection.listFromConnection("controlGasto/tipo/" + tipo, new TypeToken<ArrayList<ControlesGastosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static List<ControlesGastosDTO> estadoControlesGastos(String estado) {

        List<ControlesGastosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ControlesGastosDTO>) Conection.listFromConnection("controlGasto/estado/" + estado, new TypeToken<ArrayList<ControlesGastosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }

    public static List<ControlesGastosDTO> fechaControlesGastos(String ini, String fin) {

        List<ControlesGastosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<ControlesGastosDTO>) Conection.listFromConnection("controlGasto/fechaRegistroBetween/" + ini + "/and/" + fin, new TypeToken<ArrayList<ControlesGastosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }

    public static ControlesGastosDTO idControlGasto(Long id) {

        ControlesGastosDTO dTO = new ControlesGastosDTO();
        try {
            dTO = (ControlesGastosDTO) Conection.oneConnection("controlGasto/" + id, new TypeToken<ControlesGastosDTO>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }

    public static int createControlGasto(ControlesGastosDTO create) {
        int codeResponse = 0;
        try {
            codeResponse = Conection.createObjectToConnection("controlGasto/", create);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }

    public static int updateControlGasto(ControlesGastosDTO update) {
        int codeResponse = 0;
        try {
            codeResponse = Conection.updateObjectToConnection("controlGasto/" + update.getId(), update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }

}
