package com.example.jm.checkjum;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by JM on 2018-05-22.
 */

//책점화면(f_checkjum.xml) 프래그먼트
public class F_checkjum extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Bitmap bm;
    String cover = null;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_checkjum, container, false);

        StrictMode.enableDefaults();

        mRecyclerView = view.findViewById(R.id.recyclerV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        boolean inTitle = false, inAuthor = false, inPublisher = false, inItem = false, inCover = false, inStar = false;
        String title = null, author = null, publisher = null,star=null;


        ArrayList<BookInfo> bookInfoArrayList = new ArrayList<>();
        try {
            URL url = new URL("http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbkey&QueryType=Bestseller&Cover=Small&MaxResults=20&start=1&SearchTarget=Book&Version=20131101");

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
                                bookInfoArrayList.add(new BookInfo(bm, title , author , publisher , star));
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
            Log.d("ttttt",e.toString());
        }

        Recommand_Adapter myAdapter = new Recommand_Adapter(bookInfoArrayList);
        mRecyclerView.setAdapter(myAdapter);


        return view;
    }
}
