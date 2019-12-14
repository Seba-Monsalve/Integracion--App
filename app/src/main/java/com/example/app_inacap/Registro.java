package com.example.app_inacap;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_inacap.db.DBAdmin;
import com.example.app_inacap.db.Util;

public class Registro extends AppCompatActivity {

    private EditText etNombre;
    private EditText etEmail;
    private EditText etPass;
    private EditText etRepeatPass;

    private ImageButton btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        etRepeatPass = findViewById(R.id.etRepeatPass);

        btnRegistrar = (ImageButton) findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String repeatPass = etRepeatPass.getText().toString().trim();

                boolean error = false;

                if (!nombre.isEmpty()) {
                    //POS NADA :v
                } else {
                    etNombre.setError("Debe ingresar nombre");
                    error = true;
                }

                if (!email.isEmpty()) {
                    //Verif email
                    String regEx = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regEx);
                    java.util.regex.Matcher matcher = pattern.matcher(email);
                    if (!matcher.matches()) {
                        etEmail.setError("Formato de email no válido");
                        error = true;
                    }
                } else {
                    etEmail.setError("Debe ingresar email");
                    error = true;
                }

                if (!pass.isEmpty()) {
                    if (!repeatPass.isEmpty()) {
                        //Verif passwords
                        if (!pass.equals(repeatPass)) {
                            etRepeatPass.setError("Las contraseñas no coinciden");
                            error = true;
                        }
                    } else {
                        etRepeatPass.setError("Debe repetir password");
                        error = true;
                    }
                } else {
                    etPass.setError("Debe ingresar password");
                    error = true;
                }


                if (!error) {
                    DBAdmin dbAdmin = new DBAdmin(Registro.this, Util.NOMBRE_BD, null, 1);
                    SQLiteDatabase sqLiteDatabase = dbAdmin.getWritableDatabase();
                    String[] params = {nombre, email, pass};
                    sqLiteDatabase.execSQL(
                            "INSERT INTO USUARIO (" +
                                    "USUARIO_NOMBRE," +
                                    "USUARIO_EMAIL," +
                                    "USUARIO_PASSWORD) VALUES (?,?,?)", params);
                    sqLiteDatabase.close();
                    finish();

                    String url = Util.IP+"server/app_inapap/Conexion.php?peticion=insert_user&pass="+pass+"&mail="+email+"&nombre="+nombre;
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(stringRequest);
                }
            }
        });
    }
}
