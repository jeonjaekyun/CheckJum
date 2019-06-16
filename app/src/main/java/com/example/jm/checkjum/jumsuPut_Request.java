package com.example.jm.checkjum;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-10-25.
 */

public class jumsuPut_Request extends StringRequest {
    final static private String URL = "http://ipAddress/JumsuPut.php";
    private Map<String,String> parameters;

    public jumsuPut_Request(String userID, String cover, String title, float jumsu,  Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("cover",cover);
        parameters.put("title",title);
        parameters.put("jumsu",new Float(jumsu).toString());
    }
    public Map<String, String> getParams() {
        return parameters;
    }
}
