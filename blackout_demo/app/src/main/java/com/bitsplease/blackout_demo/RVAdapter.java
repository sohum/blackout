package com.bitsplease.blackout_demo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sunny on 2016-06-21.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DisplayObjectHolder> {
    ArrayList<DisplayObject> listOfDisplayObjects;

    public static class DisplayObjectHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView type;
        TextView time;
        TextView text;
        ImageView photo;

        DisplayObjectHolder(View itemView)
        {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setUseCompatPadding(true);
            type = (TextView)itemView.findViewById(R.id.display_type);
            time = (TextView)itemView.findViewById(R.id.display_time);
            text = (TextView)itemView.findViewById(R.id.display_text);
            photo = (ImageView)itemView.findViewById(R.id.display_photo);

        }

    }

    RVAdapter(ArrayList<DisplayObject> listOfDisplayObjects)
    {
        this.listOfDisplayObjects = listOfDisplayObjects;
    }

    @Override
    public int getItemCount() {
        return listOfDisplayObjects.size();
    }

    @Override
    public DisplayObjectHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        DisplayObjectHolder dov = new DisplayObjectHolder(v);
        return dov;
    }

    @Override
    public void onBindViewHolder(DisplayObjectHolder displayObjectHolder, int i) {
        displayObjectHolder.type.setText(listOfDisplayObjects.get(i).type);
        displayObjectHolder.time.setText(listOfDisplayObjects.get(i).time);
        displayObjectHolder.text.setText(listOfDisplayObjects.get(i).text);
        displayObjectHolder.photo.setImageResource(listOfDisplayObjects.get(i).photoId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
