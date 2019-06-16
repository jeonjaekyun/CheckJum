package com.example.jm.checkjum;

import android.graphics.Bitmap;

/**
 * Created by 612-1 on 2018-05-31.
 */

//리사이클러뷰에 올라갈 책 정보를 가지고있는 클래스
public class BookInfo {
    public Bitmap drawableId;
    public String title, writer, publisher, star;

    public BookInfo(Bitmap drawableId, String title, String writer, String publisher, String star){
        this.drawableId = drawableId;
        this.title = title;
        this.writer = writer;
        this.publisher = publisher;
        this.star = star;
    }
}