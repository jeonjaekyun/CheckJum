package com.example.jm.checkjum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

//로그인 화면 설계를 위한 class
public class Login extends AppCompatActivity {

    TextView join_membership, id_pw_find;
    AlertDialog dialog;
    static public String userIdKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("로그인");

        join_membership = findViewById(R.id.join_membership);

        join_membership.setOnClickListener(new View.OnClickListener() {//회원가입 클릭시
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinMembership.class);
                startActivity(intent);
            }
        });

        //로그인 기능(db연동)
        final EditText etId = findViewById(R.id.id_Input);
        final EditText etPasswd = findViewById(R.id.pw_Input);
        final Button btnLogin = findViewById(R.id.login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = etId.getText().toString();
                final String userPasswd = etPasswd.getText().toString();

                userIdKey = userID;

                /*Toast.makeText(Login.this,"로그인에 성공했습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
                finish();*/
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                Toast.makeText(Login.this,"로그인에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Main.class);
                                intent.putExtra("userID",userID);
                                startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                dialog = builder.setMessage("계정을 다시 확인하세요.").setNegativeButton("다시 시도",null).create();
                                dialog.show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                Login_Request loginRequest = new Login_Request(userID, userPasswd, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}