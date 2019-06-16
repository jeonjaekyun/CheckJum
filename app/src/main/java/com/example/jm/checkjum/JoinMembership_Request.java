package com.example.jm.checkjum;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-09-23.
 */

public class JoinMembership_Request extends StringRequest{

    final static private String URL = "http://ipAddress/UserRegister.php";
    private Map<String,String> parameters;

    public JoinMembership_Request(String userName, String userID, String userPassword, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userName",userName);
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userEmail",userEmail);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
