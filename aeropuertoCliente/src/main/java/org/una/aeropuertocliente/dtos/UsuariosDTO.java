/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author colo7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor 
@ToString
public class UsuariosDTO {

    private Long id;
    private String nombreCompleto;
    private String contrasenaEncriptada;
    private String cedula;
    private String correo;
    private boolean estado;
    private RolesDTO rolId;
    private AreasTrabajosDTO areaTrabajoId;
    private Date fechaRegistro;
    private boolean jefeId;

    
    
}

