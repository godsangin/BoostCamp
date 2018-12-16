package com.msproject.myhome.boostcamp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.MyViewHolder>{
    Context mContext;
    List<Integer> items;
    int i = 0;

    public TempAdapter(Context c, List<Integer> items, int i){
        this.mContext = c;
        this.items = items;
        this.i = i;
    }

    @NonNull
    @Override
    public TempAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temp_item, viewGroup, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull TempAdapter.MyViewHolder viewHolder, int i) {
        final Integer item = items.get(i);
        viewHolder.tv.setText(""+item);

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

}
