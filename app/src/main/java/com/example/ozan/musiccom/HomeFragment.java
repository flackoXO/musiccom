package com.example.ozan.musiccom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b  = getArguments();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView tvUsername = (TextView) v.findViewById(R.id.tvUsername);
        final TextView tvKind = (TextView) v.findViewById(R.id.tvKind);
        tvUsername.setText(b.getString("username"));
        tvKind.setText(b.getString("kind"));
        return v;
    }

}
