package com.example.judongseok.itcontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by judongseok on 2018-09-16.
 */

public class NoticeButtons extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_buttons);

        Button btn_janghack = (Button)findViewById(R.id.janghack);
        Button btn_hengsa= (Button)findViewById(R.id.hengsa);
        Button btn_hacksa= (Button)findViewById(R.id.hacksa);
        Button btn_suep= (Button)findViewById(R.id.suep);

        Button btn_option = (Button)findViewById(R.id.button);

        btn_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeButtons.this, MainScene.class);
                startActivity(intent);
            }
        });

        btn_hacksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeButtons.this, MainActivity.class);
                intent.putExtra("HtmlUrl", "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10004&searchBun=51");
                startActivity(intent);
            }
        });
        btn_hengsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeButtons.this, MainActivity.class);
                intent.putExtra("HtmlUrl", "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10008");
                startActivity(intent);
            }
        });
        btn_janghack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeButtons.this, MainActivity.class);
                intent.putExtra("HtmlUrl", "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10006&searchBun=75");
                startActivity(intent);
            }
        });
        btn_suep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeButtons.this, MainActivity.class);
                intent.putExtra("HtmlUrl", "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10005&searchBun=53");
                startActivity(intent);
            }
        });
    }
}
