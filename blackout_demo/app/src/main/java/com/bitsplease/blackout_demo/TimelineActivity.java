package com.bitsplease.blackout_demo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        TextView txt = (TextView) findViewById(R.id.custom_font2);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ostrichregular.ttf");
        txt.setTypeface(font);

        InitRecyclerView();

        listOfDisplayObjects = new ArrayList<>();
        listOfDisplayObjects = (ArrayList<DisplayObject>) getIntent().getSerializableExtra("DisplayList");

        if(!listOfDisplayObjects.isEmpty()) {

            //If the list is empty, you see an
            View view = (View) findViewById(R.id.custom_font2);
            view.setVisibility(View.GONE);

            InitializeAdapter();
        }
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

}
