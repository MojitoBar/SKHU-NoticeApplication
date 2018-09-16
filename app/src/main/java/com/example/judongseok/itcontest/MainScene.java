package com.example.judongseok.itcontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by judongseok on 2018-09-16.
 */

public class MainScene extends AppCompatActivity{
    private Button btnStart, btnEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscene_activity);
        btnStart = (Button)findViewById(R.id.btn_start);
        btnEnd = (Button)findViewById(R.id.btn_end);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainScene.this, MyService.class);
                startService(intent);
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Service 끝",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainScene.this, MyService.class);
                stopService(intent);
            }
        });
    }
}
