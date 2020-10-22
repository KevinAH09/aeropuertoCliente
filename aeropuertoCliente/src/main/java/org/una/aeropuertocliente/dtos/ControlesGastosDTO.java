/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.dtos;

import java.util.Date;

/**
 *
 * @author colo7
 */
public class ControlesGastosDTO {

    private Long id;
    private String responsable;
    private String empresaContratante;
    private String numeroContrato;
    private Date fechaRegistro;
    private DetallesControlesGastosDTO detalleControlGastoId;

    public DetallesControlesGastosDTO getDetalleControlGastoId() {
        return detalleControlGastoId;
    }

    public void setDetalleControlGastoId(DetallesControlesGastosDTO detalleControlGastoId) {
        this.detalleControlGastoId = detalleControlGastoId;
    }

    public ControlesGastosDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getEmpresaContratante() {
        return empresaContratante;
    }

    public void setEmpresaContratante(String empresaContratante) {
        this.empresaContratante = empresaContratante;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
