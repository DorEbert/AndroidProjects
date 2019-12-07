package com.example.asteroidfallinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearLayout;
    private int mainActivityWitdh;
    private int mainActivityHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                ,mainActivityHeight/13
                ,0
                ,mainActivityHeight/13);
        loginButton.setLayoutParams(loginButtonLayoutParams);
        loginButton.setBackgroundResource(R.color.loginViewColor);
        loginButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(loginButton);
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
        passwordEditText.setBackgroundColor(getApplication().getResources().getColor(R.color.textViewColor));
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setLayoutParams(lparams);
        linearLayout.addView(passwordEditText);
    }
    private void addUserNameTextBox(LinearLayout.LayoutParams lparams) {
        //Set UserName EditText Setting
        EditText userNameEditText = new EditText(this);
        userNameEditText.setBackgroundColor(getApplication().getResources().getColor(R.color.textViewColor));
        userNameEditText.setLayoutParams(lparams);
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
        Intent myIntent = new Intent(MainActivity.this,
                GameActivity.class);
        startActivity(myIntent);
    }
}
