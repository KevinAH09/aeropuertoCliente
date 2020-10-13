/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.aeropuertocliente.dtos.AuthenticationRequest;
import org.una.aeropuertocliente.service.ConectionService;

/**
 *
 * @author colo7
 */
public class ConectionController {

    private final String urlstring = "http://localhost:8099/usuarios/";

    public ConectionController() {
    }

    public List<Object> getAll() throws InterruptedException, ExecutionException, IOException {
        return ConectionService.ListFromConnection(urlstring, Object.class);
    }

    public void add(Object object) throws InterruptedException, ExecutionException, IOException {
        ConectionService.ObjectToConnection(urlstring, object);
    }

    public Object Login(String cedula, String password) throws InterruptedException, ExecutionException, IOException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(cedula, password);
        Object o = ConectionService.ObjectLogin(urlstring+"login", authenticationRequest);
        return o;

    }
    public Object getId(String id)throws InterruptedException, ExecutionException, IOException {
        return ConectionService.FromConnectionID(urlstring, id,Object.class);
    }
//     public Object getCedulaPassword(String cedula,String pass)throws InterruptedException, ExecutionException, IOException {
//        return ConectionService.FromConnectionCedulaPassword(urlstring, cedula,pass,Object.class);
//    }
//    public Object getCedula(String cedula)throws InterruptedException, ExecutionException, IOException {
//        return ConectionService.FromConnectionCedula(urlstring+"cedula/", cedula,Object.class);
//    }
//     public int Update(Object usu)throws InterruptedException, ExecutionException, IOException {
//        return ConectionService.UpdateObjectToConnection(urlstring, usu.getId().toString(),usu);
//    }
    public static ConectionController getInstance() {
        return UsuariocontrollerHolder.INSTANCE;
    }

    private static class UsuariocontrollerHolder {

        private static final ConectionController INSTANCE = new ConectionController();
    }
}
