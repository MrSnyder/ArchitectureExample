package com.example.android.architectureexample.remote;

import com.example.android.architectureexample.Note;

import org.w3c.dom.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    @GET("notes")
    Call<List<Note>> getNotes();

//    @GET("notes/{id}")
//    Call<Note> getNote(@Path("id") int noteId);
}