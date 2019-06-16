package com.example.jm.checkjum;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-11-14.
 */

public class InfoChange_Request extends StringRequest {
    final static private String URL = "http://ipAddress/InfoChange.php";
    private Map<String,String> parameters;

    public InfoChange_Request(String userID, String nowpw, String newpw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("nowpw",nowpw);
        parameters.put("newpw",newpw);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
