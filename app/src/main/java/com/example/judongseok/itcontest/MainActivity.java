package com.example.judongseok.itcontest;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private String htmlPageUrl;  //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";
    private String urlPath="";

    // 크롤링 카운터
    int cnt=0;
    // 행사공지 카운터
    int ContestNoticeCount = 0;
    // 학사공지 카운터
    int HacksaNoticeCount= 0;
    // 장학공지 카운터
    int JanghakcNoticeCount= 0;

    // 공지들이 리스트 뷰로 보여지게 하는 변수
    public ArrayList<String> items;
    public ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기


        items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;



        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter);


        // 행사 공지 크롤링 버튼 생성
        Button htmlTitleButton = (Button)findViewById(R.id.button);

        // 학사 공지 크롤링 버튼 생성
        Button hacksaNoticeButton = (Button)findViewById(R.id.button2);

        // 장학 공지 크롤링 버튼 생성
        Button janghackNoticeButton = (Button)findViewById(R.id.button3);

        // 행사공지 크롤링 버튼 리스너
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
                // 행사공지 URL 연결
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?bsid=10008";
                clearTextView();
                // 행사공지 카운트 초기화
                ContestNoticeCount = 0;
                System.out.println( (cnt+1) +"번째 파싱");
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });

        // 학사공지 크롤링 버튼 리스너
        hacksaNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
                // 학사공지 URL 연결
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10004&searchBun=51";
                clearTextView();
                // 학사공지 카운트 초기화
                HacksaNoticeCount = 0;
                System.out.println( (cnt+1) +"번째 파싱");
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });

        // 장학공지 크롤링 버튼 리스너
        janghackNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
                // 장학공지 URL 연결
                htmlPageUrl = "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10006&searchBun=75";
                clearTextView();
                // 장학공지 카운트 초기화
                JanghakcNoticeCount = 0;
                System.out.println( (cnt+1) +"번째 파싱");
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });
    }

    void clearTextView(){
        textviewHtmlDocument.setText("");
        htmlContentInStringFormat = "";
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

                //테스트1
                Elements titles= doc.select("td.left15");

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    htmlContentInStringFormat += e.text().trim() + "\n";

                    // 아이템 추가.
                    items.add(e.text().trim());


                    // 크롤링 페이지가 행사 공지일 때
                    if(htmlPageUrl == "http://www.skhu.ac.kr/board/boardlist.aspx?bsid=10008")
                        // 행사 공지 카운터 증가
                        ContestNoticeCount++;
                    // 크롤링 페이지가 학사 공지일 때
                    else if(htmlPageUrl == "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10004&searchBun=51")
                        // 학사 공지 카운터 증가
                        HacksaNoticeCount++;
                    // 크롤링 페이지가 장학 공지일 때
                    else if(htmlPageUrl == "http://www.skhu.ac.kr/board/boardlist.aspx?curpage=1&bsid=10006&searchBun=75")
                        // 장학 공지 카운터 증가
                        JanghakcNoticeCount++;
                }

                //테스트2
                titles= doc.select("td.left15 a");

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    String href = e.attr("abs:href");
                    urlPath += href + "\n";
                }
                System.out.println(urlPath);
//
//                //테스트3
//                titles= doc.select("li.section02 div.con h2.news-tl");
//
//                System.out.println("-------------------------------------------------------------");
//                for(Element e: titles){
//                    System.out.println("title: " + e.text());
//                    htmlContentInStringFormat += e.text().trim() + "\n";
//                }
//                System.out.println("-------------------------------------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // listview 갱신
            adapter.notifyDataSetChanged();
            //textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }
}
