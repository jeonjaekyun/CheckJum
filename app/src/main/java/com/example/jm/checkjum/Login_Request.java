package com.example.jm.checkjum;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-09-24.
 */

public class Login_Request extends StringRequest {

    final static  private String URL = "http://ipAddress/UserLogin.php";
    private Map<String,String> parameters;

    public Login_Request(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword",userPassword);
    }

    public Map<String, String> getParams() {return parameters;}
}
