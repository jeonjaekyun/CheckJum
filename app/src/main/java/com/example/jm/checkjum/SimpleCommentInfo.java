package com.example.jm.checkjum;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018-11-02.
 */

public class SimpleCommentInfo {  //개인별점 클래스에 선언한 IndividualInfo
    Bitmap drawableID1;
    String title,comment,star;

    public SimpleCommentInfo(Bitmap drawableID1, String title, String comment, String star){ //개인별점에 출력할 변수
        this.drawableID1 = drawableID1;
        this.title = title;
        this.comment = comment;
        this.star = star;
    }
}
