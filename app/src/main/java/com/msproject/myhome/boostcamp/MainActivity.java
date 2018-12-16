package com.msproject.myhome.boostcamp;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    LinearLayoutManager mLayoutManager;
    ArrayList<Movie> movies;
    MyAdapter myAdapter;
    GestureDetector gestureDetector;
    TextView connotfount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.input_text);
        searchButton = findViewById(R.id.search_bt);
        connotfount = findViewById(R.id.cannot_fount_text);
        mRecyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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
                            movies = new ArrayList<>();
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
                            ArrayList<Integer> a = new ArrayList<>();
                            for(int i = 0; i < 100; i++){
                                a.add(i);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            if(movies.size() == 0){
                                connotfount.setVisibility(View.VISIBLE);
                            }
                            else{
                                connotfount.setVisibility(View.GONE);
                            }
                            myAdapter = new MyAdapter(movies);

                            mRecyclerView.setAdapter(myAdapter);
                            gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener() {

                                //누르고 뗄 때 한번만 인식하도록 하기위해서
                                @Override
                                public boolean onSingleTapUp(MotionEvent e) {
                                    return true;
                                }
                            });



                            mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                                @Override
                                public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                                    View childView = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                                    //터치한 곳의 View가 RecyclerView 안의 아이템이고 그 아이템의 View가 null이 아니라
                                    //정확한 Item의 View를 가져왔고, gestureDetector에서 한번만 누르면 true를 넘기게 구현했으니
                                    //한번만 눌려서 그 값이 true가 넘어왔다면
                                    if(childView != null && gestureDetector.onTouchEvent(motionEvent)){

                                        //현재 터치된 곳의 position을 가져오고
                                        int currentPosition = recyclerView.getChildAdapterPosition(childView);

                                        //해당 위치의 Data를 가져옴
                                        Movie currentItem = movies.get(currentPosition);

                                        Intent intent = new Intent(MainActivity.this, WebViewActiviy.class);
                                        intent.putExtra("Movie", currentItem.getLink());
                                        startActivity(intent);
                                        return true;
                                    }
                                    return false;


                                }

                                @Override
                                public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                                }

                                @Override
                                public void onRequestDisallowInterceptTouchEvent(boolean b) {

                                }
                            });


                            for(int i = 0; i < movies.size(); i++){
                                Log.d("movie==", movies.get(i).toString());
                            }
                        }
                    }

                }
            }
        });


    }


}
