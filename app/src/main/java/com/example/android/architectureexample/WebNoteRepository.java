package com.example.android.architectureexample;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.architectureexample.remote.APIService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebNoteRepository {

    Application application;

    List<Note> webserviceResponseList = new ArrayList<>();

    public WebNoteRepository(Application application) {
        this.application = application;
    }

    private static OkHttpClient providesOkHttpClientBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
    }

    public LiveData<List<Note>> getAllNotes() {

        final MutableLiveData<List<Note>> data = new MutableLiveData<>();

        String response = "";
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://127.0.0.1:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            //Defining retrofit api service
            APIService service = retrofit.create(APIService.class);
            //  response = service.makeRequest().execute().body();
            service.getNotes().enqueue(new Callback<List<Note>>() {
                @Override
                public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                    Log.d("Repository", "Response::::" + response.body());
//                    webserviceResponseList = parseJson(response.body());
//                    PostRoomDBRepository postRoomDBRepository = new PostRoomDBRepository(application);
//                    postRoomDBRepository.insertPosts(webserviceResponseList);
//                    data.setValue(webserviceResponseList);
                }

                @Override
                public void onFailure(Call<List<Note>> call, Throwable t) {
                    Log.d("Repository", "Failed:::");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  return retrofit.create(Note.class);
        return data;

    }
//
//
//    private List<Note> parseJson(String response) {
//
//        List<Note> apiResults = new ArrayList<>();
//
//        JSONObject jsonObject;
//
//        JSONArray jsonArray;
//
//        try {
//            jsonArray = new JSONArray(response);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject object = jsonArray.getJSONObject(i);
//
//                Note mMovieModel = new Note();
//                //mMovieModel.setId(object.getString("id"));
//                mMovieModel.setId(Integer.parseInt(object.getString("id")));
//                mMovieModel.setTitle(object.getString("title"));
//                mMovieModel.setBody(object.getString("body"));
//
//                apiResults.add(mMovieModel);
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
//        return apiResults;
//
//    }
}
