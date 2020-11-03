/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.entitiesServices;

import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.una.aeropuertocliente.sharedService.Conection;

/**
 *
 * @author colo7
 */
public class ReportesService {
      public static void reporteGastosMant(String ini,String fin) {
           String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/gastosMant/fechaini/fechafin/"+ini+"/"+fin);
            System.out.println(jasper);
        byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;
            try {
                bta = new ByteArrayInputStream(repor);
                ois = new ObjectInputStream(bta);
                JasperPrint jp = (JasperPrint) ois.readObject();
                JasperViewer jv = new JasperViewer(jp, false);
                jv.setTitle("Reporte vuelos");
                jv.setVisible(true);
                jv.show();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      public static void reporteGastosMantEmpresa(String ini,String fin,String empresa) {
           String jasper = "";
        try {
            jasper = Conection.oneConnectionReporte("reportes/gastosMant/fechaini/fechafin/empresa/"+ini+"/"+fin+"/"+empresa);
            System.out.println(jasper);
        byte[] repor = Base64.getDecoder().decode(jasper);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;
            try {
                bta = new ByteArrayInputStream(repor);
                ois = new ObjectInputStream(bta);
                JasperPrint jp = (JasperPrint) ois.readObject();
                JasperViewer jv = new JasperViewer(jp, false);
                jv.setTitle("Reporte vuelos");
                jv.setVisible(true);
                jv.show();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}
