package com.example.asteroidfallinggame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreSheetActivity extends AppCompatActivity {
    private int mainActivityWitdh;
    private int mainActivityHeight;
    private LinearLayout linearLayout;
    private Fragment fragment;
    private static int maxPointAchieved = 0;
    private static int currentPoints = 0;
    private static int amountOfGamesPlayed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_sheet);
        mainActivityWitdh = getResources().getDisplayMetrics().widthPixels;
        mainActivityHeight = getResources().getDisplayMetrics().heightPixels;
        linearLayout = findViewById(R.id.linear_layout);
        addHeadLine();
        addImage();
        currentPoints = getIntent().getExtras().getInt(Global_Variable.MY_POINTS);
        amountOfGamesPlayed++;
        if(currentPoints > maxPointAchieved)
            maxPointAchieved = currentPoints;
        addLabel(Global_Variable.CURRENT_POINTS,currentPoints);
        addLabel(Global_Variable.MAX_POINTS_ACHIEVED,maxPointAchieved);
        addLabel(Global_Variable.GAMES_PLAYED,amountOfGamesPlayed);
        addButton();
    }
    private void addHeadLine(){
        //Set Button Settings
        Button welcomeButton = new Button(this);
        welcomeButton.setText(Global_Variable.SCORE_SHEET);
        welcomeButton.setTextSize(mainActivityWitdh/28);
        welcomeButton.setWidth(mainActivityWitdh);
        welcomeButton.setHeight(mainActivityHeight/7);
        welcomeButton.setEnabled(false);;
        welcomeButton.setBackgroundResource(R.color.headerColor);
        welcomeButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(welcomeButton);

    }
    private void addFragment(){

    }
    private void addLabel(String text,int number){
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams((int)(mainActivityWitdh *0.9),mainActivityHeight/20);
        lparams.gravity = Gravity.CENTER;
        TextView userNameLabel = new TextView(this);
        userNameLabel.setText("  " + text + " : " + number);
        userNameLabel.setTextSize(mainActivityWitdh/40);
        userNameLabel.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(userNameLabel);
    }
    private void addImage(){
        ImageView loginImage = new ImageView(this);
        loginImage.setImageResource(R.drawable.medalscoresheet);
        Bitmap bmp;
        int width=mainActivityHeight/5;
        int height=mainActivityHeight/5;
        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.medalscoresheet);//image is your image
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
    private void addButton(){
        Button loginButton = new Button(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ScoreSheetActivity.this,
                        GameActivity.class);
                startActivity(myIntent);
            }
        });
        loginButton.setText(Global_Variable.NEW_GAME);
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
    @Override
    public void onBackPressed(){

    }
}
