/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.apiForex;

import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author colo7
 */
public class TiposMonedasServices {
    /*Euro (EUR) - Highest Priority
UK Pound Sterling (GBP)
Australian Dollar (AUD)
New Zealand Dollar (NZD)
Unites States Dollar (USD)
Canadian Dollar (CAD)
Swiss Franc (CHF)
Japanese Yen (JPY) */

    public static Tipomodena valorMonedaDolarVSColon() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("USDCRC", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
    public static Tipomodena valorMonedaDolarVSEuros() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("USDEUR", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
    public static Tipomodena valorMonedaDolarVSLibraEsterlina() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("USDGBP", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
    public static Tipomodena valorMonedaDolarVSAustraliaDolar() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("USDAUD", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
    public static Tipomodena valorMonedaDolarVSNuevaZelandaDolar() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("USDNZD", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
    
    public static Tipomodena valorMonedaDolarVSCanadaDolar() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("USDCAD", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
    
    public static Tipomodena valorMonedaDolarVSFrancoSuizo() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("USDCHF", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
    
    public static Tipomodena valorMonedaDolarVSYenJapones() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("USDJPY", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
    
    
}
