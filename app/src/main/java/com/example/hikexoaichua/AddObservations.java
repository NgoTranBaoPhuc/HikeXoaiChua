package com.example.hikexoaichua;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.content.Intent;
import android.widget.Toast;
import android.provider.MediaStore;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.constant.ImageProvider;

import java.io.IOException;
import java.util.Calendar;

public class AddObservations extends AppCompatActivity {

    private Button btnSelectedDate, imagePickerButton;
    private EditText et_observation_name, et_time, et_cmt, et_time_observation;
    private Button saveObservation;
    Uri uri;
    private ImageView imagePreview;
    private Bitmap imageToStorage;
    private int mHour, mMinute;

    ImageView gobacktohikedetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observations);

        storageImage();
        getImage();
    }

    private void timePicker(){
        et_observation_name = findViewById(R.id.et_observation_name);
        et_time_observation = findViewById(R.id.et_time_observation);
        btnSelectedDate = findViewById(R.id.btnSelectedDate);
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        et_time_observation.setText(hourOfDay + ":" + minute);

                    }
                },mHour,mMinute,false);
        timePickerDialog.show();

    }


    public  int getHikeID(){
        Intent i = getIntent();
        return  1;
    }

    public  void storageImage(){
        saveObservation = findViewById(R.id.saveObservation);
        et_observation_name = findViewById(R.id.et_observation_name);
        et_time = findViewById(R.id.et_time_observation);
        et_cmt = findViewById(R.id.et_cmt);

        saveObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyDatabaseHelper db = new MyDatabaseHelper(AddObservations.this);
                    db.addObservations(
                            et_observation_name.getText().toString(),
                            et_time.getText().toString(),
                            et_cmt.getText().toString(),
                            getHikeID(),
                            imageToStorage);
                            changeActivity();

                } catch (Exception e) {
                    Toast.makeText(AddObservations.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void getImage(){
        imagePreview = findViewById(R.id.imagePreview);
        imagePickerButton =  findViewById(R.id.imagePickerButton);
        imagePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(AddObservations.this)
                        .provider(ImageProvider.BOTH)
                        .crop()
                        .maxResultSize(1080, 1080)
                        .start(101);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            imagePreview.setImageURI(uri);

            try {
                imageToStorage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            Toast.makeText(AddObservations.this, "No Image", Toast.LENGTH_SHORT).show();
        }
    }
    public void openTimeDialog(View view) {
        timePicker();
    }
    private void changeActivity() {
        Intent i = new Intent(this, HikeDetail.class);
        startActivity(i);
    }

    public void goback() {
        gobacktohikedetail = findViewById(R.id.gobacktohikedetail);
        gobacktohikedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}