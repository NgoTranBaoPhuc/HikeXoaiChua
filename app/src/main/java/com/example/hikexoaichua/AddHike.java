package com.example.hikexoaichua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddHike extends AppCompatActivity {
    EditText name_input, location_input, date_input, parking_input, length_input, difficulty_input, description_input, vehicle_input;
    Button add_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);
       AddHike();



    }
    public void AddHike(){
        name_input = findViewById(R.id.editTextName);
        location_input = findViewById(R.id.editTextLocation);
        date_input = findViewById(R.id.editTextDate);
        parking_input = findViewById(R.id.editTextIsParking);
        length_input = findViewById(R.id.editTextLength);
        difficulty_input = findViewById(R.id.editTextDifficulty);
        description_input = findViewById(R.id.editTextDescription);
        vehicle_input = findViewById(R.id.editTextVehicle);
        add_button = findViewById(R.id.buttonSubmit);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    MyDatabaseHelper myDB = new MyDatabaseHelper( AddHike.this);
                    myDB.addHike(name_input.getText().toString().trim(),
                            location_input.getText().toString().trim(),
                            date_input.getText().toString().trim(),
                            Integer.valueOf(parking_input.getText().toString().trim()),
                            Float.parseFloat(length_input.getText().toString().trim()),
                            difficulty_input.getText().toString().trim(),
                            description_input.getText().toString().trim(),
                            vehicle_input.getText().toString().trim());
                }
                catch (Exception exception){
                    Toast.makeText(AddHike.this, exception.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}