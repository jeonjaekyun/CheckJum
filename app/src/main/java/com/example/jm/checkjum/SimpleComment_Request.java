package com.example.jm.checkjum;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-10-25.
 */

public class SimpleComment_Request extends StringRequest {
    final static private String URL = "http://ipAddress/Simple_comment.php";
    private Map<String,String> parameters;

    public SimpleComment_Request(String userID, String cover, String title, String comment , String star, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("cover",cover);
        parameters.put("title",title);
        parameters.put("comment",comment);
        parameters.put("star",star);
    }
    public Map<String, String> getParams() {
        return parameters;
    }
}
