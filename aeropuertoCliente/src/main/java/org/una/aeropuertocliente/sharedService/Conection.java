/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.sharedService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.una.aeropuertocliente.dtos.AuthenticationResponse;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.utils.AppContext;

/**
 *
 * @author colo7
 */
public class Conection {

    private final static String urlBase = "http://localhost:8099/";

    public static <T> Object loginConexion(String urlstring, Object object) throws MalformedURLException, IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        StringBuilder response = new StringBuilder();
        Type type = new TypeToken<AuthenticationResponse>() {
        }.getType();
        URL url = new URL(urlBase + urlstring);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String data = gson.toJson(object);

        try ( OutputStream os = con.getOutputStream()) {
            byte[] input = data.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (Exception e) {
            return null;
        }
        if (con.getResponseCode() == 200) {
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {

                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return gson.fromJson(response.toString(), type);
            }
        }
        return null;
    }

    public static int createObjectToConnection(String urlstring, Object object) throws MalformedURLException, IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

        URL url = new URL(urlBase + urlstring);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "bearer " + Token.getInstance().getJwt());
        con.setDoOutput(true);

        String data = gson.toJson(object);

        try ( OutputStream os = con.getOutputStream()) {
            byte[] input = data.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (Exception e) {
            return 0;
        }
        if (con.getResponseCode() == 201) {
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

            }
        }
        return con.getResponseCode();

    }

    public static <T> Object createObjectToConnectionReturnObject(String urlstring, Object object,  Type listtype) throws MalformedURLException, IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

        URL url = new URL(urlBase + urlstring);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "bearer " + Token.getInstance().getJwt());
        con.setDoOutput(true);

        String data = gson.toJson(object);

        try ( OutputStream os = con.getOutputStream()) {
            byte[] input = data.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (Exception e) {
            return 0;
        }
        if (con.getResponseCode() == 201) {
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return gson.fromJson(response.toString(), listtype);

            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }

    }

    public static <T> Object listFromConnection(String urlstring, Type listtype) throws MalformedURLException, IOException {
        Gson gson = new Gson();
        URL url = new URL(urlBase + urlstring);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "bearer " + Token.getInstance().getJwt());
        if (con.getResponseCode() == 200) {
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return gson.fromJson(response.toString(), listtype);

            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> Object oneConnection(String urlstring, Type listtype) throws MalformedURLException, IOException {
        Gson gson = new Gson();
        URL url = new URL(urlBase + urlstring);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "bearer " + Token.getInstance().getJwt());
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return gson.fromJson(response.toString(), listtype);

        } catch (Exception e) {
            return null;
        }
    }
    public static <T> String oneConnectionReporte(String urlstring) throws MalformedURLException, IOException {
       
        URL url = new URL(urlBase + urlstring);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "bearer " + Token.getInstance().getJwt());
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            return response.toString();

        } catch (Exception e) {
            return null;
        }
    }

    public static int updateObjectToConnection(String urlstring, Object object) throws MalformedURLException, IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        URL url = new URL(urlBase + urlstring);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "bearer " + Token.getInstance().getJwt());
        con.setDoOutput(true);

        String data = gson.toJson(object);

        try ( OutputStream os = con.getOutputStream()) {
            byte[] input = data.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (Exception e) {
            return 0;
        }
        if (con.getResponseCode() == 200) {

            try ( BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

            }

        }
        return con.getResponseCode();
    }
}
