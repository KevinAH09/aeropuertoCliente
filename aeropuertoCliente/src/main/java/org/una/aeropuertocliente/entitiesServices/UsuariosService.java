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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class UsuariosService {

    public static List<UsuariosDTO> allUsuarios() {

        List<UsuariosDTO> listUsuariosDTOs = new ArrayList<>();
        try {
            listUsuariosDTOs = (List<UsuariosDTO>) Conection.listFromConnection("usuario/", new TypeToken<ArrayList<UsuariosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsuariosDTOs;
    }

    public static UsuariosDTO idUsuario(Long id) {

        UsuariosDTO usuarioDTO = new UsuariosDTO();
        try {
            usuarioDTO = (UsuariosDTO) Conection.oneConnection("usuario/" + id, new TypeToken<UsuariosDTO>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarioDTO;
    }

    public static int createUsuario(UsuariosDTO createUsuario) {
        int codeResponse = 0;
        try {
            codeResponse = Conection.createObjectToConnection("usuario/", createUsuario);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }

    public static int updateUsuario(UsuariosDTO createUsuario) {
        int codeResponse = 0;
        try {
            codeResponse = Conection.updateObjectToConnection("usuario/" + createUsuario.getId(), createUsuario);
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codeResponse;
    }

    public static List<UsuariosDTO> estadoUsuarios(Boolean estado) {

        List<UsuariosDTO> listUsuariosDTOs = new ArrayList<>();
        try {
            listUsuariosDTOs = (List<UsuariosDTO>) Conection.listFromConnection("usuario/estado/" + estado, new TypeToken<ArrayList<UsuariosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsuariosDTOs;
    }

    public static List<UsuariosDTO> nombreUsuarios(String nombre) {

        List<UsuariosDTO> listUsuariosDTOs = new ArrayList<>();
        try {
            listUsuariosDTOs = (List<UsuariosDTO>) Conection.listFromConnection("usuario/nombre/" + nombre, new TypeToken<ArrayList<UsuariosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsuariosDTOs;
    }
    public static List<UsuariosDTO>rolUsuarios(Long id) {

        List<UsuariosDTO> listUsuariosDTOs = new ArrayList<>();
        try {
            listUsuariosDTOs = (List<UsuariosDTO>) Conection.listFromConnection("usuario/rol/" + id, new TypeToken<ArrayList<UsuariosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsuariosDTOs;
    }
    public static List<UsuariosDTO>areaTrabajoUsuarios(Long id) {

        List<UsuariosDTO> listUsuariosDTOs = new ArrayList<>();
        try {
            listUsuariosDTOs = (List<UsuariosDTO>) Conection.listFromConnection("usuario/areaTrabajo/" + id, new TypeToken<ArrayList<UsuariosDTO>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsuariosDTOs;
    }

    public static UsuariosDTO cedulaUsuarios(String cedula) {

        UsuariosDTO listUsuariosDTOs = new UsuariosDTO();
        try {
            listUsuariosDTOs = (UsuariosDTO) Conection.oneConnection("usuario/cedula/" + cedula, new TypeToken<UsuariosDTO>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsuariosDTOs;
    }

}
