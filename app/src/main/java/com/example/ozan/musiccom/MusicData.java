package com.example.ozan.musiccom;

public class MusicData {
    public String title;
    public String artist;
    public String genre;

    @Override
    public  String toString() {
        return "Title: " + title + " | Artist: " + artist + " | Genre: " + genre;
    }
}
