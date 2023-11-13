package com.example.hikexoaichua;

public class HikeModel {
    private  int id;
    private String name;
    private  String location;
    private String date;
    private int isParking;
    private  float length;
    private String difficulty;
    private  String description;
    private  String vehicle;

    public HikeModel(int id, String name, String location, String date, int isParking, float length, String difficulty, String description, String vehicle) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.isParking = isParking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.vehicle = vehicle;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsParking() {
        return isParking;
    }

    public void setIsParking(int isParking) {
        this.isParking = isParking;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
