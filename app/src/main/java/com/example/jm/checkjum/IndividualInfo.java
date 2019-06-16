package com.example.jm.checkjum;

import android.graphics.Bitmap;

/**
 * Created by woavm on 2018-06-01.
 */

public class IndividualInfo {  //개인별점 클래스에 선언한 IndividualInfo
    Bitmap drawableID1;
    String title,star1;

    public IndividualInfo(Bitmap drawableID1, String title, String star1){ //개인별점에 출력할 변수
        this.drawableID1 = drawableID1;
        this.title = title;
        this.star1 = star1;
    }
}
