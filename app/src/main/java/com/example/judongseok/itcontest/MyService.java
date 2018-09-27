package com.example.judongseok.itcontest;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;


public class MyService extends Service {
    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi ;

    String[] JurlPath = new String[30];
    String[] HurlPath = new String[30];
    String[] CurlPath = new String[30];
    String[] NurlPath = new String[30];
    String[] GurlPath = new String[30];
    String[] SurlPath = new String[30];

    String LastJUrl = "";
    String LastHUrl = "";
    String LastCUrl = "";
    String LastNUrl = "";
    String LastGUrl = "";
    String LastSUrl = "";

    String NewJUrl = "";
    String NewHUrl = "";
    String NewCUrl = "";
    String NewNUrl = "";
    String NewGUrl = "";
    String NewSUrl = "";

    boolean j = false;
    boolean h = false;
    boolean c = false;
    boolean n = false;
    boolean g = false;
    boolean s = false;



    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // 행사공지 크롤링
                Document doc = Jsoup.connect("http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10008").get();
                Elements titles;
                int i = 0;

                titles= doc.select("td.left15 a");
                for(Element e: titles){
                    String href = e.attr("abs:href");
                    CurlPath[i] = href;
                    i++;
                }

                // 학사공지 크롤링
                doc = Jsoup.connect("http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10004&searchBun=51").get();
                i = 0;

                titles= doc.select("td.left15 a");
                for(Element e: titles){
                    String href = e.attr("abs:href");
                    HurlPath[i] = href;
                    i++;
                }

                // 장학공지 크롤링
                doc = Jsoup.connect("http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10006&searchBun=75").get();
                i = 0;

                titles= doc.select("td.left15 a");
                for(Element e: titles){
                    String href = e.attr("abs:href");
                    JurlPath[i] = href;
                    i++;
                }

                // 일반공지 크롤링
                doc = Jsoup.connect("http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10007").get();
                i = 0;

                titles= doc.select("td.left15 a");
                for(Element e: titles){
                    String href = e.attr("abs:href");
                    NurlPath[i] = href;
                    i++;
                }

                // 학점교류 크롤링
                doc = Jsoup.connect("http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10038&searchBun=89").get();
                i = 0;

                titles= doc.select("td.left15 a");
                for(Element e: titles){
                    String href = e.attr("abs:href");
                    GurlPath[i] = href;
                    i++;
                }

                // 수업공지 크롤링
                doc = Jsoup.connect("http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10005&searchBun=53").get();
                i = 0;

                titles= doc.select("td.left15 a");
                for(Element e: titles){
                    String href = e.attr("abs:href");
                    SurlPath[i] = href;
                    i++;
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
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        LastJUrl = pref.getString("LastJUrl", "");
        LastCUrl = pref.getString("LastCUrl", "");
        LastHUrl = pref.getString("LastHUrl", "");

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

        NewJUrl = JurlPath[23];
        NewCUrl = CurlPath[14];
        NewHUrl = HurlPath[20];
        NewNUrl = NurlPath[17];
        NewGUrl = GurlPath[15];
        NewSUrl = SurlPath[14];

        if (NewJUrl != null && !LastJUrl.equals(NewJUrl)){
            j = true;
        }
        if (NewCUrl != null && !LastCUrl.equals(NewCUrl)){
            c = true;
        }
        if (NewHUrl != null && !LastHUrl.equals(NewHUrl)){
            h = true;
        }
        if (NewNUrl != null && !LastNUrl.equals(NewNUrl)){
            n = true;
        }
        if (NewGUrl != null && !LastGUrl.equals(NewGUrl)){
            g = true;
        }
        if (NewSUrl != null && !LastSUrl.equals(NewSUrl)){
            s = true;
        }
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(MyService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            crawlingCheck();
//            이 부분에서 bool형 변수를 통해 Url이 다른지 체크
//            다르다면 그에 맞는 Notifi 생성
//            예외적으로 원하는 공지만 알림을 띄우기 위해 bool형 변수 하나 더 추가
//            원하는 공지만 알림을 띄우기 위한 bool형 변수와 Url이 다르다는 것을 알려주는 bool형 변수 두 개가 서로 true면 Notifi 생성


            if (j){
                j = false;
                Notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("장학 공지 알림!")
                        .setContentText("장학 공지가 업데이트 됐습니다.")
                        .setSmallIcon(R.drawable.snews_icon)
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
            }
            if (c){
                c = false;
                Notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("행사 공지 알림!")
                        .setContentText("행사 공지가 업데이트 됐습니다.")
                        .setSmallIcon(R.drawable.snews_icon)
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
            }
            if (s){
                s = false;
                Notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("수업 공지 알림!")
                        .setContentText("수업 공지가 업데이트 됐습니다.")
                        .setSmallIcon(R.drawable.snews_icon)
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
            }
            if (h){
                h = false;
                Notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("학사 공지 알림!")
                        .setContentText("학사 공지가 업데이트 됐습니다.")
                        .setSmallIcon(R.drawable.snews_icon)
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
            }
            if (n){
                n = false;
                Notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("일반 공지 알림!")
                        .setContentText("일반 공지가 업데이트 됐습니다.")
                        .setSmallIcon(R.drawable.snews_icon)
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
            }
            if (g){
                h = false;
                Notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("학점 교류 알림!")
                        .setContentText("학점 교류가 업데이트 됐습니다.")
                        .setSmallIcon(R.drawable.snews_icon)
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
            }
            else {
                Notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("테스트 알림!")
                        .setContentText("이런 형식으로 알림이 표시 됩니다!")
                        .setSmallIcon(R.drawable.snews_icon)
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
            }
        }
    };
}