package com.example.travel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    // List to store all the  details
    private Context context;
    private List<FavItem> favItemList;
    private TravelDB travelDB;

    // Counstructor
    public FavAdapter(Context context, List<FavItem> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
    }


    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout view you have created for the list rows here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false);
        travelDB = new TravelDB(context);
        return new ViewHolder(view);
    }




    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.favTextView.setText(favItemList.get(position).getItem_title());   // Set the data to the views here
        holder.favImageView.setImageResource(favItemList.get(position).getItem_image());

        //set on click in favourite countries
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Details.class);
                intent.putExtra("image",favItemList.get(position).getItem_image());
                intent.putExtra("titleTextView",favItemList.get(position).getItem_title());

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView favTextView;
        Button favBtn;
        ImageView favImageView;
        RelativeLayout relative;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favTextView = itemView.findViewById(R.id.favTextView);
            favBtn = itemView.findViewById(R.id.favBtn);
            favImageView = itemView.findViewById(R.id.favImageView);
            relative = itemView.findViewById(R.id.relative);   //relative layout


            //after click remove from favorite
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final FavItem favItem = favItemList.get(position);
                    travelDB.remove_fav(favItem.getKey_id());
                    removeItem(position);
                }

              //methanta
            });
        }
    }
    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favItemList.size());
    }
}
