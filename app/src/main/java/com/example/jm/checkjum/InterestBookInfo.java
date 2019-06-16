package com.example.jm.checkjum;

import android.graphics.Bitmap;

/**
 * Created by woavm on 2018-06-01.
 */

public class InterestBookInfo { //관심분야 클래스에 생성한 InterestBookInfo클래스
    Bitmap drawableID;
    String title,star;
    public InterestBookInfo(Bitmap drawableID, String title, String star){  //InterestBookInfo 클래스에 사용할 변수
        this.drawableID = drawableID;
        this.star = star;
        this.title = title;
    }
}
