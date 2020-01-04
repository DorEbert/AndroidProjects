package com.example.asteroidfallinggame;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseUtill {
    private static FirebaseDatabase databaseReference;
    private static ApplicationUserModel[] login;
    public FireBaseUtill(){
        databaseReference = getInstance();
    }
    private static FirebaseDatabase getInstance()
    {
        if (databaseReference == null) {
            try {
                databaseReference = FirebaseDatabase.getInstance();
            }catch(Exception e){
                e.getMessage();
            }
            if(login == null){
                login = new ApplicationUserModel[1];
            }
        }
        return databaseReference;
    }

    public ApplicationUserModel signUp(String username,String passowrd){
        DatabaseReference signUpReference = databaseReference.getReference(Global_Variable.APPLICATION_USERS_TABLE_NAME);
        String id = signUpReference.push().getKey();
        ApplicationUserModel userModel = new ApplicationUserModel(id,username,passowrd);
        signUpReference.child(id).setValue(userModel);
        return userModel;
    }
    public DatabaseReference getRefrencesApplicationusers(){
        return databaseReference.getReference(Global_Variable.APPLICATION_USERS_TABLE_NAME);
    }
    public DatabaseReference getRefrencesScoresSheet(){
        return databaseReference.getReference(Global_Variable.SCORE_SHEET_TABLE_NAME);
    }
    public ScoreSheetModel saveScoreSheet(String username, int scoreAchieved, LatLng location){
        DatabaseReference databaseReference = FireBaseUtill.databaseReference.getReference(Global_Variable.SCORE_SHEET_TABLE_NAME);
        String id = databaseReference.push().getKey();
        ScoreSheetModel scoreSheetModel = new ScoreSheetModel(username,scoreAchieved,location);
        databaseReference.child(id).setValue(scoreSheetModel);
        return scoreSheetModel;
    }
}
