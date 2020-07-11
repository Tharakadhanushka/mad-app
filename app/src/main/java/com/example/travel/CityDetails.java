package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CityDetails extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView1;
    TextView title1TextView;
    TextView populationTextView;
    TextView citydescTextView;
    TextView airportTextView;
    String city;
    int imageResourse1;
    String population;
    String citydesc;
    String airport;

    //weather
    TextView view_temp;
    TextView view_desc;
    ImageView view_weather;

    FloatingActionButton addbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citydetails);


        imageView1=findViewById(R.id.image1);
        title1TextView=findViewById(R.id.title1TextView);
        populationTextView=findViewById(R.id.populationTextView);
        citydescTextView = findViewById(R.id.citydescTextView);
        airportTextView=findViewById(R.id.airportTextView);
        city=getIntent().getStringExtra("title1TextView");
        imageResourse1=getIntent().getIntExtra("image1",0);
        population=getIntent().getStringExtra("populationTextView");
        citydesc =getIntent().getStringExtra("citydescTextView");
        airport=getIntent().getStringExtra("airportTextView");
        title1TextView.setText(city);
        imageView1.setImageResource(imageResourse1);
        populationTextView.setText(population);
        citydescTextView.setText(citydesc);
        airportTextView.setText(airport);

       //weather

        view_temp=findViewById(R.id.temp);
        view_temp.setText("");
        view_desc=findViewById(R.id.desc);
        view_desc.setText("");

        view_weather=findViewById(R.id.wheather_image);

        api_key();


        //note pad
        addbtn=(FloatingActionButton) findViewById(R.id.notepadbutton);
        addbtn.setOnClickListener(this);




    }

    @Override
    protected void onStart(){
        super.onStart();   //extend from appcompactactivity
        Log.i("city","onStart");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i("city","onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("city","onPause");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("city","onDestroy");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("city","onStop");

    }
    //City map button
    public void goCityMap(View view){


        Intent googleMapIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/place/"+city+"/"));
               // Uri.parse("https://www.google.lk/maps/dir/"+city+"/panadura/"));

        startActivity(googleMapIntent);
    }
    //Airport map button
    public void goAirport(View view){

        Intent googleMapIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.lk/maps/place/"+airport+"/"));


        startActivity(googleMapIntent);
    }

    //wiki button
    public void goWiki(View view){
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/"+city+"")
        );
        startActivity(browserIntent);
    }


    //weather

    private void api_key() {


        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=8d861b87d2e1b22f60f62051142fa763&units=metric")
                .get()
                .build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Response response= client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData= response.body().string();
                    try {
                        JSONObject json=new JSONObject(responseData);
                        JSONArray array=json.getJSONArray("weather");
                        JSONObject object=array.getJSONObject(0);

                        String description=object.getString("description");
                        String icons = object.getString("icon");

                        JSONObject temp1= json.getJSONObject("main");
                        Double Temperature=temp1.getDouble("temp");


                        String temps=Math.round(Temperature)+" °C";
                        setText(view_temp,temps);
                        setText(view_desc,description);
                        setImage(view_weather,icons);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    private void setText(final TextView text, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }
    private void setImage(final ImageView imageView, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //paste switch
                switch (value){
                    case "01d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "01n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "02d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "02n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "03d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                        break;
                    case "03n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                        break;
                    case "04d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "04n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "09d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "09n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "10d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "10n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "11d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                        break;
                    case "11n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                        break;
                    case "13d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                        break;
                    case "13n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                        break;
                    default:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));

                }
            }
        });
    }






// notepad button
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(CityDetails.this, NoteActivity.class);
        startActivity(intent);
    }
}
