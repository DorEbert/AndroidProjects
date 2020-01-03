package com.example.asteroidfallinggame;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseUtill {
    private static FirebaseDatabase databaseReference;
    private static UserModel2[] login;
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
                login = new UserModel2[1];
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
    public DatabaseReference getRefrences(){
        return databaseReference.getReference(Global_Variable.APPLICATION_USERS_TABLE_NAME);
    }
}
