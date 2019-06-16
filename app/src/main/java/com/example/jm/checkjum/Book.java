package com.example.jm.checkjum;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//도서소개(book.xml) 화면 설계를 위한 class
public class Book extends AppCompatActivity {
    LinearLayout toLibrary;
    TextView b_title, b_author, b_publisher, b_description, b_star;
    ImageView b_cover;
    Bitmap bm;
    String cover = null;
    String title = null, author = null, publisher = null, description = null, star=null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), Main.class);
            intent.putExtra("maink", 0);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);
        setTitle("도서");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent inIntent = getIntent();
        final String getTitle = inIntent.getStringExtra("title");

        toLibrary = findViewById(R.id.toLibrary);
        b_title = findViewById(R.id.b_title);
        b_author = findViewById(R.id.b_author);
        b_publisher = findViewById(R.id.b_publisher);
        b_cover = findViewById(R.id.b_cover);
        b_description = findViewById(R.id.b_description);
        b_star = findViewById(R.id.b_star);

        boolean inTitle = false, inAuthor = false, inPublisher = false, inItem = false, inCover = false, inDescription = false, inStar=false;



        try {

            URL url = new URL("http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbkey&Query="+getTitle+"&QueryType=Title&serchTarget=book&Cover=Small&MaxResults=10&start=1&SearchTarget=Book&Version=20131101");
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱을 시작합니다");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("title")) {
                            inTitle = true;
                        }
                        if (parser.getName().equals("author")) {
                            inAuthor = true;
                        }
                        if (parser.getName().equals("publisher")) {
                            inPublisher = true;
                        }
                        if (parser.getName().equals("cover")) {
                            inCover = true;
                        }
                        if (parser.getName().equals("description")) {
                            inDescription = true;
                        }
                        if (parser.getName().equals("customerReviewRank")) {
                            inStar = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (inTitle) {
                            title = parser.getText();
                            inTitle = false;
                        }
                        if (inAuthor) {
                            author = parser.getText();
                            inAuthor = false;
                        }
                        if (inPublisher) {
                            publisher = parser.getText();
                            inPublisher = false;
                        }
                        if (inCover) {
                            cover = parser.getText();
                            inCover = false;
                        }
                        if (inDescription) {
                            description = parser.getText();
                            inDescription = false;
                        }
                        if (inStar) {
                            star = parser.getText();
                            inStar = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
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
                                        System.out.println(bm);
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
                                b_cover.setImageBitmap(bm);
                                b_title.setText("제목 : " + title);
                                b_author.setText("저자 : " + author);
                                b_publisher.setText("출판사 : " + publisher);
                                b_star.setText("평점 : "+ (Float.parseFloat(star)/2));
                                b_description.setText(description);
                                inItem = false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        //클릭시 관심도서에 등록
        findViewById(R.id.interest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener responseListnener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("test",response);
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Toast.makeText(Book.this,"관심도서에 등록되었습니다.",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Book.this,"관심도서에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                InterestBookPut_Request interestBookPut_request = new InterestBookPut_Request(Login.userIdKey, cover,title,author,publisher,""+Float.parseFloat(star)/2,responseListnener);
                RequestQueue queue = Volley.newRequestQueue(Book.this);
                queue.add(interestBookPut_request);
            }
        });

        findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View c_dialog = View.inflate(Book.this, R.layout.simple_comment_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(Book.this);
                dlg.setTitle("한줄평");
                dlg.setView(c_dialog);
                dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText comment = c_dialog.findViewById(R.id.input_comment);

                        Response.Listener responseListnener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("test",response);
                                try{
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if(success){
                                        Toast.makeText(Book.this,"한줄평이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Book.this,"한줄평등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        SimpleComment_Request Simple_comment_Request = new SimpleComment_Request(Login.userIdKey, cover,title,comment.getText().toString(),""+Float.parseFloat(star)/2,responseListnener);
                        RequestQueue queue = Volley.newRequestQueue(Book.this);
                        queue.add(Simple_comment_Request);
                    }
                });
                dlg.show();
            }
        });

        //클릭시 별점주기 대화상자 나옴
        findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View c_dialog = View.inflate(Book.this, R.layout.checkjum_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(Book.this);
                dlg.setTitle("별점주기");
                dlg.setView(c_dialog);
                dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RatingBar jumsu = c_dialog.findViewById(R.id.jumsu);


                        Response.Listener responseListnener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("test",response);
                                try{
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if(success){
                                        Toast.makeText(Book.this,"별점이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Book.this,"별점등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        jumsuPut_Request jumsuPut_request = new jumsuPut_Request(Login.userIdKey, cover,title,jumsu.getRating(),responseListnener);
                        RequestQueue queue = Volley.newRequestQueue(Book.this);
                        queue.add(jumsuPut_request);
                    }
                });
                dlg.show();
            }
        });

        toLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Book.this, Main.class);
                intent.putExtra("maink", 1);
                startActivity(intent);
            }
        });
    }
}
