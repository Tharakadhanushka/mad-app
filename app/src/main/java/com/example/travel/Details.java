package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Details extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    TextView titleTextView;
    String country;
    int imageResourse;

    private CityAdapter placesFilteredAdapter;
    private RecyclerView placesByCityRecyclerView;

    FloatingActionButton addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        imageView=findViewById(R.id.image);
        titleTextView=findViewById(R.id.titleTextView);
        country=getIntent().getStringExtra("titleTextView");
        imageResourse=getIntent().getIntExtra("image",0);
        titleTextView.setText(country);
        imageView.setImageResource(imageResourse);

        placesByCityRecyclerView=findViewById(R.id.recycle_View_deta);


        addbtn=(FloatingActionButton) findViewById(R.id.notepadbutton);
        addbtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Details.this, NoteActivity.class);
        startActivity(intent);
    }
}
