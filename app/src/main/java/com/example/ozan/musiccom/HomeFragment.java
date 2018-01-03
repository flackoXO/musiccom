package com.example.ozan.musiccom;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;

    private String TAG = HomeActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    // URL to get contacts JSON
    private static String url = "http://xotwod.000webhostapp.com/Blog.php";

    List<MusicData> musicDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        musicAdapter = new MusicAdapter(getContext(), null);
        recyclerView.setAdapter(musicAdapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetMusic().execute();
    }

    class GetMusic extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            boolean fetched = false;
            try {
                JSONArray posts = HttpHandler.fetchJSONArrayFromUrl(url);
                musicDataList = new ArrayList<>();
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject p = posts.getJSONObject(i);
                    MusicData m = new MusicData();
                    m.title = p.getString("title");
                    m.artist = p.getString("artist");
                    m.genre = p.getString("genre");
                    Log.d(TAG, m.toString());
                    musicDataList.add(m);
                }
                fetched = false;
            } catch (IOException e) {
                Log.e(TAG, "IOException when fetching posts from url: " + e.getMessage());
            } catch (JSONException e) {
                Log.e(TAG, "JSONException when fetching posts from url: " + e.getMessage());
            }
            return fetched;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result) {
                Log.d(TAG, "Successfully fetched posts");
                musicAdapter.replaceMusicDataList(musicDataList);
            } else {
                Log.d(TAG, "Failed to fetch posts");
            }
        }

    }

    /*
    public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {


        private MusicData musicData;

        List<MusicData> musicDataList;

        public MusicAdapter(MusicData musicData, List<MusicData> musicDataList) {

            this.musicData = musicData;
            this.musicDataList = musicDataList;
        }

        @Override
        public MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MusicAdapter.ViewHolder viewHoler, int i) {

            viewHoler.title.setText(musicDataList.get(i).title);
            viewHoler.artist.setText(musicDataList.get(i).artist);
            viewHoler.genre.setText(musicDataList.get(i).genre);
        }

        @Override
        public int getItemCount() {

            return musicDataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView title, artist, genre;

            public ViewHolder(final View view) {

                super(view);

                title = (TextView) itemView.findViewById(R.id.tvTitle);
                artist = (TextView) itemView.findViewById(R.id.tvArtist);
                genre = (TextView) itemView.findViewById(R.id.tvGenre);
            }
        }
    }
    */

}

