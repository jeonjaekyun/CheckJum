package com.example.jm.checkjum;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-09-24.
 */

public class Validate_Request extends StringRequest {
    final  static private String URL = "http://ipAddress/UserValidate.php";
    private Map<String,String> parameters;

    public Validate_Request(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("userID",userID);
    }
    public Map<String, String> getParams() {
        return parameters;
    }
}
