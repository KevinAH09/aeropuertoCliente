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
    public static List<DetallesControlesGastosDTO> allDetallesControlesGastos(){
        
        List<DetallesControlesGastosDTO> listsDTOs = new ArrayList<>();
        try {
            listsDTOs = (List<DetallesControlesGastosDTO>) Conection.listFromConnection("detalleControlGasto/",DetallesControlesGastosDTO.class);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listsDTOs;
    }
    public static DetallesControlesGastosDTO idDetalleControlGasto(Long id){
        
        DetallesControlesGastosDTO dTO = new DetallesControlesGastosDTO();
        try {
            dTO = (DetallesControlesGastosDTO) Conection.oneConnection("detalleControlGasto/"+id,new TypeToken<DetallesControlesGastosDTO>() {}.getType());
            
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dTO;
    }
    public static int createDetalleControlGasto(DetallesControlesGastosDTO update){
        int codeResponse=0;
        try {
            codeResponse = Conection.createObjectToConnection("detalleControlGasto/",update);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
    public static int updateDetalleControlGasto(DetallesControlesGastosDTO create){
        int codeResponse=0;
        try {
            codeResponse = Conection.updateObjectToConnection("detalleControlGasto/"+create.getId(),DetallesControlesGastosDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }
}
