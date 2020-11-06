/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.entitiesServices;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class ReportesService {

    public static void reporteGastosMantFechas(String ini, String fin) {
        String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/gastosMant/fechaini/fechafin/" + ini + "/" + fin);
            byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;
            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de gastos de mantenimiento");
            jv.setVisible(true);
            jv.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void reporteGastosMantFechasEmpresa(String ini, String fin, String empresa) {
        String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/gastosMant/fechaini/fechafin/empresa/" + ini + "/" + fin + "/" + empresa);
            byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;

            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de gastos de mantenimiento");
            jv.setVisible(true);
            jv.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void reporteGastosMantFechasAreaTrabajo(String ini, String fin, String area) {
        String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/gastosMant/fechaini/fechafin/area/" + ini + "/" + fin + "/" + area);
            byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;
            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de gastos de mantenimiento");
            jv.setVisible(true);
            jv.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void reporteGastosMantFechasEstadoPago(String ini, String fin, String pago) {
        String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/gastosMant/fechaini/fechafin/pago/" + ini + "/" + fin + "/" + pago);
            byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;

            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de gastos de mantenimiento");
            jv.setVisible(true);
            jv.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void reporteAvionesFechas(String ini, String fin) {
        String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/aviones/fechaini/fechafin/" + ini + "/" + fin);
            byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;

            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de recorrido de vuelos");
            jv.setVisible(true);
            jv.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void reporteAvionesFechasZona(String ini, String fin, String zona) {
        String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/aviones/fechaini/fechafin/zona/" + ini + "/" + fin + "/" + zona);
            byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;

            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de recorrido de vuelos");
            jv.setVisible(true);
            jv.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void reporteAvionesFechasAerolinea(String ini, String fin, String aero) {
        String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/aviones/fechaini/fechafin/aerolinea/" + ini + "/" + fin + "/" + aero);
            byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;

            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de recorrido de vuelos");
            jv.setVisible(true);
            jv.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void reporteAvionesFechasAerolineaZona(String ini, String fin, String aero, String zona) {
        String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/aviones/fechaini/fechafin/zonaAerolinea/" + ini + "/" + fin + "/" + aero + "/" + zona);
            byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;

            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de recorrido de vuelos");
            jv.setVisible(true);
            jv.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
