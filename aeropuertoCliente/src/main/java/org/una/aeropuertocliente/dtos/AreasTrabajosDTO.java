/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;



/**
 *
 * @author colo7
 */

public class AreasTrabajosDTO {
     
    
    
    private Long id;
    private String nombreAreaTrabajo;
    private String descripcion;
    private boolean estado;

    public AreasTrabajosDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAreaTrabajo() {
        return nombreAreaTrabajo;
    }

    public void setNombreAreaTrabajo(String nombreAreaTrabajo) {
        this.nombreAreaTrabajo = nombreAreaTrabajo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombreAreaTrabajo; //To change body of generated methods, choose Tools | Templates.
    }
    
   
    
}
