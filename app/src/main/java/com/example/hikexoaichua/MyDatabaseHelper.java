package com.example.hikexoaichua;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private  Context context;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInByte;
    private   static  final  String DATABASE_NAME = "Hike.db";
    private  static  final  int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_Hike";
    private static final String COLUMN_ID ="ID";
    private static final String COLUMN_NAME ="hike_name";
    private static final String COLUMN_LOCATION ="location_id";
    private static final String COLUMN_DATE ="date_id";
    private static final String COLUMN_PARKING ="parking_id";
    private static final String COLUMN_LENGTH ="length_id";
    private static final String COLUMN_DIFFICULTY ="difficulty_id";
    private static final String COLUMN_DESCRIPTION ="description_id";
    private static final String COLUMN_VEHICLE ="vehicle_id";


    private static final String TABLE_OBSERVATIONS = "Observations";
    private static final String COLUMN_OBSERVATION_ID = "ID";
    private  static  final  String COLUMN_OBSERVATIONS = "Observations";
    private  static  final  String COLUMN_COMMENT = "Comment";
    private  static  final  String COLUMN_TIME = "Time";
    private  static  final  String COLUMN_IMAGE = "observationImage";
    private static final String COLUMN_HIKE_ID_REF = "Hike_Id";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private String query = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NAME + " TEXT, " +
            COLUMN_LOCATION + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_PARKING + " INTEGER, " +
            COLUMN_LENGTH + " FLOAT, " +
            COLUMN_DIFFICULTY + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_VEHICLE + " TEXT); ";

    private String Observations = "CREATE TABLE " + TABLE_OBSERVATIONS +
            " (" + COLUMN_OBSERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_OBSERVATIONS + "TEXT, " +
            COLUMN_TIME + "TEXT, " +
            COLUMN_COMMENT + "TEXT, " +
            COLUMN_HIKE_ID_REF + " INTEGER, " +
            COLUMN_IMAGE + "BLOB, " +
            " FOREIGN KEY (Hike_Id) REFERENCES my_Hike(ID));";




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(query);
        db.execSQL(Observations);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addHike(String name, String location, String date, int parking, float length, String difficulty,String description, String vehicle ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put( COLUMN_NAME, name);
        cv.put( COLUMN_LOCATION, location);
        cv.put( COLUMN_DATE, date);
        cv.put( COLUMN_PARKING, parking);
        cv.put( COLUMN_LENGTH, length);
        cv.put( COLUMN_DIFFICULTY, difficulty);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put( COLUMN_VEHICLE, vehicle);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addObservations(String observation, String time, String comment,  int hikeId, Bitmap observationImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Bitmap imageToStorageBitmap = observationImage;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStorageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInByte = byteArrayOutputStream.toByteArray();

        cv.put( COLUMN_OBSERVATIONS, observation);
        cv.put( COLUMN_IMAGE, time);
        cv.put(COLUMN_COMMENT, comment);
        cv.put(COLUMN_HIKE_ID_REF, hikeId);
        cv.put("observationImage", imageInByte);


        long result = db.insert(TABLE_OBSERVATIONS, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }


    }

    Cursor readAllData(){
        query = "SELECT * fROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public ArrayList<HikeModel> fetchHikeData() {
        ArrayList<HikeModel> trip = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC", null);

        if(cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String date = cursor.getString(3);
                int isParking = cursor.getInt(4);
                float length = cursor.getFloat(5);
                String difficulty = cursor.getString(6);
                String description = cursor.getString(7);
                String vehicle = cursor.getString(8);

                HikeModel t = new HikeModel(id, name, location, date, isParking, length, difficulty, description,vehicle);
                trip.add(t);

            } while (cursor.moveToNext());
        }

        return trip;
    }

    public void updateHike(int id, String name, String location, String date, int isParking, float length, String difficulty,String vehicle, String description)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_PARKING, isParking);
        contentValues.put(COLUMN_LENGTH, length);
        contentValues.put(COLUMN_DIFFICULTY, difficulty);
        contentValues.put(COLUMN_VEHICLE, vehicle);
        contentValues.put(COLUMN_DESCRIPTION, description);
        db.update(TABLE_NAME, contentValues, "ID = " + id, null);
    };




    public void deleteHike(int id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_NAME, "ID = " + id, null);
        db.delete(TABLE_OBSERVATIONS, "Hike_Id = " + id, null);
    }

    public void deleteObservation(int id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_OBSERVATIONS, "ID = " + id, null);
    }
    public ArrayList<ObservationModel> fetchObservationData(int hikeID) {
        ArrayList<ObservationModel> observations = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_OBSERVATIONS + " WHERE " + COLUMN_HIKE_ID_REF + " = " + hikeID , null);

        if(cursor.moveToNext()) {
            do{
                int observationID = cursor.getInt(0);
                String observation = cursor.getString(1);
                String time = cursor.getString(2);
                String comment = cursor.getString(3);
                int hikeId = cursor.getInt(4);
                byte[] image = cursor.getBlob(5);

                Bitmap objectBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                ObservationModel o = new ObservationModel(observationID, observation, time, comment, hikeId, objectBitmap);
                observations.add(o);


            } while (cursor.moveToNext());
        }
        return observations;
    }
}
