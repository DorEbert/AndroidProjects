package com.example.asteroidfallinggame;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBaseUtill {
    private static FirebaseDatabase databaseReference;
    private static UserModel[] login;
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
                login = new UserModel[1];
            }
        }
        return databaseReference;
    }

    public void signUp(String username,String passowrd){
        DatabaseReference signUpReference = databaseReference.getReference(Global_Variable.APPLICATION_USERS_TABLE_NAME);
        String id = signUpReference.push().getKey();
        ApplicationUserModel userModel = new ApplicationUserModel(id,username,passowrd);
        signUpReference.child(id).setValue(userModel);
    }
    public DatabaseReference getRefrences(){
        return databaseReference.getReference(Global_Variable.APPLICATION_USERS_TABLE_NAME);
    }
}
