package com.example.app_inacap;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_inacap.Clases.Publicacion;
import com.example.app_inacap.Clases.Usuario;
import com.example.app_inacap.db.DBAdmin;
import com.example.app_inacap.db.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etUser;
    private EditText etPass;

    private Button btnIngresar;
    private TextView tvRegristrate;

    final private ArrayList<Usuario> lista_usuario= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);

        btnIngresar = findViewById(R.id.btnIngresar);
        tvRegristrate = findViewById(R.id.tvRegristrate);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String user = etUser.getText().toString().trim();
                final String pass = etPass.getText().toString().trim();

                /*
                DBAdmin dbAdmin = new DBAdmin(MainActivity.this, Util.NOMBRE_BD, null, 1);
                SQLiteDatabase sqLiteDatabase = dbAdmin.getWritableDatabase();
                String[] params = {user, pass};
                Cursor cursor = sqLiteDatabase.rawQuery(
                        "SELECT " +
                                "USUARIO_ID " +
                                "FROM USUARIO " +
                                "WHERE USUARIO_EMAIL = ? " +
                                "AND USUARIO_PASSWORD = ?", params);

                if (cursor.moveToFirst()) {
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("userId", cursor.getString(0));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o password incorrecto", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                sqLiteDatabase.close();
            }*/

                String url = Util.IP+"server/app_inapap/Conexion.php?peticion=select_user";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    lista_usuario.clear();
                                    JSONArray jarr = new JSONArray(response);
                                    boolean flag = false;
                                    for (int i = 0; i < jarr.length(); i++) {
                                        JSONObject job = jarr.getJSONObject(i);
                                        String  id = job.optString("id");
                                        String nombre_user = job.optString("nombre");
                                        String pass_user = job.optString("pass");
                                        String email_user = job.optString("email");
                                       // lista_usuario.add(new Usuario(id,nombre_user,pass_user,email_user));
                                        //Toast.makeText(MainActivity.this, email_user+"  "+pass_user, Toast.LENGTH_SHORT).show();
                                        if(user.equalsIgnoreCase(email_user)&&(pass.equalsIgnoreCase(pass_user))){
                                            Intent intent = new Intent(MainActivity.this, Home.class);
                                            intent.putExtra("userId", id);
                                            Toast.makeText(MainActivity.this, email_user+"  "+pass_user+" "+id, Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                            finish();
                                            flag=true;
                                        }
                                    }
                                    if(!flag)
                                    Toast.makeText(MainActivity.this, "Usuario o password incorrecto", Toast.LENGTH_SHORT).show();
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
            }
            });





        tvRegristrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });
    }
}
