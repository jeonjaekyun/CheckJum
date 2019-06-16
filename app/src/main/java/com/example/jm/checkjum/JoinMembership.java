package com.example.jm.checkjum;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

//회원가입1 클래스
public class JoinMembership extends AppCompatActivity {

    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next,menu);
        return true;

    }
    //액션버튼을 클릭했을때 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){ //이전아이콘 클릭시 로그인 화면으로 이동
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ////회원가입 db기능구현 부분 시작
    private ArrayAdapter adapter;
    private AlertDialog dialog;
    private boolean validate = false;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_membership);
        setTitle("회원가입");

        final EditText etname = findViewById(R.id.jmname_edittext);
        final EditText etId = findViewById(R.id.jmid_edittext);
        final EditText etPasswd = findViewById(R.id.jmpw_edittext);
        final EditText etrePasswd = findViewById(R.id.jmrepw_edittext);
        final EditText emailTxt = findViewById(R.id.jmemail_edittext);
        final Button btnValidate = findViewById(R.id.btnValidate);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = etId.getText().toString();
                Log.d("ddddddddddddddd", userID);
                if (userID.trim().equals(""))
                {
                    Toast.makeText(JoinMembership.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etId.requestFocus();
                    return;
                }

                Response.Listener responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean newID = jsonResponse.getBoolean("newID");
                            Log.d("mytest", jsonResponse.toString());

                            if (newID) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinMembership.this);

                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인",null).create();
                                dialog.show();
                                etId.setEnabled(false);
                                etId.setBackgroundColor(Color.GRAY);
                                btnValidate.setBackgroundColor(Color.GRAY);
                                validate = true;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinMembership.this);

                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                Validate_Request validateRequest = new Validate_Request(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinMembership.this);
                queue.add(validateRequest);
            }
        });

        Button btnMembership = findViewById(R.id.membership_btn);
        btnMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etname.getText().toString();
                String userID = etId.getText().toString();
                String userPassword = etPasswd.getText().toString();
                String userEmail = emailTxt.getText().toString();
                String userRepassword = etrePasswd.getText().toString();


                if (!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinMembership.this);

                    dialog = builder.setMessage("중복체크를 해주세요.").setNegativeButton("확인",null).create();
                    dialog.show();
                    return;
                }
                if (userName.equals("") || userID.equals("") || userPassword.equals("") || userEmail.equals("") || userRepassword.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinMembership.this);

                    dialog = builder.setMessage("모든 정보를 입력해주세요.").setNegativeButton("확인",null).create();
                    dialog.show();
                    return;
                }

                if (!userPassword.equals(userRepassword)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinMembership.this);
                    etPasswd.setText("");
                    etrePasswd.setText("");
                    etPasswd.requestFocus();
                    dialog = builder.setMessage("비밀번호와 비밀번호 확인이 같지 않습니다 확인해주세요.").setNegativeButton("확인",null).create();
                    dialog.show();
                    return;
                }

                Response.Listener responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("mytest", response);
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinMembership.this);

                                dialog = builder.setMessage("회원으로 등록되었습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).create();
                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinMembership.this);

                                dialog = builder.setMessage("회원 등록에 실패했습니다.").setMessage("회원 등록에 실패했습니다.").setNegativeButton("확인",null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                JoinMembership_Request registerRequest = new JoinMembership_Request(userName, userID, userPassword, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinMembership.this);
                queue.add(registerRequest);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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