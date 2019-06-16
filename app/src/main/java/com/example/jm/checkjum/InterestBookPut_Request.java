package com.example.jm.checkjum;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InterestBookPut_Request extends StringRequest {
    final static private String URL = "http://ipAddress/InterestBookPut.php";
    private Map<String,String> parameters;

    public InterestBookPut_Request(String userID, String cover, String title, String author, String publisher, String star,  Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("cover",cover);
        parameters.put("title",title);
        parameters.put("author",author);
        parameters.put("publisher",publisher);
        parameters.put("star",star);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}