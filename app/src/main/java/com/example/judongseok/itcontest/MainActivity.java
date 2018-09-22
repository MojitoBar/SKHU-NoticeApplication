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
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // 크롤링 공지에 변화가 생김을 체크하기 위해 SharedPreferences 사용
    SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE); // 선언


    SharedPreferences.Editor editor = pref.edit();// editor에 put 하기


    private String htmlPageUrl = null;  //파싱할 홈페이지의 URL주소
    private String htmlContentInStringFormat="";
    private String[] urlPath;

    // 행사공지 카운터
    public int ContestNoticeCount = 0;
    // 학사공지 카운터
    public int HacksaNoticeCount= 0;
    // 장학공지 카운터
    public int JanghakcNoticeCount= 0;

    int currentPage = 1;

    // 공지들이 리스트 뷰로 보여지게 하는 변수
    public ArrayList<String> items;
    public ArrayAdapter adapter;
    public ListView listview;

    TextView curpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        htmlPageUrl = intent.getStringExtra("HtmlUrl");

        urlPath = new String[30];

        items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        adapter = new ArrayAdapter(this, R.layout.mytextview, items);

        // listview 생성 및 adapter 지정.
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(this);

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
                    System.out.print(z);
                    z++;
                    StringBuilder temp = new StringBuilder(htmlPageUrl);
                    temp.setCharAt(idx+1, (char)z);
                    htmlPageUrl = temp.toString();

                    items.clear();

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

                    items.clear();

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
        String c_list = urlPath[position];
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(c_list));
        startActivity(intent);
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

                int i = 0;
                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    String href = e.attr("abs:href");
                    urlPath[i] = href;
                    i++;
                }

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
