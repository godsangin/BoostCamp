package com.msproject.myhome.boostcamp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    Button searchButton;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.input_text);
        searchButton = findViewById(R.id.search_bt);
        mRecyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonString = "";
                LoadingThread loadingThread = new LoadingThread(inputText.getText().toString());
                loadingThread.start();
                synchronized (loadingThread){//쓰레드가 데이터를 전부 받아올 때까지 기다린다.
                    try {//json으로 변환
                        loadingThread.wait();
                        jsonString = loadingThread.getJsonString();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        Log.d("JsonString==", jsonString);
                        try {//json을 파싱하여 movie객체 선언
                            ArrayList<Movie> movies = new ArrayList<>();
                            JSONObject wholeObject = new JSONObject(jsonString);
                            JSONArray items = wholeObject.getJSONArray("items");

                            for(int i = 0; i < items.length(); i++){
                                JSONObject jObject = items.getJSONObject(i);
                                String title=  jObject.getString("title");
                                String link = jObject.getString("link");
                                String imageURL = jObject.getString("image");
                                String pubDate = jObject.getString("pubDate");
                                String director = jObject.getString("director");
                                String actor = jObject.getString("actor");
                                double userRate = jObject.getDouble("userRating");
                                Movie movie = new Movie(title, userRate, director, pubDate, actor, imageURL, link);
                                movies.add(movie);
                            }
                            MyAdapter myAdapter = new MyAdapter(movies);

                            mRecyclerView.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
//                        JSONArray jArray = new JSONArray(jsonString);
//                        for(int i = 0; i < jArray.length(); i++){
//                            JSONObject jObject = jArray.getJSONObject(i);
//                            JSONObject items = jObject.getJSONObject("items");
//                            Log.d("items==", items.toString());
//                        }
                            for(int i = 0; i < movies.size(); i++){
                                Log.d("movie==", movies.get(i).toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {

                        }
                    }

                }
            }
        });


    }


}
