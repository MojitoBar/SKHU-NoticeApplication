package com.example.judongseok.itcontest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public String htmlPageUrl = null;  //파싱할 홈페이지의 URL주소
    public String[] CurlPath;
    public String[] HurlPath;
    public String[] JurlPath;
    public String[] SurlPath;
    public String[] GurlPath;
    public String[] NurlPath;
    public String[] urlPath;

    // 행사공지 카운터
    public int ContestNoticeCount = 0;
    // 학사공지 카운터
    public int HacksaNoticeCount= 0;
    // 장학공지 카운터
    public int JanghakcNoticeCount= 0;
    // 일반공지 카운터
    public int NomalNoticeCount = 0;
    // 학점교류 카운터
    public int ScoreNoticeCount = 0;
    // 수업공지 카운터
    public int SuepNoticeCount = 0;

    int currentPage = 1;

    // 공지들이 리스트 뷰로 보여지게 하는 변수
    private ListView m_oListView = null;
    public ArrayList<ItemData> oData;
    ListAdapter oAdapter;

    TextView curpos;

    ProgressBar progressBar;
    boolean loadingCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10008";

        final Button cbtn = (Button)findViewById(R.id.contestbtn);
        final Button nbtn = (Button)findViewById(R.id.nomalbtn);
        final Button gbtn = (Button)findViewById(R.id.scorebtn);
        final Button sbtn = (Button)findViewById(R.id.suepbtn);
        final Button jbtn = (Button)findViewById(R.id.janghackbtn);
        final Button hbtn = (Button)findViewById(R.id.hacksabtn);

        Button alarm = (Button)findViewById(R.id.alarm);

        CurlPath = new String[30];
        SurlPath = new String[30];
        HurlPath = new String[30];
        JurlPath = new String[30];
        GurlPath = new String[30];
        NurlPath = new String[30];

        urlPath = new String[30];

        oData = new ArrayList<>();

        curpos = (TextView)findViewById(R.id.cur_pos);

        curpos.setText(currentPage + " / 30");

        final Button nextButton = (Button)findViewById(R.id.button4);
        final Button preButton = (Button)findViewById(R.id.button5);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MainScene.class);
                startActivity(intent);
            }
        });

        cbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 1;
                curpos.setText(currentPage + " / 30");
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10008";
                StartCrawling();
                cbtn.setTextColor(Color.argb(255, 54, 172, 255));
                nbtn.setTextColor(Color.argb(255, 71, 71, 71));
                jbtn.setTextColor(Color.argb(255, 71, 71, 71));
                hbtn.setTextColor(Color.argb(255, 71, 71, 71));
                gbtn.setTextColor(Color.argb(255, 71, 71, 71));
                sbtn.setTextColor(Color.argb(255, 71, 71, 71));
            }
        });

        nbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 1;
                curpos.setText(currentPage + " / 30");
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10007";
                StartCrawling();
                cbtn.setTextColor(Color.argb(255, 71, 71, 71));
                nbtn.setTextColor(Color.argb(255, 54, 172, 255));
                jbtn.setTextColor(Color.argb(255, 71, 71, 71));
                hbtn.setTextColor(Color.argb(255, 71, 71, 71));
                gbtn.setTextColor(Color.argb(255, 71, 71, 71));
                sbtn.setTextColor(Color.argb(255, 71, 71, 71));
            }
        });

        jbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 1;
                curpos.setText(currentPage + " / 30");
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10006&searchBun=75";
                StartCrawling();
                cbtn.setTextColor(Color.argb(255, 71, 71, 71));
                nbtn.setTextColor(Color.argb(255, 71, 71, 71));
                jbtn.setTextColor(Color.argb(255, 54, 172, 255));
                hbtn.setTextColor(Color.argb(255, 71, 71, 71));
                gbtn.setTextColor(Color.argb(255, 71, 71, 71));
                sbtn.setTextColor(Color.argb(255, 71, 71, 71));
            }
        });

        hbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 1;
                curpos.setText(currentPage + " / 30");
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10004&searchBun=51";
                StartCrawling();
                cbtn.setTextColor(Color.argb(255, 71, 71, 71));
                nbtn.setTextColor(Color.argb(255, 71, 71, 71));
                jbtn.setTextColor(Color.argb(255, 71, 71, 71));
                hbtn.setTextColor(Color.argb(255, 54, 172, 255));
                gbtn.setTextColor(Color.argb(255, 71, 71, 71));
                sbtn.setTextColor(Color.argb(255, 71, 71, 71));
            }
        });

        gbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 1;
                curpos.setText(currentPage + " / 30");
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10038&searchBun=89";
                StartCrawling();
                cbtn.setTextColor(Color.argb(255, 71, 71, 71));
                nbtn.setTextColor(Color.argb(255, 71, 71, 71));
                jbtn.setTextColor(Color.argb(255, 71, 71, 71));
                hbtn.setTextColor(Color.argb(255, 71, 71, 71));
                gbtn.setTextColor(Color.argb(255, 54, 172, 255));
                sbtn.setTextColor(Color.argb(255, 71, 71, 71));
            }
        });

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 1;
                curpos.setText(currentPage + " / 30");
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10005&searchBun=53";
                StartCrawling();
                cbtn.setTextColor(Color.argb(255, 71, 71, 71));
                nbtn.setTextColor(Color.argb(255, 71, 71, 71));
                jbtn.setTextColor(Color.argb(255, 71, 71, 71));
                hbtn.setTextColor(Color.argb(255, 71, 71, 71));
                gbtn.setTextColor(Color.argb(255, 71, 71, 71));
                sbtn.setTextColor(Color.argb(255, 54, 172, 255));
            }
        });


        // 다음으로 넘어가는 버튼 리스너
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage < 30 && htmlPageUrl != null && !loadingCheck){
                    currentPage++;
                    int idx = htmlPageUrl.indexOf("=");
                    int z = htmlPageUrl.charAt(idx+1);
                    z++;
                    StringBuilder temp = new StringBuilder(htmlPageUrl);
                    temp.setCharAt(idx+1, (char)z);
                    htmlPageUrl = temp.toString();

                    oData.clear();

                    StartCrawling();
                    curpos.setText(currentPage + " / 30");
                }
            }
        });

        // 이전으로 넘어가는 버튼 리스너
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage > 1 && htmlPageUrl != null && !loadingCheck){
                    currentPage--;
                    int idx = htmlPageUrl.indexOf("=");
                    int z = htmlPageUrl.charAt(idx+1);
                    System.out.print(z);
                    z--;
                    StringBuilder temp = new StringBuilder(htmlPageUrl);
                    temp.setCharAt(idx+1, (char)z);
                    htmlPageUrl = temp.toString();

                    oData.clear();

                    StartCrawling();
                    curpos.setText(currentPage + " / 30");
                }
            }
        });

        // 맨 처음 실행되는 크롤링
        StartCrawling();
    }

    void StartCrawling(){
        progressBar.setVisibility(View.VISIBLE);
        oData.clear();
        loadingCheck = true;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();
                //테스트2
                Elements titles= doc.select("td.left15 a");

                int i = 0;
                for(Element e: titles){

                    // 크롤링 페이지가 행사 공지일 때
                    if(htmlPageUrl.contains("10008")){
                        String href = e.attr("abs:href");
                        CurlPath[i] = href;
                        urlPath[i] = href;
                        i++;
                    }
                    // 크롤링 페이지가 학사 공지일 때
                    else if(htmlPageUrl.contains("10004")){
                        String href = e.attr("abs:href");
                        HurlPath[i] = href;
                        urlPath[i] = href;
                        i++;
                    }
                    // 크롤링 페이지가 장학 공지일 때
                    else if(htmlPageUrl.contains("10006")){
                        String href = e.attr("abs:href");
                        JurlPath[i] = href;
                        urlPath[i] = href;
                        i++;
                    }
                    // 크롤링 페이지가 수업 공지일 때
                    else if(htmlPageUrl.contains("10005")){
                        String href = e.attr("abs:href");
                        SurlPath[i] = href;
                        urlPath[i] = href;
                        i++;
                    }
                    // 크롤링 페이지가 학점 교류일 때
                    else if(htmlPageUrl.contains("10038")){
                        String href = e.attr("abs:href");
                        GurlPath[i] = href;
                        urlPath[i] = href;
                        i++;
                    }
                    else if (htmlPageUrl.contains("10007")){
                        String href = e.attr("abs:href");
                        NurlPath[i] = href;
                        urlPath[i] = href;
                        i++;
                    }
                }
                //테스트1
                titles= doc.select("td.left15");

                int j = 0;
                for(Element e: titles){
                    ItemData oItem = new ItemData();
                    oItem.strNum = e.text().trim();

                    // 크롤링 페이지가 행사 공지일 때
                    if(htmlPageUrl.contains("10008")){
                        // 행사 공지 카운터 증가
                        ContestNoticeCount++;
                        oItem.strTitle = "행사공지";
                    }
                    // 크롤링 페이지가 학사 공지일 때
                    else if(htmlPageUrl.contains("10004")){
                        // 학사 공지 카운터 증가
                        HacksaNoticeCount++;
                        oItem.strTitle = "학사공지";
                    }
                    // 크롤링 페이지가 장학 공지일 때
                    else if(htmlPageUrl.contains("10006")){
                        // 장학 공지 카운터 증가
                        JanghakcNoticeCount++;
                        oItem.strTitle = "장학공지";
                    }
                    // 크롤링 페이지가 수업 공지일 때
                    else if(htmlPageUrl.contains("10005")){
                        SuepNoticeCount++;
                        oItem.strTitle = "수업공지";
                    }
                    // 크롤링 페이지가 학점 교류일 때
                    else if(htmlPageUrl.contains("10038")){
                        ScoreNoticeCount++;
                        oItem.strTitle = "학점교류";
                    }
                    else if (htmlPageUrl.contains("10007")){
                        NomalNoticeCount++;
                        oItem.strTitle = "일반공지";
                    }


                    // 공지 content 불러오기
                    doc = Jsoup.connect(urlPath[j]).get();
                    //테스트2
                    titles = doc.select(".board_view_con");

                    oItem.URL = urlPath[j];
                    oItem.strDate = titles.text().trim() + "...";
                    oData.add(oItem);

                    titles = doc.select("td.left15");
                    j++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // listview 갱신
            // 리스트뷰 어댑터 생성 및 연결

            m_oListView = (ListView)findViewById(R.id.listview1);
            oAdapter = new ListAdapter(oData);
            m_oListView.setAdapter(oAdapter);
            m_oListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String c_list = urlPath[position];
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(c_list));
                    startActivity(intent);
                }
            });
            setPreferences();
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            loadingCheck = false;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            System.out.println(loadingCheck);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    void setPreferences(){

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if (currentPage == 1) {
            if (JurlPath[23] != null)
                editor.putString("LastJUrl", JurlPath[23]);
            if (CurlPath[14] != null)
                editor.putString("LastCUrl", CurlPath[14]);
            if (HurlPath[20] != null)
                editor.putString("LastHUrl", HurlPath[20]);
            if (SurlPath[14] != null)
                editor.putString("LastSUrl", SurlPath[14]);
            if (GurlPath[15] != null)
                editor.putString("LastGUrl", GurlPath[15]);
            if (NurlPath[17] != null)
                editor.putString("LastNUrl", NurlPath[17]);
        }

        editor.commit();
    }
}
