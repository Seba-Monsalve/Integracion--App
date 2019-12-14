package com.example.app_inacap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;


public class Seccion extends AppCompatActivity {
    private Toolbar tbMenuTopInterior;
    private View containerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion);

        tbMenuTopInterior = (Toolbar) findViewById(R.id.tbMenuTopInterior);
        containerItem = (View) findViewById(R.id.containerItem);
        setSupportActionBar(tbMenuTopInterior);

        containerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Seccion.this, Perfil.class));
            }
        });
    }

        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu_top_interior, menu);
            return true;
        }



}
