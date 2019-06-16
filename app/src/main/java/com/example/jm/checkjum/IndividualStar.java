package com.example.jm.checkjum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by woavm on 2018-06-01.
 */

public class IndividualStar extends AppCompatActivity {  //개인별점 클래스
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Spinner sp;
    Bitmap bm;
    String myJSON;

    //result는 전체 배열의 이름, cover, title은 원하는 데이터의 이름
    private static final String TAG_RESULTS="result";
    private static final String TAG_COVER="cover";
    private static final String TAG_TITLE="title";
    private static final String TAG_JUMSU="jumsu";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(), Main.class);
            intent.putExtra("maink",2);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interestbook);
        setTitle("개인별점");

        sp= findViewById(R.id.sp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent inIntent = getIntent();
        String order = inIntent.getStringExtra("order");

        //연결하려고 하는 url의 주소를 입력
        String url = "http://ipAddress/GetJumsu.php?userID="+Login.userIdKey+"&order="+order;

        Log.d("url",url);

        final String[] mid = {"정렬조건","별점순","제목순"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mid);
        sp.setAdapter(adapter1);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), IndividualStar.class);
                        intent1.putExtra("order","star");
                        startActivity(intent1);
                        finish();
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), IndividualStar.class);
                        intent2.putExtra("order","title");
                        startActivity(intent2);
                        finish();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mRecyclerView = findViewById(R.id.re);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getData(url);

    }

    //서버로 부터 가져온 데이터를 RecyclerView로 보여주는 코드
    protected void showList(){

        ArrayList<IndividualInfo> IndividualInfoArrayList = new ArrayList<>(); //관심도서 Individual 생성
        try{
            //json을 받아온 myJSON을 jsonObject에 넣어줌
            JSONObject jsonObject = new JSONObject(myJSON);
            Log.d("myJSON",myJSON);

            //myJSON안에 TAG_RESULTS(result)를 배열로 받아옴
            JSONArray books = jsonObject.getJSONArray(TAG_RESULTS);

            //배열을 book의 길이만큼 받아옴
            for(int i =0;i<books.length();i++){
                JSONObject c = books.getJSONObject(i);
                final String cover = c.getString(TAG_COVER);
                String title = c.getString(TAG_TITLE);
                String jumsu = c.getString(TAG_JUMSU);

                Log.d("info",cover);
                Log.d("info",title);
                //String형의 cover(url형식)을 bitmap으로 변환 시켜줌
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL coverUrl = new URL(cover);
                            HttpURLConnection conn = (HttpURLConnection) coverUrl.openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            bm = BitmapFactory.decodeStream(is);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                };

                mThread.start();

                try {
                    mThread.join();
                    IndividualInfoArrayList.add(new IndividualInfo(bm,title,jumsu));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            IndividualStar_Adapter myAdapter = new IndividualStar_Adapter(IndividualInfoArrayList);
            mRecyclerView.setAdapter(myAdapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... strings) { //Strings는 url을 담고있는 배열

                String uri = strings[0];

                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    //버퍼로 con(open한 url)을 받아옴
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    //String json에 버퍼로 읽어온 url의 문장들을 저장해줌
                    while ((json = bufferedReader.readLine())!=null){
                        sb.append(json+"\n");
                        Log.d("json",json);
                    }

                    //onPostExecute로 리턴해줌
                    return sb.toString().trim();

                }catch (Exception e){
                    return null;
                }

            }
            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}