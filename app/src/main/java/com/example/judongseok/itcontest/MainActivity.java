package com.example.judongseok.itcontest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public String htmlPageUrl = null;  //파싱할 홈페이지의 URL주소
    public String[] CurlPath;
    public String[] HurlPath;
    public String[] JurlPath;
    public String[] urlPath;

    // 행사공지 카운터
    public int ContestNoticeCount = 0;
    // 학사공지 카운터
    public int HacksaNoticeCount= 0;
    // 장학공지 카운터
    public int JanghakcNoticeCount= 0;

    int currentPage = 1;

    // 공지들이 리스트 뷰로 보여지게 하는 변수
    private ListView m_oListView = null;
    public ArrayList<ItemData> oData;
    ListAdapter oAdapter;

    TextView curpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10008";

        CurlPath = new String[30];
        HurlPath = new String[30];
        JurlPath = new String[30];

        urlPath = new String[30];

        oData = new ArrayList<>();

        curpos = (TextView)findViewById(R.id.cur_pos);

        curpos.setText(currentPage + " / 30");

        // 공지 다음으로 넘어가는 버튼
        Button nextButton = (Button)findViewById(R.id.button4);

        // 공지 이전으로 넘어가는 버튼
        Button preButton = (Button)findViewById(R.id.button5);

        // 다음으로 넘어가는 버튼 리스너
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < 30 && htmlPageUrl != null){
                    currentPage++;
                    int idx = htmlPageUrl.indexOf("=");
                    int z = htmlPageUrl.charAt(idx+1);
                    z++;
                    StringBuilder temp = new StringBuilder(htmlPageUrl);
                    temp.setCharAt(idx+1, (char)z);
                    htmlPageUrl = temp.toString();

                    oData.clear();

                        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                        jsoupAsyncTask.execute();
                    curpos.setText(currentPage + " / 30");
                }
            }
        });

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 1 && htmlPageUrl != null){
                    currentPage--;
                    int idx = htmlPageUrl.indexOf("=");
                    int z = htmlPageUrl.charAt(idx+1);
                    System.out.print(z);
                    z--;
                    StringBuilder temp = new StringBuilder(htmlPageUrl);
                    temp.setCharAt(idx+1, (char)z);
                    htmlPageUrl = temp.toString();

                    oData.clear();

                    JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                    jsoupAsyncTask.execute();
                    curpos.setText(currentPage + " / 30");
                }
            }
        });

        // 맨 처음 실행되는 크롤링
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
        }

        editor.commit();
    }
}
