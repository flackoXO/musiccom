package com.example.ozan.musiccom;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends StringRequest {

    private static final String POST_REQUEST_URL = "http://xotwod.000webhostapp.com/Post.php";

    private Map<String, String> params;

    public PostRequest(String title, String artist, String genre, Response.Listener<String> Listener){

        super(Method.POST, POST_REQUEST_URL, Listener,null);
        params = new HashMap<>();
        params.put("title", title);
        params.put("artist", artist);
        params.put("genre", genre);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

