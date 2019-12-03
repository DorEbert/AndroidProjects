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

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    int mainActivityWitdh;
    int mainActivityHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityWitdh = getResources().getDisplayMetrics().widthPixels;
        mainActivityHeight = getResources().getDisplayMetrics().heightPixels;
        linearLayout = findViewById(R.id.linear_layout);
        //Set Button Settings
        Button welcomeButton = new Button(this);
        welcomeButton.setText("Welcome");
        welcomeButton.setTextSize(mainActivityWitdh/28);
        welcomeButton.setWidth(mainActivityWitdh);
        welcomeButton.setHeight(mainActivityHeight/7);
        welcomeButton.setEnabled(false);;
        welcomeButton.setBackgroundResource(R.color.headerColor);
        welcomeButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(welcomeButton);
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
        //Set UserName Label Setting
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams((int)(mainActivityWitdh *0.9),mainActivityHeight/20);
        lparams.gravity = Gravity.CENTER;
        TextView userNameLabel = new TextView(this);
        userNameLabel.setText("   Username");
        userNameLabel.setTextSize(mainActivityWitdh/40);
        userNameLabel.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(userNameLabel);
        //Set UserName EditText Setting
        EditText userNameEditText = new EditText(this);
        userNameEditText.setBackgroundColor(getApplication().getResources().getColor(R.color.textViewColor));
        userNameEditText.setLayoutParams(lparams);
        linearLayout.addView(userNameEditText);
        //Set Password Label Setting
        TextView passwordLabel = new TextView(this);
        passwordLabel.setText("   Password");
        passwordLabel.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        passwordLabel.setTextSize(mainActivityWitdh/40);
        linearLayout.addView(passwordLabel);
        //Set passwordEditText EditText Setting
        EditText passwordEditText = new EditText(this);
        passwordEditText.setBackgroundColor(getApplication().getResources().getColor(R.color.textViewColor));
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setLayoutParams(lparams);
        linearLayout.addView(passwordEditText);
        //Set Button Settings
        Button loginButton = new Button(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        GameActivity.class);
                startActivity(myIntent);
            }
        });
        loginButton.setText("Login");
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
}
