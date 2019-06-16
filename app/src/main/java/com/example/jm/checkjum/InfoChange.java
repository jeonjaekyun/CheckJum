package com.example.jm.checkjum;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by woavm on 2018-06-01.
 */

public class InfoChange extends AppCompatActivity { //개인정보수정 클래스
    Button change_btn;
    TextView now_id;
    EditText nowpw,newpw,newpw_chk;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(), Main.class);
            intent.putExtra("maink",2);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infochange);
        setTitle("개인정보수정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        change_btn = findViewById(R.id.change_btn);
        nowpw = findViewById(R.id.nowpw);
        newpw = findViewById(R.id.newpw);
        newpw_chk = findViewById(R.id.newpw_chk);

        ((TextView)findViewById(R.id.nowid_text)).setText(Login.userIdKey);


        change_btn.setOnClickListener(new View.OnClickListener() {  //변경하기 버튼 클릭시 처리
            @Override
            public void onClick(View view) {

                if (newpw.getText().toString().equals(newpw_chk.getText().toString())){
                    Response.Listener responseListnener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("test", response);

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(InfoChange.this);
                                    dlg.setTitle("알림");
                                    dlg.setMessage("개인정보 수정이 완료되었습니다");
                                    dlg.setIcon(R.drawable.ic_book_black_24dp);
                                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(InfoChange.this, Main.class);
                                            intent.putExtra("maink", 2);
                                            startActivity(intent);

                                        }
                                    });
                                    dlg.show();
                                } else {
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(InfoChange.this);
                                    dlg.setTitle("알림");
                                    dlg.setMessage("비밀번호가 일치하지 않습니다.");
                                    dlg.setIcon(R.drawable.ic_book_black_24dp);
                                    dlg.setPositiveButton("확인", null);
                                    dlg.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    InfoChange_Request infoadjust_Request = new InfoChange_Request(Login.userIdKey, nowpw.getText().toString(), newpw.getText().toString(), responseListnener);
                    RequestQueue queue = Volley.newRequestQueue(InfoChange.this);
                    queue.add(infoadjust_Request);
                } else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(InfoChange.this);
                    dlg.setTitle("알림");
                    dlg.setMessage("변경할 비밀번호와 변경할 비밀번호 확인이 맞지 않습니다.");
                    dlg.setIcon(R.drawable.ic_book_black_24dp);
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                }
            }
        });
    }
}
