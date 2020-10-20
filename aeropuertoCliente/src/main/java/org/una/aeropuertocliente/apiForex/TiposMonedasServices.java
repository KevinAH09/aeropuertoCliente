/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.apiForex;

import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author colo7
 */
public class TiposMonedasServices {

    public static Tipomodena valorMonedaDolar() {
             Tipomodena tipomodena = new Tipomodena();
        try {
            tipomodena = (Tipomodena) ConectionApiForex.listFromConnection("", new TypeToken<Tipomodena>() {
            }.getType());
            System.out.println(tipomodena.valorMoneda());
        } catch (IOException ex) {
            Logger.getLogger(TiposMonedasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipomodena;
    }
}
