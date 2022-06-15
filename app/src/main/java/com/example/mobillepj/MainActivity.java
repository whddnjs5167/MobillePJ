package com.example.mobillepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.signInButton);
        Button btn2 = findViewById(R.id.singUpButton);

        btn1.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        });

        btn2.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
            //hihi
        });
    }
}