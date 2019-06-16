package com.example.jm.checkjum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by woavm on 2018-06-01.
 */

public class IndividualStar_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {  //개인별점 클래스에 선언된 IndividualStar_Adapter 클래스

    String flag = "personal_star";

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView b_star_cover;
        TextView b_star_name,b_star1;

        MyViewHolder(View view){ //개인별점 xml에 선언된 변수를 가져옴
            super(view);
            b_star_cover = view.findViewById(R.id.b_star_cover);
            b_star_name = view.findViewById(R.id.b_star_name);
            b_star1 = view.findViewById(R.id.b_star1);
        }
    }
    private ArrayList<IndividualInfo> individualInfoArrayList;
    IndividualStar_Adapter(ArrayList<IndividualInfo> individualInfoArrayList){
        this.individualInfoArrayList = individualInfoArrayList;
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklist_star,parent,false);
        return new MyViewHolder(view);
    }
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.b_star_cover.setImageBitmap(individualInfoArrayList.get(position).drawableID1);
        myViewHolder.b_star_name.setText(individualInfoArrayList.get(position).title);
        myViewHolder.b_star1.setText("별점 : "+individualInfoArrayList.get(position).star1);

        //책표지 클릭시 도서정보로 이동
        myViewHolder.b_star_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Book.class);
                intent.putExtra("title",individualInfoArrayList.get(position).title);
                view.getContext().startActivity(intent);
            }
        });

        myViewHolder.b_star_cover.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("삭제하시겠습니까?");
                dlg.setNegativeButton("아니오", null);
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        Toast.makeText(view.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Delete_Request deleteRequest = new Delete_Request(individualInfoArrayList.get(position).title, flag, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(view.getContext());
                        queue.add(deleteRequest);
                        Intent intent = new Intent(view.getContext(), IndividualStar.class);
                        intent.putExtra("order","title");
                        view.getContext().startActivity(intent);
                    }
                });
                dlg.show();
                return true;
            }
        });
    }
    public int getItemCount(){
        return individualInfoArrayList.size();
    }
}
