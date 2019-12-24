package com.example.asteroidfallinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.graphics.BitmapFactory;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearLayout;
    private UserModel[] login;
    private int mainActivityWitdh;
    private int mainActivityHeight;
    private int usernameTextboxID;

    private FireBaseUtill fireBaseUtill;
    private int passwordTextboxID;
    private UserModel the_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fireBaseUtill = new FireBaseUtill();
        login = new UserModel[1];

        // initial local variables
        mainActivityWitdh = getResources().getDisplayMetrics().widthPixels;
        mainActivityHeight = getResources().getDisplayMetrics().heightPixels;
        linearLayout = findViewById(R.id.linear_layout);
        buildLoginScreen();
    }
    private void buildLoginScreen() {
        AddHeaderButton();
        addLoginImage();
        addUserNamePasswordLabels();
        addLoginButton();
        addSignUpButton();
    }
    private void addLoginButton() {
        //Set Button Settings
        Button loginButton = new Button(this);
        loginButton.setOnClickListener(this);
        loginButton.setText(Global_Variable.LOGIN);
        LinearLayout.LayoutParams loginButtonLayoutParams =
                new LinearLayout.LayoutParams((int)(mainActivityWitdh *0.5),mainActivityHeight/20);
        loginButtonLayoutParams.gravity = Gravity.CENTER;
        loginButtonLayoutParams.setMargins(0
                ,mainActivityHeight/20
                ,0
                ,mainActivityHeight/40);
        loginButton.setLayoutParams(loginButtonLayoutParams);
        loginButton.setBackgroundResource(R.color.loginViewColor);
        loginButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(loginButton);
    }
    private void addSignUpButton() {
        //Set Button Settings
        Button signUpButton = new Button(this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseUtill.getRefrences().orderByChild(Global_Variable.USERNAME_COLUMN)
                        .addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EditText username = findViewById(usernameTextboxID);
                                EditText password = findViewById(passwordTextboxID);
                                String usernameString = username.getText().toString();
                                String passwordString = password.getText().toString();
                                if(usernameString.isEmpty() ||
                                        passwordString.isEmpty()){
                                    showAlertDialog(Global_Variable.MISSING_INFO_MSG,Global_Variable.MISSING_INFO_BODY_MSG);
                                    return;
                                }
                                for(DataSnapshot usersSnapShot: dataSnapshot.getChildren()){
                                    try {
                                        String userNameFireBaes = usersSnapShot.child(Global_Variable.USERNAME_COLUMN).getValue().toString();
                                        if (userNameFireBaes.equals(usernameString)) {
                                            showAlertDialog(Global_Variable.USER_EXIST_MSG, Global_Variable.USER_EXIST_BODY_MSG);
                                            return;
                                        }
                                    }
                                    catch(Exception e){
                                        e.getMessage();
                                    }
                                }
                                addUser();
                                Intent myIntent = new Intent(MainActivity.this,
                                        GameActivity.class);
                                startActivity(myIntent);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        });
        signUpButton.setText(Global_Variable.SIGN_UP);
        LinearLayout.LayoutParams loginButtonLayoutParams =
                new LinearLayout.LayoutParams((int)(mainActivityWitdh *0.5),mainActivityHeight/20);
        loginButtonLayoutParams.gravity = Gravity.CENTER;
        loginButtonLayoutParams.setMargins(0
                ,mainActivityHeight/40
                ,0
                ,mainActivityHeight/40);
        signUpButton.setLayoutParams(loginButtonLayoutParams);
        signUpButton.setBackgroundResource(R.color.loginViewColor);
        signUpButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(signUpButton);
    }
    private void addUserNamePasswordLabels() {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams((int)(mainActivityWitdh *0.9),mainActivityHeight/20);
        lparams.gravity = Gravity.CENTER;
        //Set UserName Label Setting
        addUserNameLabel();
        addUserNameTextBox(lparams);
        addPasswordLabel();
        addPasswordTextBox(lparams);

    }
    private void addPasswordTextBox(LinearLayout.LayoutParams lparams) {
        //Set passwordEditText EditText Setting
        EditText passwordEditText = new EditText(this);
        passwordEditText.setMaxLines(1);
        passwordEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        passwordTextboxID = Global_Variable.GetID();
        passwordEditText.setId(passwordTextboxID);
        passwordEditText.setBackgroundColor(getApplication().getResources().getColor(R.color.textViewColor));
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setLayoutParams(lparams);
        passwordEditText.setText("dor");
        linearLayout.addView(passwordEditText);
    }
    private void addUserNameTextBox(LinearLayout.LayoutParams lparams) {
        //Set UserName EditText Setting
        EditText userNameEditText = new EditText(this);
        userNameEditText.setMaxLines(1);
        userNameEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        usernameTextboxID = Global_Variable.GetID();
        userNameEditText.setId(usernameTextboxID);
        userNameEditText.setBackgroundColor(getApplication().getResources().getColor(R.color.textViewColor));
        userNameEditText.setLayoutParams(lparams);
        userNameEditText.setText("dor");
        linearLayout.addView(userNameEditText);
    }
    public void addPasswordLabel(){
        //Set Password Label Setting
        TextView passwordLabel = new TextView(this);
        passwordLabel.setText("    " + Global_Variable.PASSWORD);
        passwordLabel.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        passwordLabel.setTextSize(mainActivityWitdh/40);
        linearLayout.addView(passwordLabel);

    }
    public void addUserNameLabel(){
        //Set UserName Label Setting
        TextView userNameLabel = new TextView(this);
        userNameLabel.setText("    " + Global_Variable.USERNAME);
        userNameLabel.setTextSize(mainActivityWitdh/40);
        userNameLabel.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(userNameLabel);

    }
    public void AddHeaderButton(){
        //Set Button Settings
        Button welcomeButton = new Button(this);
        welcomeButton.setText(Global_Variable.WELCOME);
        welcomeButton.setTextSize(mainActivityWitdh/28);
        welcomeButton.setWidth(mainActivityWitdh);
        welcomeButton.setHeight(mainActivityHeight/7);
        welcomeButton.setEnabled(false);;
        welcomeButton.setBackgroundResource(R.color.headerColor);
        welcomeButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(welcomeButton);
    }
    public void addLoginImage(){
        //Set Image settings
        ImageView loginImage = new ImageView(this);
        loginImage.setImageResource(R.drawable.loginimage);
        Bitmap bmp;
        int width=mainActivityHeight/5;
        int height=mainActivityHeight/5;
        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.loginimage);//image is your image
        bmp=Bitmap.createScaledBitmap(bmp, width,height, true);
        loginImage.setImageBitmap(bmp);
        LinearLayout.LayoutParams loginImageLayoutParams = new LinearLayout.LayoutParams(width, height);
        loginImageLayoutParams.setMargins(mainActivityHeight/2
                                            ,mainActivityHeight/13
                                            ,mainActivityHeight/2
                                            ,mainActivityHeight/13);
        loginImageLayoutParams.gravity = Gravity.CENTER;
        loginImage.setLayoutParams(loginImageLayoutParams);
        linearLayout.addView(loginImage);
    }
    @Override
    public void onClick(View v) {
        EditText username = findViewById(usernameTextboxID);
        EditText password = findViewById(passwordTextboxID);
        final String usernameString = username.getText().toString();
        final String passwordString = username.getText().toString();
        if(usernameString.isEmpty() ||
                passwordString.isEmpty()){
            showAlertDialog(Global_Variable.MISSING_INFO_MSG,Global_Variable.MISSING_INFO_BODY_MSG);
            return;
        }
        fireBaseUtill.getRefrences().orderByChild(Global_Variable.USERNAME_COLUMN).equalTo(usernameString)
                .addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot usersSnapShot: dataSnapshot.getChildren()){
                            try {
                                String userNameFireBaes = usersSnapShot.child(Global_Variable.USERNAME_COLUMN).getValue().toString();
                                String passwordFireBaes = usersSnapShot.child(Global_Variable.PASSWORD.toLowerCase()).getValue().toString();
                                if (userNameFireBaes.equals(usernameString)
                                        && passwordFireBaes.equals(passwordString))
                                    login[0] = new UserModel();
                                    login[0].setUsername(userNameFireBaes);
                                    login[0].setPassword(passwordFireBaes);
                                    Intent myIntent = new Intent(MainActivity.this,
                                            GameActivity.class);
                                    startActivity(myIntent);
                                    break;
                            }
                            catch(Exception e){
                                e.getMessage();
                            }
                        }
                        if(login[0] == null){
                                showAlertDialog(Global_Variable.USER_NOT_EXIST_MSG,Global_Variable.USER_NOT_EXIST_BODY_MSG);
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    private void showAlertDialog(String header, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(header);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    private boolean addUser(String usernameString,String passwordString){
        if(usernameString == null || usernameString == ""
        || passwordString == null || passwordString == "")
            return false;
        fireBaseUtill.signUp(usernameString,passwordString);
        return true;
    }
    private boolean addUser(){
        EditText username = findViewById(usernameTextboxID);
        EditText password = findViewById(passwordTextboxID);
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        return addUser(usernameString,passwordString);
    }
}
