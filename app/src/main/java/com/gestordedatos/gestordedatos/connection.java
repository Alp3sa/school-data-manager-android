package com.gestordedatos.gestordedatos;

import android.app.AlertDialog;
import android.content.Context;
import android.database.SQLException;
import android.icu.util.Output;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gestordedatos.gestordedatos.pojos.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

public class connection extends AsyncTask<String,Void,User> {
  AlertDialog dialog;
  Context context;

  public connection(Context context){
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
    JSONObject json;
    User validation = new User(-2,null,null);
    String username = arg0[0];
    String password = arg0[1];

    try{
      String link = "http://192.168.1.37/gestorDatos/connection.php";
      //192.168.1.35 IPv4
      URL url = new URL(link);
      HttpURLConnection client = (HttpURLConnection) url.openConnection();

      if(!isServerUp(client)){
        validation = new User(-1,null,null);
      }
      else {
        //System.out.println("APACHE STATUS: "+isApacheRunning);

        client = (HttpURLConnection) url.openConnection();
        client.setRequestMethod("POST");
        //client.setDoInput(true);
        client.setDoOutput(true);

        //OutputStream ops = client.getOutputStream();
        OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());

        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&&" +
                URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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

        Object jsonObject = json.getInt("0");
        validation = new User(json.getInt("0"), json.getString("1"), json.getString("2"));

        r.close();
        ips.close();
        client.disconnect();
      }
    }
    catch (SQLException e) {
      validation = new User(-1,null,null);
      Log.i("Connection",e.getMessage());//Error
    }
    catch (FileNotFoundException e) {
      Log.i("Connection",e.getMessage());//Error
      e.printStackTrace();
    }
    catch(MalformedURLException e){
      Log.i("Connection",e.getMessage());//Error
    }
    catch(IOException e){
      Log.i("Connection",e.getMessage());//Error de conección
    }
    catch(Exception e){
      Log.i("Connection",e.getMessage());//Error de conección
    }

    return validation;
  }
}