package com.bitsplease.blackout_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TimelineActivity extends AppCompatActivity {

    private ArrayList<DisplayObject> listOfDisplayObjects;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline);

        InitRecyclerView();

        //Intent intent = getIntent();
        //String message = intent.getStringExtra("DisplayList");

          listOfDisplayObjects = new ArrayList<>();
          listOfDisplayObjects = (ArrayList<DisplayObject>) getIntent().getSerializableExtra("DisplayList");


    //      InitializeData();

        InitializeAdapter();
        //SortDisplayObject(listOfDisplayObjects);

    }

    void InitRecyclerView()
    {
        rv = (RecyclerView)findViewById(R.id.rv);
        //rv.setHasFixedSize(true); // only if recycler view size is not changing
        LinearLayoutManager llm = new LinearLayoutManager(this); //this line may cause an error, not sure if using this is correct
        rv.setLayoutManager(llm);
    }

    void InitializeAdapter()
    {
        RVAdapter adapter = new RVAdapter(listOfDisplayObjects);
        rv.setAdapter(adapter);
    }



   /* void SortDisplayObject(ArrayList<DisplayObject> DisplayIntent)
    {
        Collections.sort(DisplayIntent, new Comparator<DisplayObject>() {
            @Override
            public int compare(DisplayObject lhs, DisplayObject rhs) {
                return rhs.getTime().get(0).compareTo(lhs.getTime().get(0));
            }
        });
    }*/

    private void InitializeData() {
        listOfDisplayObjects = new ArrayList<>();
        listOfDisplayObjects.add(new DisplayObject("SMS","02:30:45", "This is a very long text", R.drawable.sms));
        listOfDisplayObjects.add(new DisplayObject("Facebook", "03:00:00","This is a very long post", R.drawable.sms));
        listOfDisplayObjects.add(new DisplayObject("Twitter", "01:40:50","This is a very long tweet", R.drawable.sms));
    }
}
