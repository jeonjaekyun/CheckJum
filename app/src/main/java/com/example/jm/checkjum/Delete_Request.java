package com.example.jm.checkjum;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Delete_Request extends StringRequest {

    final static private String URL = "http://ipAddress/DeleteData.php";
    private Map<String, String> parameters;

    public Delete_Request(String title, String flag, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("title", title);
        parameters.put("flag", flag);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}

