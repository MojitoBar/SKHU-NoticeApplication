package com.example.judongseok.itcontest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by judongseok on 2018-09-16.
 */

public class MainScene extends BaseActivity{

    public Switch sw;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        check = pref.getBoolean("checked", false);
        System.out.println("onStart");
        System.out.println(check);
        if (check){
            sw.setChecked(true);
            System.out.println("체크됨");
        }
        else
            sw.setChecked(false);
    }

    boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        setContentView(R.layout.mainscene_activity);

        sw = (Switch)findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !pref.getBoolean("checked", false)){
                    editor.putBoolean("checked", true);
                    editor.commit();

                    Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainScene.this, MyService.class);
                    startService(intent);
                }
                else{
                    editor.putBoolean("checked", false);
                    editor.commit();

                    Intent intent = new Intent(MainScene.this, MyService.class);
                    stopService(intent);
                }
            }
        });
    }
}
