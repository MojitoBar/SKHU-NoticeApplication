package com.example.judongseok.itcontest;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;



public class MyService extends Service {
    private String htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?bsid=10008"; //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";


    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi ;
    MainActivity mainActivity;

    // 크롤링 카운터
    int cnt = 0;

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();
                //테스트1
                Elements titles= doc.select("td.left15");

                for(Element e: titles){
                    htmlContentInStringFormat += e.text().trim() + "\n";
                    cnt++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mainActivity = new MainActivity();
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    void crawlingCheck(){

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
            System.out.println(mainActivity.ContestNoticeCount);
            System.out.println(cnt);

        if(mainActivity.ContestNoticeCount != cnt){

        }
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(MyService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            crawlingCheck();

            Notifi = new Notification.Builder(getApplicationContext())
                    .setContentTitle("행사 공지 알림!")
                    .setContentText("행사 공지가 업데이트 됐습니다.")
                    .setSmallIcon(R.drawable.icon_new_gry)
                    .setTicker("알림!!!")
                    .setContentIntent(pendingIntent)
                    .build();
            //소리추가
            Notifi.defaults = Notification.DEFAULT_SOUND;

            //알림 소리를 한번만 내도록
            Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;

            //확인하면 자동으로 알림이 제거 되도록
            Notifi.flags = Notification.FLAG_AUTO_CANCEL;


            Notifi_M.notify( 777 , Notifi);

            //토스트 띄우기
//            Toast.makeText(MyService.this, "뜸?", Toast.LENGTH_LONG).show();
        }
    };
}