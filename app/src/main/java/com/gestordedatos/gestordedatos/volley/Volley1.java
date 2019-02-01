package com.gestordedatos.gestordedatos.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.gestordedatos.gestordedatos.application.AppController;
import com.gestordedatos.gestordedatos.Globals;
import com.gestordedatos.gestordedatos.pojos.Classroom;
import com.gestordedatos.gestordedatos.contentProvider.BitacoraProveedor;
import com.gestordedatos.gestordedatos.pojos.Enrollment;
import com.gestordedatos.gestordedatos.pojos.Subject;
import com.gestordedatos.gestordedatos.pojos.Tutorship;
import com.gestordedatos.gestordedatos.pojos.User;
import com.gestordedatos.gestordedatos.sync.Sincronizacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Volley1 {
    public static String ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
    //ContentResolver resolvedor;
    //RequestQueue queue;
    //Context context;

    //public CicloVolley(ContentResolver resolvedor, Context context) {
    //    this.resolvedor = resolvedor;
    //    //this.queue = Volley.newRequestQueue(context);
    //}

    public static void getAll(){
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        System.out.println("check GETALL: "+Globals.SERVER_TABLE_NAME+" "+Globals.LAST_TAB);
        String tag_json_obj = "getAllClassroom";
        if(Globals.SERVER_TABLE_NAME.equals("classroom")) {
            tag_json_obj = "getAllClassroom";
        }
        else if(Globals.SERVER_TABLE_NAME.equals("subject")) {
            tag_json_obj = "getAllSubject";
        }
        else if(Globals.SERVER_TABLE_NAME.equals("tutorship")) {
            tag_json_obj = "getAllTutorship";
        }
        System.out.println("check getAll: "+tag_json_obj);

        String url = ruta;
        // prepare the Request
        System.out.println("check uri: "+ruta);
        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        //Log.d("Response", response.toString());
                        Sincronizacion.realizarActualizacionesDelServidorUnaVezRecibidas(response);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        // add it to the RequestQueue
        //queue.add(getRequest);
        AppController.getInstance().addToRequestQueue(getRequest, tag_json_obj);
    }

    public static void addClassroom(Classroom classroom, final boolean conBitacora, final int idBitacora){
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        System.out.println("check addClassroom");
        String tag_json_obj = "addClassroom";
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", classroom.getID());
            jsonObject.put("classroomName", classroom.getClassroomName());
            jsonObject.put("details", classroom.getDetails());
            jsonObject.put("image", classroom.getImage_url());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateClassroom(Classroom classroom, final boolean conBitacora, final int idBitacora){
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        System.out.println("check updateClassroom");
        String tag_json_obj = "updateClassroom";
        String url = ruta + "/" + classroom.getID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", classroom.getID());
            jsonObject.put("classroomName", classroom.getClassroomName());
            jsonObject.put("details", classroom.getDetails());
            jsonObject.put("image", classroom.getImage_url());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(putRequest);
        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void delClassroom(int id, final boolean conBitacora, final int idBitacora){
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        System.out.println("check delClassroom");
        String tag_json_obj = "delClassroom";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }
        );
        //queue.add(delRequest);
        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }

    public static void addSubject(Subject subject, final boolean conBitacora, final int idBitacora){
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        System.out.println("check addSubject"+ruta);
        String tag_json_obj = "addSubject";
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", subject.getID());
            jsonObject.put("name", subject.getName());
            jsonObject.put("teacher", subject.getTeacher());
            jsonObject.put("classroom", subject.getClassroom());
            jsonObject.put("startTime", subject.getStartTime());
            jsonObject.put("endingTime", subject.getEndingTime());
            jsonObject.put("classroomID", subject.getClassroomID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateSubject(Subject subject, final boolean conBitacora, final int idBitacora){
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        System.out.println("check updateSubject");
        String tag_json_obj = "updateSubject";
        String url = ruta + "/" + subject.getID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", subject.getID());
            jsonObject.put("name", subject.getName());
            jsonObject.put("teacher", subject.getTeacher());
            jsonObject.put("classroom", subject.getClassroom());
            jsonObject.put("startTime", subject.getStartTime());
            jsonObject.put("endingTime", subject.getEndingTime());
            jsonObject.put("classroomID", subject.getClassroomID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(putRequest);
        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void delSubject(int id, final boolean conBitacora, final int idBitacora){
        System.out.println("check delSubject");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "delSubject";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }
        );
        //queue.add(delRequest);
        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }

    public static void addTutorship(Tutorship tutorship, final boolean conBitacora, final int idBitacora){
        System.out.println("check addTutorship");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "addTutorship";
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", tutorship.getID());
            jsonObject.put("name", tutorship.getName());
            jsonObject.put("classroom", tutorship.getClassroom());
            jsonObject.put("startTime", tutorship.getStartTime());
            jsonObject.put("endingTime", tutorship.getEndingTime());
            jsonObject.put("classroomID", tutorship.getClassroomID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateTutorship(Tutorship tutorship, final boolean conBitacora, final int idBitacora){
        System.out.println("check updateTutorship");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "updateTutorship";
        String url = ruta + "/" + tutorship.getID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", tutorship.getID());
            jsonObject.put("name", tutorship.getName());
            jsonObject.put("classroom", tutorship.getClassroom());
            jsonObject.put("startTime", tutorship.getStartTime());
            jsonObject.put("endingTime", tutorship.getEndingTime());
            jsonObject.put("classroomID", tutorship.getClassroomID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(putRequest);
        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void delTutorship(int id, final boolean conBitacora, final int idBitacora){
        System.out.println("check delTutorship");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "delTutorship";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }
        );
        //queue.add(delRequest);
        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }


    public static void addEnrollment(Enrollment enrollment, final boolean conBitacora, final int idBitacora){
        System.out.println("check addEnrollment");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "addEnrollment";
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", enrollment.getID());
            jsonObject.put("userID", enrollment.getUserID());
            jsonObject.put("subjectID", enrollment.getSubjectID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateEnrollment(Enrollment enrollment, final boolean conBitacora, final int idBitacora){
        System.out.println("check updateEnrollment");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "updateEnrollment";
        String url = ruta + "/" + enrollment.getID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", enrollment.getID());
            jsonObject.put("userID", enrollment.getUserID());
            jsonObject.put("subjectID", enrollment.getSubjectID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(putRequest);
        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void delEnrollment(int id, final boolean conBitacora, final int idBitacora){
        System.out.println("check delEnrollment");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "delEnrollment";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }
        );
        //queue.add(delRequest);
        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }

    public static void addUser(User user, final boolean conBitacora, final int idBitacora){
        System.out.println("check addUser");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "addUser";
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", user.getID());
            jsonObject.put("nombreUsuario", user.getNombreUsuario());
            jsonObject.put("nombre", user.getNombre());
            jsonObject.put("primerApellido", user.getPrimerApellido());
            jsonObject.put("segundoApellido", user.getSegundoApellido());
            jsonObject.put("edad", user.getEdad());
            jsonObject.put("dni", user.getDni());
            jsonObject.put("genero", user.getGenero());
            jsonObject.put("tipoDeMiembro", user.getTipoDeMiembro());
            jsonObject.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateUser(User user, final boolean conBitacora, final int idBitacora){
        System.out.println("check updateUser");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "updateUser";
        String url = ruta + "/" + user.getID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", user.getID());
            jsonObject.put("nombreUsuario", user.getNombreUsuario());
            jsonObject.put("nombre", user.getNombre());
            jsonObject.put("primerApellido", user.getPrimerApellido());
            jsonObject.put("segundoApellido", user.getSegundoApellido());
            jsonObject.put("edad", user.getEdad());
            jsonObject.put("dni", user.getDni());
            jsonObject.put("genero", user.getGenero());
            jsonObject.put("tipoDeMiembro", user.getTipoDeMiembro());
            jsonObject.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(putRequest);
        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void delUser(int id, final boolean conBitacora, final int idBitacora){
        System.out.println("check delUser");
        ruta = Globals.RUTA_SERVIDOR_TOMCAT + "/"+Globals.SERVER_TABLE_NAME;
        String tag_json_obj = "delUser";
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }
        );
        //queue.add(delRequest);
        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }
}