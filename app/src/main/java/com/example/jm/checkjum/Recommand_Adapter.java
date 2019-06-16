package com.example.jm.checkjum;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 612-1 on 2018-05-31.
 */
//리사이클러뷰 클래스 http://dreamaz.tistory.com/345 참고
public class Recommand_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    //recommandlist.xml에 뷰들을 받아옴
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView bookcover;
        TextView title, writer, publisher;
        RatingBar star;

        MyViewHolder(View view){
            super(view);
            bookcover = view.findViewById(R.id.re_bookcover);
            title = view.findViewById(R.id.re_title);
            writer = view.findViewById(R.id.re_writer);
            publisher = view.findViewById(R.id.re_publisher);
            star = view.findViewById(R.id.re_star);
        }
    }

    private ArrayList<BookInfo> bookInfoArrayList;
    Recommand_Adapter(ArrayList<BookInfo> bookInfoArrayList){
        this.bookInfoArrayList = bookInfoArrayList;
    }

    //recommandlist.xml를 리사이클러뷰 디자인으로 지정
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommandlist, parent, false);

        return new MyViewHolder(v);
    }


    //리사이클러뷰에 각각에 정보를 set해줌
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.bookcover.setImageBitmap(bookInfoArrayList.get(position).drawableId);
        myViewHolder.title.setText("제목 : "+bookInfoArrayList.get(position).title);
        myViewHolder.writer.setText("저자 : "+bookInfoArrayList.get(position).writer);
        myViewHolder.publisher.setText("출판사 : "+bookInfoArrayList.get(position).publisher);
        myViewHolder.star.setRating(Float.parseFloat(bookInfoArrayList.get(position).star)/2);


        //책표지 클릭시 도서정보로 이동
        myViewHolder.bookcover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Book.class);
                intent.putExtra("title",bookInfoArrayList.get(position).title);
                view.getContext().startActivity(intent);
            }
        });
    }

    //전체 리스트 항목의 갯수를 반환
    @Override
    public int getItemCount() {
        return bookInfoArrayList.size();
    }
}

