package com.example.asteroidfallinggame;

import com.google.android.gms.maps.model.LatLng;

class ScoreSheetModel {
    private String username;
    private double latitude;
    private double longitude;
    private int scoreAchieved = 0;
    public ScoreSheetModel(String username, int scoreAchieved, LatLng location) {
        this.username = username;
        this.scoreAchieved = scoreAchieved;
        this.latitude = location.latitude;
        this.longitude = location.longitude;
    }
    public String getUsername() {
        return username;
    }
    public int getScoreAchieved() {return scoreAchieved;}
    public void setScoreAchieved(int scoreAchieved){this.scoreAchieved = scoreAchieved;}
    public double getLatitude() {return latitude; }
    public double getLongitude() { return longitude;}

}
