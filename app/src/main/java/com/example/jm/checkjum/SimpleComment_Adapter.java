package com.example.jm.checkjum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class SimpleComment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {  //한줄평 클래스에 선언된 SimpleComment_Adapter 클래스

    Context context;
    String flag = "comment";
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView b_star_cover;
        TextView b_star_name,b_comment,b_star_star;

        MyViewHolder(View view){ //한줄평 xml에 선언된 변수를 가져옴
            super(view);
            b_star_cover = view.findViewById(R.id.sc_cover);
            b_star_name = view.findViewById(R.id.sc_name);
            b_star_star = view.findViewById(R.id.sc_star);
            b_comment = view.findViewById(R.id.simple_comment);
        }
    }
    private ArrayList<SimpleCommentInfo> simple_commentinfos;
    SimpleComment_Adapter(ArrayList<SimpleCommentInfo> simple_commentinfos, Context context){
        this.simple_commentinfos = simple_commentinfos;
        this.context = context;
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_comment_list,parent,false);
        return new MyViewHolder(view);
    }
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.b_star_cover.setImageBitmap(simple_commentinfos.get(position).drawableID1);
        myViewHolder.b_star_name.setText(simple_commentinfos.get(position).title);
        myViewHolder.b_comment.setText("한줄평 : "+simple_commentinfos.get(position).comment);
        myViewHolder.b_star_star.setText("평점 : "+simple_commentinfos.get(position).star);

        //책표지 클릭시 도서정보로 이동

        myViewHolder.b_star_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Book.class);
                intent.putExtra("title",simple_commentinfos.get(position).title);
                view.getContext().startActivity(intent);
            }
        });

        myViewHolder.b_star_cover.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("181030", simple_commentinfos.get(position).title);
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
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
                                        Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Delete_Request deleteRequest = new Delete_Request(simple_commentinfos.get(position).title, flag, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(deleteRequest);
                        Intent intent = new Intent(context, SimpleComment.class);
                        intent.putExtra("order","title");
                        context.startActivity(intent);
                    }
                });
                dlg.show();
                return true;
            }
        });
    }
    public int getItemCount(){
        return simple_commentinfos.size();
    }
}
