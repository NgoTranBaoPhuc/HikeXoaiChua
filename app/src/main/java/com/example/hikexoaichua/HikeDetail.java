package com.example.hikexoaichua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HikeDetail extends AppCompatActivity {

    TextView textViewTitle, textViewTime, textViewLocation, textViewDescription;
    CheckBox checkBoxIsParking;
    RecyclerView listViewObservations;
    Button buttonAddObservation;
    ArrayList<ObservationModel> observations;
    ObservationAdapter adapter;
    ImageView goback_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_detail);

        getDatafromMain();
        fetchObservationData();
        goBack();
    }

    public void getDatafromMain() {

        Intent i = getIntent();
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewTime = findViewById(R.id.textViewTime);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewDescription = findViewById(R.id.textViewDescription);
        checkBoxIsParking = findViewById(R.id.checkBoxIsParking);

        textViewTitle.setText(i.getStringExtra("name"));
        textViewTime.setText(i.getStringExtra("date"));
        textViewLocation.setText(i.getStringExtra("location"));
        textViewDescription.setText(i.getStringExtra("description"));
        checkBox(i.getBooleanExtra("isParking", true));

    }

    public void checkBox(boolean i) {
        if(i) {
            checkBoxIsParking.setChecked(true);
        } else {
            checkBoxIsParking.setChecked(false);
        }
    }

    private void fetchObservationData() {
        listViewObservations = findViewById(R.id.listViewObservations);
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        observations = db.fetchObservationData(getHikeId());
        adapter = new ObservationAdapter(observations, this);
        listViewObservations.setAdapter(adapter);
        listViewObservations.setLayoutManager(new LinearLayoutManager(this));
    }

    private int getHikeId() {
        Intent i = getIntent();
        int id = i.getIntExtra("id", 0);
        return id;
    };

    public void changeActivity() {
        buttonAddObservation = findViewById(R.id.buttonAddObservation);

        Intent intent = new Intent(this, AddObservations.class);
        intent.putExtra("id", getHikeId());
        this.startActivity(intent);
    }

    public void changeToAddObservation(View view) {
        changeActivity();
    }

    public void goBack() {
        goback_btn = findViewById(R.id.goback_btn);

        goback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}