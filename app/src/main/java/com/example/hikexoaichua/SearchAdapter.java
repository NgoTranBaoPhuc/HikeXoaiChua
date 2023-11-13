package com.example.hikexoaichua;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHikeVH> implements FileFilter {

    ArrayList<HikeModel> hike;
    Context context;
    public SearchAdapter(ArrayList<HikeModel> hike, Context context){
        this.hike = hike;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchHikeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new SearchAdapter.SearchHikeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchHikeVH holder, int position) {
        HikeModel h = hike.get(position);
        holder.name_txt.setText(String.valueOf(h.getName()));
        holder.length_txt.setText(String.valueOf(h.getLength()));
        holder.location_txt.setText(String.valueOf(h.getLocation()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HikeDetail.class);
                intent.putExtra("id", h.getId());
                intent.putExtra("name", h.getName());
                intent.putExtra("location", h.getLocation());
                intent.putExtra("length", h.getLength());
                intent.putExtra("location", h.getLocation());
                intent.putExtra("date", h.getDate());
                intent.putExtra("difficulty", h.getDifficulty());
                intent.putExtra("description", h.getDescription());
                intent.putExtra("isParking", h.getIsParking());
                intent.putExtra("vehicle",h.getVehicle());

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {return hike.size();}

    @Override
    public boolean accept(File file) {
        return false;
    }

    class SearchHikeVH extends RecyclerView.ViewHolder {
        TextView name_txt, length_txt, location_txt;
        CardView cardView;

        public SearchHikeVH(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.hike_name_txt);
            length_txt = itemView.findViewById(R.id.hike_length_txt);
            location_txt = itemView.findViewById(R.id.hike_location_txt);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


}
