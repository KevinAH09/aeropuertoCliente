/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.apiForex;

/**
 *
 * @author colo7
 */
public class Tipomodena {

    Object rates;
    String code;

    public Object getRates() {
        return rates;
    }

    public void setRates(Object rates) {
        this.rates = rates;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double valorMoneda() {
        String valor ="";
        if ("200".equals(this.code)) {
            for (int i = 14; i < this.rates.toString().length(); i++) {
                if (this.rates.toString().charAt(i) == ',') {
                    break;
                }
                valor += this.rates.toString().charAt(i);
            }
           return Double.valueOf(valor);
        }
        return 0;
    }

    public Tipomodena() {
    }

    @Override
    public String toString() {
        return rates.toString();
    }

}
