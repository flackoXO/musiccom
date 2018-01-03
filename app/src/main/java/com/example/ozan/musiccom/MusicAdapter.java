package com.example.ozan.musiccom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private Context context;

    List<MusicData> musicDataList;

    public MusicAdapter(Context context, List<MusicData> musicDataList) {

        this.context = context;
        this.musicDataList = musicDataList;
    }

    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicAdapter.ViewHolder viewHolder, int i) {

        viewHolder.title.setText(musicDataList.get(i).title);
        viewHolder.artist.setText(musicDataList.get(i).artist);
        viewHolder.genre.setText(musicDataList.get(i).genre);
    }

    @Override
    public int getItemCount() {

        return musicDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, artist, genre;

        public ViewHolder(final View view) {

            super(view);

            title = (TextView) view.findViewById(R.id.tvTitle);
            artist = (TextView) view.findViewById(R.id.tvArtist);
            genre = (TextView) view.findViewById(R.id.tvGenre);
        }
    }
}


