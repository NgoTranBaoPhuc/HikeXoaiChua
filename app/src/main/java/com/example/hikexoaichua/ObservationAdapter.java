package com.example.hikexoaichua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationVH> {
    ArrayList<ObservationModel> observations;

    Context context;

    public ObservationAdapter(ArrayList<ObservationModel> observations, Context context) {
        this.observations = observations;
        this.context = context;
    }

    @NonNull
    @Override
    public ObservationAdapter.ObservationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.observation_row, parent, false);
        return new ObservationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationAdapter.ObservationVH holder, int position) {
        ObservationModel o = observations.get(position);
        holder.observation_txt.setText(String.valueOf(o.getObservations()));
        holder.observation_comment_text.setText(String.valueOf(o.getComment()));
        holder.observation_image.setImageBitmap(o.getObservationImage());

    }

    @Override
    public int getItemCount() {
        return observations.size();
    }
    class ObservationVH extends RecyclerView.ViewHolder {

        TextView observation_txt, observation_comment_text;
        ImageView observation_image;

        CardView observation_cv;

        public ObservationVH(@NonNull View itemView) {
            super(itemView);
            observation_txt = itemView.findViewById(R.id.observation_txt);
            observation_comment_text = itemView.findViewById(R.id.observation_comment_text);
            observation_image = itemView.findViewById(R.id.observation_image);
            observation_cv = itemView.findViewById(R.id.observation_cv);

        }
    }
}
