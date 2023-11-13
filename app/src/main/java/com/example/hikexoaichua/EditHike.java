package com.example.hikexoaichua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditHike extends AppCompatActivity {
    ImageView gobackToHikeDetail;
    EditText edit_hike_name, edit_location, edit_date, edit_length, edit_difficulty, edit_description, edit_vehicle;
    RadioGroup rg_risk;
    RadioButton editBtnYes, editBtnNo;

    Button updateHike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);

        goback();
        getData();
    }


    private void getData(){
        edit_hike_name = findViewById(R.id.edit_hike_name);
        edit_location = findViewById(R.id.edit_location);
        edit_date = findViewById(R.id.edit_date);
        edit_length = findViewById(R.id.edit_length);
        edit_difficulty = findViewById(R.id.edit_difficulty);
        edit_vehicle = findViewById(R.id.edit_vehicle);
        edit_description = findViewById(R.id.edit_description);
        editBtnYes = findViewById(R.id.editBtnYes);
        editBtnNo = findViewById(R.id.editBtnNo);
        updateHike = findViewById(R.id.updateHike);

        Intent i = getIntent();
        int id = i.getIntExtra("id",3);
        edit_hike_name.setText(i.getStringExtra("name"));
        edit_location.setText(i.getStringExtra("location"));
        edit_length.setText(String.valueOf(i.getFloatExtra("length", 0.0f)));
        edit_difficulty.setText(i.getStringExtra("difficulty"));
        edit_vehicle.setText(i.getStringExtra("vehicle"));
        edit_date.setText(i.getStringExtra("date"));
        edit_description.setText(i.getStringExtra("description"));
        if(i.getIntExtra("isParking", 0) == 1) {
            editBtnYes.setChecked(true);
        } else {
            editBtnNo.setChecked(false);
        }
        int isParking;
        if(editBtnYes.isChecked()) {
            isParking =  1;
        } else {
            isParking = 0;
        }

        updateHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyDatabaseHelper db = new MyDatabaseHelper(EditHike.this);
                    db.updateHike(
                            id,
                            edit_hike_name.getText().toString().trim(),
                            edit_location.getText().toString().trim(),
                            edit_date.getText().toString().trim(),
                            isParking,
                            Float.parseFloat(edit_length.getText().toString().trim()),
                            edit_difficulty.getText().toString().trim(),
                            edit_vehicle.getText().toString().trim(),
                            edit_description.getText().toString().trim()
                    );
                    BacktoMainActivity();
                } catch (Exception e) {
                    Toast.makeText(EditHike.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void goback() {
        gobackToHikeDetail = findViewById(R.id.gobackToHikeDetail);
        gobackToHikeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void BacktoMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}