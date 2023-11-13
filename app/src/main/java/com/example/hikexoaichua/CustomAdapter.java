package com.example.hikexoaichua;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable{
    private Context context;
    private ArrayList<HikeModel> hike;
    private ArrayList<HikeModel> hikeOld;
    CustomAdapter(Context context, ArrayList<HikeModel> hike,ArrayList<HikeModel> hikeOld){
        this.context = context;
        this.hike = hike;
        this.hikeOld = hikeOld;

    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        HikeModel h = hike.get(position);
        holder.hike_name_txt.setText(String.valueOf(h.getName()));
        holder.hike_location_txt.setText(String.valueOf(h.getLocation()));
        holder.hike_date_txt.setText(String.valueOf(h.getDate()));
        holder.hike_length_txt.setText(String.valueOf(h.getLength()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HikeDetail.class);
                intent.putExtra("id", h.getId());
                intent.putExtra("name", h.getName());
                intent.putExtra("location", h.getLocation());
                intent.putExtra("date", h.getDate());
                intent.putExtra("isParking", h.getIsParking());
                intent.putExtra("length", h.getLength());
                intent.putExtra("difficulty", h.getDifficulty());
                intent.putExtra("description", h.getDescription());
                intent.putExtra("vehicle", h.getVehicle());


                v.getContext().startActivity(intent);
            }

    });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottomsheetlayout);

                LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit);
                LinearLayout deleteLayout = dialog.findViewById(R.id.layoutDelete);
                editLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), EditHike.class);
                        intent.putExtra("id", h.getId());
                        intent.putExtra("name", h.getName());
                        intent.putExtra("location", h.getLocation());
                        intent.putExtra("length", h.getLength());
                        intent.putExtra("difficulty", h.getDifficulty());
                        intent.putExtra("location", h.getLocation());
                        intent.putExtra("date", h.getDate());
                        intent.putExtra("description", h.getDescription());
                        intent.putExtra("vehicle",h.getVehicle());
                        intent.putExtra("isParking", h.getIsParking());

                        v.getContext().startActivity(intent);
                        dialog.dismiss();
                        Toast.makeText(context, "Edit is Clicked", Toast.LENGTH_SHORT).show();

                    }
                });
                deleteLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(context,"Delete is successfully",Toast.LENGTH_SHORT).show();
                        MyDatabaseHelper db = new MyDatabaseHelper(context);
                        db.deleteHike(h.getId());
                    }
                });


                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                return true;


            }
        });
    }



    @Override
    public int getItemCount() {
        return hike.size();
    }
     class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  hike_name_txt, hike_location_txt, hike_date_txt, hike_length_txt;
        CardView cardView;


        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            hike_name_txt = itemView.findViewById(R.id.hike_name_txt);
            hike_location_txt = itemView.findViewById(R.id.hike_location_txt);

            hike_date_txt = itemView.findViewById(R.id.hike_date_txt);
            hike_length_txt = itemView.findViewById(R.id.hike_length_txt);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()) {
                    hike = hikeOld;
                } else {
                    ArrayList<HikeModel> list = new ArrayList<>();
                    for (HikeModel hike: hikeOld) {
                        if(hike.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(hike);
                        }
                        if(hike.getLocation().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(hike);
                        }
                        if(hike.getDate().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(hike);
                        }
                    }

                    hike = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = hike;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                hike = (ArrayList<HikeModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

