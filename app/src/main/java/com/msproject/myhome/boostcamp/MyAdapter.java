package com.msproject.myhome.boostcamp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView title;
        TextView year;
        TextView director;
        TextView actor;

        MyViewHolder(View view){
            super(view);
            movieImage = view.findViewById(R.id.movie_image);
            title = view.findViewById(R.id.title);
            year = view.findViewById(R.id.year);
            director = view.findViewById(R.id.director);
            actor = view.findViewById(R.id.actor);
        }
    }

    private ArrayList<Movie> movies;
    Bitmap bitmap;
    MyAdapter(ArrayList<Movie> movies){
        this.movies = movies;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.title.setText(movies.get(position).getTitle());
        myViewHolder.year.setText(movies.get(position).getYear());
        myViewHolder.director.setText(movies.get(position).getDirector());
        myViewHolder.actor.setText(movies.get(position).getActors());
        bitmap = null;


        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(movies.get(position).getImageURL());

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }catch (IOException e){

                }
            }
        };

        mThread.start();

        try{
            mThread.join();
            myViewHolder.movieImage.setImageBitmap(bitmap);
        }catch(InterruptedException e){

        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
