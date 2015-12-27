package com.rdzero.nuproject.net;

/**
 * Custom implementation of Json Volley Request Queue
 * Used from http://www.truiton.com/2015/02/android-volley-example/
 */

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CustomJSONArrayRequest extends JsonArrayRequest {

    public CustomJSONArrayRequest(int method, String url, JSONArray jsonRequest,
                                  Response.Listener<JSONArray> listener,
                                  Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // here you can write a custom retry policy, but i won`t
        return super.getRetryPolicy();
    }
}