package com.example.developer.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button locate;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locate = (Button)findViewById(R.id.buttonLocate);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Descifrando posición Enemiga!",
                        Toast.LENGTH_LONG).show();
                Intent locate = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(locate);
            }
        });

    }
}

