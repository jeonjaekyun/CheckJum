package com.example.jm.checkjum;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;



/**
 * Created by JM on 2018-05-22.
 */

public class Main extends AppCompatActivity {

    F_checkjum f_checkjum;
    F_library f_library;
    F_personal f_personal;

    ImageView checkjum, library, personal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("메인");
        getSupportActionBar().hide();
        f_checkjum = new F_checkjum();
        f_library = new F_library();
        f_personal = new F_personal();

        checkjum = findViewById(R.id.checkjum);
        library = findViewById(R.id.library);
        personal = findViewById(R.id.personal);

        //Main화면 실행시 책점(f_ckeckjum)이 기본으로 실행됨
        checkjum.setColorFilter(Color.parseColor("#56b1ef"));
        library.setColorFilter(Color.BLACK);
        personal.setColorFilter(Color.BLACK);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, f_checkjum).commit();

        //도서정보(Book.xml)에서 도서관검색 클릭시 도서관검색(f_library)화면으로 넘어감
        Intent inIntent = getIntent();
        int maink = inIntent.getIntExtra("maink", 0);
        if (maink == 1) {
            library.setColorFilter(Color.parseColor("#56b1ef"));
            checkjum.setColorFilter(Color.BLACK);
            personal.setColorFilter(Color.BLACK);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, f_library).commit();
        }else if(maink==2){
            personal.setColorFilter(Color.parseColor("#56b1ef"));
            checkjum.setColorFilter(Color.BLACK);
            library.setColorFilter(Color.BLACK);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, f_personal).commit();
        }else{
            checkjum.setColorFilter(Color.parseColor("#56b1ef"));
            library.setColorFilter(Color.BLACK);
            personal.setColorFilter(Color.BLACK);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, f_checkjum).commit();
        }


        //클릭시 추천도서 화면으로 변경
        checkjum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkjum.setColorFilter(Color.parseColor("#56b1ef"));
                library.setColorFilter(Color.BLACK);
                personal.setColorFilter(Color.BLACK);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, f_checkjum).commit();
            }
        });

        //클릭시 도서관검색 화면으로 변경
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                library.setColorFilter(Color.parseColor("#56b1ef"));
                checkjum.setColorFilter(Color.BLACK);
                personal.setColorFilter(Color.BLACK);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, f_library).commit();
            }
        });

        //클릭시 마이페이지 화면으로 변경
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personal.setColorFilter(Color.parseColor("#56b1ef"));
                checkjum.setColorFilter(Color.BLACK);
                library.setColorFilter(Color.BLACK);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, f_personal).commit();
            }
        });
    }

    public void night(){

    }


}
