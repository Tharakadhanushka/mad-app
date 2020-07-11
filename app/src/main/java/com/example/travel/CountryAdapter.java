package com.example.travel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private ArrayList<CountryItem> countryItems;
    private Context context;
    private TravelDB travelDB;

    public CountryAdapter(ArrayList<CountryItem> countryItems, Context context){
        this.countryItems = countryItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        travelDB = new TravelDB(context);
        //Create table first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        if(firstStart){
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        final CountryItem countryItem= countryItems.get(position);

        readCursorData(countryItem,holder);
        holder.imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(),countryItems.get(position).getImageResourse(),300,300));
        holder.titleTextView.setText(countryItem.getTitle());
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   //set on click for home countries
                Intent intent = new Intent(context,Details.class);
                intent.putExtra("image",countryItem.getImageResourse());
                intent.putExtra("titleTextView",countryItem.getTitle());

                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return countryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        Button favBtn;
        RelativeLayout relative;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView= itemView.findViewById(R.id.titleTextView);
            favBtn = itemView.findViewById(R.id.favBtn);
            relative = itemView.findViewById(R.id.relative);

            //add to fav button
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    CountryItem countryItem = countryItems.get(position);

                    if (countryItem.getFavStatus().equals("0")) {
                        countryItem.setFavStatus("1");
                        travelDB.insertIntoTheDatabase(countryItem.getTitle(), countryItem.getImageResourse(), countryItem.getKey_id(), countryItem.getFavStatus());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    }else{
                        countryItem.setFavStatus("0");
                        travelDB.remove_fav(countryItem.getKey_id());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                    }
                }

            });

        }
    }

    private void createTableOnFirstStart(){
        travelDB.insertEmpty();

        SharedPreferences prefs= context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
    private void readCursorData(CountryItem countryItem, ViewHolder viewHolder) {
        Cursor cursor = travelDB.read_all_data(countryItem.getKey_id());
        SQLiteDatabase db = travelDB.getReadableDatabase();
        try{
            while(cursor.moveToNext()){
                String item_fav_status = cursor.getString(cursor.getColumnIndex(TravelDB.FAVORITE_STATUS));
                countryItem.setFavStatus(item_fav_status);

                //check favourite status
                if (item_fav_status !=null && item_fav_status.equals("1")){
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);

                }else if (item_fav_status !=null && item_fav_status.equals("0")){
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                }
            }
        }finally {
            if (cursor !=null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }


}
