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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private View myFragmentView;

    private String TAG = HomeActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    // URL to get contacts JSON
    private static String url = "http://xotwod.000webhostapp.com/Blog.php";

    List<MusicData> musicDataList = new ArrayList<>();
    public MusicData musicData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        new GetMusic().execute();

         /*recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        final TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        final TextView tvArtist = (TextView) v.findViewById(R.id.tvArtist);
        final TextView tvGenre = (TextView) v.findViewById(R.id.tvGenre);

        tvTitle.setText(b.getString("title"));
        tvArtist.setText(b.getString("artist"));
        tvGenre.setText(b.getString("artist"));*/

        return myFragmentView;
    }

    class GetMusic extends AsyncTask<Void, Void, Void> {

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
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray post = jsonObj.getJSONArray("post");

                    // looping through All tracks
                    for (int i = 0; i < post.length(); i++) {
                        JSONObject p = post.getJSONObject(i);
                        MusicData m = new MusicData();
                        m.title = p.getString("title");
                        m.artist = p.getString("artist");
                        m.genre = p.getString("genre");

                        musicDataList.add(musicData);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            pDialog.dismiss();

            recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            musicAdapter = new MusicAdapter(getActivity(), musicDataList);
            recyclerView.setAdapter(musicAdapter);
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

