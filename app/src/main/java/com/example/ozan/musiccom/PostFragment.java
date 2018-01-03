package com.example.ozan.musiccom;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class PostFragment extends Fragment {


    public PostFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_post, container, false);

        final EditText etTitle = (EditText) v.findViewById(R.id.etTitle);
        final EditText etArtist = (EditText) v.findViewById(R.id.etArtist);
        final TextView tvHiddenGenre = (TextView) v.findViewById(R.id.tvHiddenGenre);

        final RadioButton rbPop = (RadioButton) v.findViewById(R.id.rbPop);
        final RadioButton rbHiphop = (RadioButton) v.findViewById(R.id.rbHiphop);
        final RadioButton rbRock = (RadioButton) v.findViewById(R.id.rbRock);

        final Button btPost = (Button) v.findViewById(R.id.btPost);

        rbPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvHiddenGenre.setText("");
                rbHiphop.setChecked(false);
                rbRock.setChecked(false);
                tvHiddenGenre.setText("Pop");
            }
        });

        rbHiphop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvHiddenGenre.setText("");
                rbPop.setChecked(false);
                rbRock.setChecked(false);
                tvHiddenGenre.setText("HipHop");
            }
        });

        rbRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvHiddenGenre.setText("");
                rbPop.setChecked(false);
                rbHiphop.setChecked(false);
                tvHiddenGenre.setText("Rock");
            }
        });



        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = etTitle.getText().toString();
                final String artist = etArtist.getText().toString();
                final String genre = tvHiddenGenre.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                startActivity(getActivity().getIntent());

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Post failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                PostRequest postRequest = new PostRequest(title, artist, genre, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(postRequest);
            }
        });

        return v;
    }
}
