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
import org.una.aeropuertocliente.dtos.DetallesControlesGastosDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class DetallesControlesGastosService {

    public static List<DetallesControlesGastosDTO> allDetallesControlesGastos() {

        List<DetallesControlesGastosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<DetallesControlesGastosDTO>) Conection.listFromConnection("detalleControlGasto/", new TypeToken<ArrayList<DetallesControlesGastosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }

    public static DetallesControlesGastosDTO idDetalleControlGasto(Long id) {

        DetallesControlesGastosDTO dTO = new DetallesControlesGastosDTO();
        try {
            dTO = (DetallesControlesGastosDTO) Conection.oneConnection("detalleControlGasto/" + id, new TypeToken<DetallesControlesGastosDTO>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
 public static DetallesControlesGastosDTO createDetalleControlGasto(DetallesControlesGastosDTO create) {
        int codeResponse = 0;
        DetallesControlesGastosDTO detalle = new DetallesControlesGastosDTO();
        try {
            detalle = (DetallesControlesGastosDTO) Conection.createObjectToConnectionReturnObject("detalleControlGasto/", create, new TypeToken<DetallesControlesGastosDTO>() {
            }.getType());
        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detalle;
    }
//    public static int createDetalleControlGasto(DetallesControlesGastosDTO create) {
//        int codeResponse = 0;
//        try {
//            codeResponse = Conection.createObjectToConnection("detalleControlGasto/", create);
//        } catch (IOException ex) {
//            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return codeResponse;
//    }

    public static int updateDetalleControlGasto(DetallesControlesGastosDTO update) {
        int codeResponse = 0;
        try {
            codeResponse = Conection.updateObjectToConnection("detalleControlGasto/" + update.getId(), update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
}
