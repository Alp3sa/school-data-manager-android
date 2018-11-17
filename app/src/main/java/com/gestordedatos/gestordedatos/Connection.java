package com.gestordedatos.gestordedatos;

import android.app.AlertDialog;
import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;

import com.gestordedatos.gestordedatos.pojos.User;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Connection extends AsyncTask<String,Void,User> {
  AlertDialog dialog;
  Context context;

  public Connection(Context context){
    this.context=context;
  }

  @Override
  protected void onPreExecute(){}
  @Override
  protected void onPostExecute(User status){}

  public boolean isServerUp(HttpURLConnection client) {
    boolean isUp = false;
    try {
      client.setRequestMethod("POST");
      client.setDoOutput(true);
      client.setConnectTimeout(500);
      OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());
      isUp = true;
      out.close();
    }
    catch (IOException e) {
      // Server is down
    }
    catch (Exception e) {
      // Server is down
    }
    return isUp;
  }

  @Override
  protected User doInBackground(String... arg0) {

      if(Globals.connectionUsers.equals("login")){
          JSONObject json;
          User validation = new User(-2, null, null);
          String username = arg0[0];
          String password = arg0[1];

          try {
              //String link = "http://192.168.1.37/gestorDatos/connection.php";
              String link = "http://www.alp3sa.info/pupluns/connectionDataManager.php";
              //192.168.1.35 IPv4
              URL url = new URL(link);
              HttpURLConnection client = (HttpURLConnection) url.openConnection();

              if (!isServerUp(client)) {
                  System.out.println("Servidor MySQL caído");
                  validation = new User(-1, null, null);
              } else {
                  client = (HttpURLConnection) url.openConnection();
                  client.setRequestMethod("POST");
                  //client.setDoInput(true);
                  client.setDoOutput(true);

                  //OutputStream ops = client.getOutputStream();
                  OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());

                  String data = URLEncoder.encode("order", "UTF-8")+"="+URLEncoder.encode("login", "UTF-8")+
                          "&&"+URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+
                          "&&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

                  out.write(data);
                  out.flush();
                  out.close();


                /*BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                System.out.println("hola1");
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"-";
                System.out.println("hola1");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();
    */
                  InputStream ips = new BufferedInputStream(client.getInputStream());

                  // Read the BufferedInputStream
                  BufferedReader r = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

                  //String line = r.readLine();

                  json = new JSONObject(r.readLine());

                  validation = new User(json.getInt("0"), json.getString("1"), json.getString("2"));

                  r.close();
                  ips.close();
                  client.disconnect();
              }
          } catch (SQLException e) {
              validation = new User(-1, null, null);
              Log.i("Connection", e.getMessage());//Error
          } catch (FileNotFoundException e) {
              Log.i("Connection", e.getMessage());//Error
              e.printStackTrace();
          } catch (MalformedURLException e) {
              Log.i("Connection", e.getMessage());//Error
          } catch (IOException e) {
              Log.i("Connection", e.getMessage());//Error de conección
          } catch (Exception e) {
              Log.i("Connection", e.getMessage());//Error de conección
          }

          return validation;
      }
      else if(Globals.connectionUsers.equals("signUp")){
          JSONObject json;
          User validation = new User(-2, null, null, null, null, null, null, null,null,null);
          String username = arg0[0];
          String name = arg0[1];
          String surname = arg0[2];
          String lastname = arg0[3];
          String age = arg0[4];
          String dni = arg0[5];
          String memberType = arg0[6];
          String gender = arg0[7];
          String password = arg0[8];
            System.out.println("check "+username+" "+name+" "+surname+" "+lastname+" "+age+" "+dni+" "+memberType+" "+gender+" "+password);
          try {
              String link = "http://www.alp3sa.info/pupluns/connectionDataManager.php";

              URL url = new URL(link);
              HttpURLConnection client = (HttpURLConnection) url.openConnection();

              if (!isServerUp(client)) {
                  System.out.println("Servidor caído");
                  validation = new User(-1, null, null);
              } else {
                  client = (HttpURLConnection) url.openConnection();
                  client.setRequestMethod("POST");

                  client.setDoOutput(true);

                  OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());

                  String data = URLEncoder.encode("order", "UTF-8")+"="+URLEncoder.encode("signUp", "UTF-8")+
                          "&&"+URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+
                          "&&"+URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name, "UTF-8")+
                          "&&"+URLEncoder.encode("surname", "UTF-8")+"="+URLEncoder.encode(surname, "UTF-8")+
                          "&&"+URLEncoder.encode("lastname", "UTF-8")+"="+URLEncoder.encode(lastname, "UTF-8")+
                          "&&"+URLEncoder.encode("age", "UTF-8")+"="+URLEncoder.encode(age, "UTF-8")+
                          "&&"+URLEncoder.encode("dni", "UTF-8")+"="+URLEncoder.encode(dni, "UTF-8")+
                          "&&"+URLEncoder.encode("memberType", "UTF-8")+"="+URLEncoder.encode(memberType, "UTF-8")+
                          "&&"+URLEncoder.encode("gender", "UTF-8")+"="+URLEncoder.encode(gender, "UTF-8")+
                          "&&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

                  out.write(data);
                  out.flush();
                  out.close();
                  System.out.println("check: "+data);
                  InputStream ips = new BufferedInputStream(client.getInputStream());

                  // Read the BufferedInputStream
                  BufferedReader r = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

                  //String line = r.readLine();
                  json = new JSONObject(r.readLine());
                  validation = new User(json.getInt("0"), json.getString("1"), json.getString("2"));
                  r.close();
                  ips.close();
                  client.disconnect();
              }
          } catch (SQLException e) {
              validation = new User(-1, null, null);
              Log.i("Connection error 1:", e.getMessage());//Error
          } catch (FileNotFoundException e) {
              Log.i("Connection error 2:", e.getMessage());//Error
              e.printStackTrace();
          } catch (MalformedURLException e) {
              Log.i("Connection error 3:", e.getMessage());//Error
          } catch (IOException e) {
              Log.i("Connection error 4:", e.getMessage());//Error de conección
          } catch (Exception e) {
              validation = new User(-2, null, null);
              Log.i("Connection error 5:", e.getMessage());//Error de conección
          }

          return validation;
      }

      Globals.connectionUsers=null;

      return null;
  }
}