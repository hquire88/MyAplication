package com.example.developer.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locate = (Button)findViewById(R.id.buttonLocate);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent locate = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(locate);
            }
        });

    }
}

