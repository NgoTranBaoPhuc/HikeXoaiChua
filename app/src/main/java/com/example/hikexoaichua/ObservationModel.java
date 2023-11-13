package com.example.hikexoaichua;

import android.graphics.Bitmap;

public class ObservationModel {
    private int id;
    private String observations;
    private String time;
    private String comment;
    private int hikeId;
    private Bitmap observationImage;

    public ObservationModel(int id, String observations, String time, String comment, int hikeId, Bitmap observationImage) {
        this.id = id;
        this.observations = observations;
        this.time = time;
        this.comment = comment;
        this.hikeId = hikeId;
        this.observationImage = observationImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
    }

    public Bitmap getObservationImage() {
        return observationImage;
    }

    public void setObservationImage(Bitmap observationImage) {
        this.observationImage = observationImage;
    }
}
