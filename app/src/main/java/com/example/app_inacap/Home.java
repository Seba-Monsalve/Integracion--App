package com.example.app_inacap;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_inacap.Clases.Publicacion;
import com.example.app_inacap.db.DBAdmin;
import com.example.app_inacap.db.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private Toolbar tbMenuCabecera;
    private FloatingActionButton fabNewComment;
    private FloatingActionButton btn_refresh;
    private ImageView imgRamos;
    private ImageView imgProfesores;
    private ImageView imgRestaurantes;

    TextView tvUsuarioPublicacion;
    TextView tvTextoPublicacion;

    ArrayList<Publicacion> lista_publicacion= new ArrayList<>();

    ListView lv_publicacion;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tbMenuCabecera = findViewById(R.id.tbMenuTopInterior);
        fabNewComment = findViewById(R.id.fabNewComment);
        btn_refresh = findViewById(R.id.btn_refresh);
        imgRamos = findViewById(R.id.imgRamos);
        imgProfesores = findViewById(R.id.imgProfesores);
        imgRestaurantes = findViewById(R.id.imgRestaurantes);
        lv_publicacion = findViewById(R.id.lv_publicacion);

        final Bundle bundle = getIntent().getExtras();
        final String userId = bundle.getString("userId");

        setSupportActionBar(tbMenuCabecera);



        imgRamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Seccion.class));
            }
        });

        imgProfesores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Seccion.class));
            }
        });

        imgRestaurantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Seccion.class));
            }
        });


        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarUltimaPublicacion();

            }
        });
        fabNewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Home.this);
                View mView = getLayoutInflater().inflate(R.layout.alert_new_comment, null);
                //EditText etTitle = mView.findViewById(R.id.etTitle);
                //Spinner spSeccion = mView.findViewById(R.id.spSeccion);
                final EditText etComment = mView.findViewById(R.id.etComment);
                Button btnCancelar = mView.findViewById(R.id.btnCancelar);
                Button btnPublicar = mView.findViewById(R.id.btnPublicar);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                btnPublicar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Publicar
                        String textoPublicacion = etComment.getText().toString().trim();
                       /* DBAdmin dbAdmin = new DBAdmin(Home.this, Util.NOMBRE_BD, null, 1);
                        SQLiteDatabase sqLiteDatabase = dbAdmin.getWritableDatabase();/*
                        String[] params = {userId, textoPublicacion};
                        sqLiteDatabase.execSQL("INSERT INTO PUBLICACION (USUARIO_ID, PUBLICACION_TEXTO) " +
                                "VALUES (?,?);", params);
                        sqLiteDatabase.close();*/
                        final String url = Util.IP+"server/app_inapap/Conexion.php?peticion=insert_publicacion&id="+userId+"&publicacion="+textoPublicacion;
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                        mostrarUltimaPublicacion();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(stringRequest);
                        dialog.dismiss();
                    }

                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();

                    }
                });

                dialog.show();


            }
        });


        mostrarUltimaPublicacion();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void mostrarUltimaPublicacion() {
        DBAdmin dbAdmin = new DBAdmin(Home.this, Util.NOMBRE_BD, null, 1);
        SQLiteDatabase sqLiteDatabase = dbAdmin.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT USUARIO_NOMBRE, PUBLICACION_TEXTO " +
                        "FROM USUARIO, PUBLICACION " +
                        "WHERE USUARIO.USUARIO_ID = PUBLICACION.USUARIO_ID " +
                        "ORDER BY PUBLICACION_ID DESC", null);
        if (cursor.moveToFirst()) {
            //tvUsuarioPublicacion.setText(cursor.getString(0));
            //<tvTextoPublicacion.setText(cursor.getString(1));
        }

        String url = Util.IP+"server/app_inapap/Conexion.php?peticion=select_publicacion";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            lista_publicacion.clear();
                            JSONArray jarr = new JSONArray(response);

                            for (int i = 0; i < jarr.length(); i++) {
                                JSONObject job = jarr.getJSONObject(i);
                                String publicacion = job.optString("publicacion");
                                String nombre_user = job.optString("nombre");
                                int id_user = job.optInt("id_user");
                                lista_publicacion.add(new Publicacion(nombre_user,publicacion));
                                //Toast.makeText(getApplicationContext(), v2, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException ex) {
                            Toast.makeText(getApplicationContext(), "Error: " + ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        AdaptadorPublicacion adaptador =  new AdaptadorPublicacion(this);
        ListView lv = findViewById(R.id.lv_publicacion);
        lv.setAdapter(adaptador);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_interior, menu);
        return true;
    }



    class AdaptadorPublicacion extends ArrayAdapter<Publicacion> {

        AppCompatActivity appCompatActivity;

        AdaptadorPublicacion(AppCompatActivity context) {
            super(context, R.layout.publicacion,lista_publicacion );
            appCompatActivity = context;
        }

        public View getView(final int pos, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.publicacion, null);
            TextView nombre = item.findViewById(R.id.tv_usuario);
            nombre.setText(lista_publicacion.get(pos).toString());
            return item;
        }
    }



}
