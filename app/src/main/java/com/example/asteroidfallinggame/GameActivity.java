package com.example.asteroidfallinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Random;

public class GameActivity extends AppCompatActivity  {
    private LinearLayout linearLayout;
    private RelativeLayout fireAttackslinearWrapper;
    private FireAttackProducer fireAttackProducer;
    private boolean isToCreateRainFire;
    private LinearLayout userHPAndLifeChancesLayout;
    private int mainActivityWitdh;
    private int mainActivityHeight;
    private Button pointsButton;
    private ImageView spaceShip;
    private ProgressBar healthProgressBar;
    private int currentLife;
    private int myPoints = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);
        isToCreateRainFire = true;
        setSettingOfActivity();
        addLifeChances();
        addHealthProgressBar();
        linearLayout.addView(userHPAndLifeChancesLayout);
        initialLayoutFireAttack();
        linearLayout.addView(fireAttackslinearWrapper);
        addSpaceShip();
        // set Settings for fire attack
        fireAttackProducer = new FireAttackProducer(mainActivityWitdh/10,mainActivityWitdh/10);
        Bitmap fireBmp= BitmapFactory.decodeResource(getResources(),R.drawable.fireattack);
        fireBmp=Bitmap.createScaledBitmap(fireBmp, fireAttackProducer.fireWidth,fireAttackProducer.fireHeight, true);
        fireAttackProducer.fireBmp = fireBmp;
        Random r = new Random();
        startRainFire(fireAttackProducer.fireWidth);
        linearLayout.addView(spaceShip);
        addTouchEventToSpaceShip();
    }
    private void addLifeChances() {
        userHPAndLifeChancesLayout = new LinearLayout(this);
        userHPAndLifeChancesLayout.setOrientation(LinearLayout.HORIZONTAL);
        //1.1 Set life Image settings
        currentLife = Global_Variable.LIFECHANCES;
        Bitmap bmp = getUIBmpForHearts();
        for(int i = 0 ;i < currentLife ; i++){
            ImageView lifeHeartIcon = new ImageView(this);
            lifeHeartIcon.setId(i);
            lifeHeartIcon.setImageBitmap(bmp);
            userHPAndLifeChancesLayout.addView(lifeHeartIcon);
        }
    }
    private void initialCurrentLifeAndHp(){
        currentLife = Global_Variable.LIFECHANCES;
        healthProgressBar.setProgress(Global_Variable.STARTED_HP);
    }
    private void initialLayoutFireAttack() {
        fireAttackslinearWrapper = new RelativeLayout(this);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(mainActivityWitdh,(int)(mainActivityHeight * 0.75));
        fireAttackslinearWrapper.setLayoutParams(lparams);
    }
    private Bitmap getUIBmpForHearts() {
        Bitmap bmp;
        int width=mainActivityHeight/25;
        int height=mainActivityHeight/25;
        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.hearticon);//image is your image
        bmp=Bitmap.createScaledBitmap(bmp, width,height, true);
        return bmp;
    }
    private void addHealthProgressBar() {
        //1.2 Set health ProgressBar settings
        healthProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        healthProgressBar.getProgressDrawable().setColorFilter(
                getApplication().getResources().getColor(R.color.colorRed)
                , android.graphics.PorterDuff.Mode.SRC_IN);
        healthProgressBar.setProgress(Global_Variable.STARTED_HP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mainActivityWitdh, mainActivityHeight/24);
        healthProgressBar.setLayoutParams(params);
        userHPAndLifeChancesLayout.addView(healthProgressBar);
    }
    private void addSpaceShip() {
        // add spaceShip
        final int spaceShipWidth=mainActivityWitdh/10;
        final int spaceShipHeight=mainActivityHeight/10;
        spaceShip = new ImageView(this);
        LinearLayout.LayoutParams spaceShipLayoutParams = new LinearLayout.LayoutParams(spaceShipWidth, spaceShipHeight);
        //spaceShipLayoutParams.gravity = Gravity.CENTER;
        spaceShipLayoutParams.gravity = Gravity.CENTER;
        spaceShip.setLayoutParams(spaceShipLayoutParams);
        //  Start spaceShipBmp
        Bitmap spaceShipBmp = BitmapFactory.decodeResource(getResources(),R.drawable.spaceship);
        spaceShipBmp =Bitmap.createScaledBitmap(spaceShipBmp,spaceShipWidth, spaceShipHeight, true);
        spaceShip.setImageBitmap(spaceShipBmp);
        //  End spaceShipBmp
    }
    private synchronized void collisionDetection(final ImageView v1,final ImageView v2){
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (isToCreateRainFire) {
                        Thread.sleep(30);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run(){
                                    if (isCllision(v1, v2)) {
                                        healthProgressBar.setProgress((int) (healthProgressBar.getProgress() - Global_Variable.LIFE_DIDACTIC));
                                        if(healthProgressBar.getProgress() == 0){
                                            userHPAndLifeChancesLayout.removeView(userHPAndLifeChancesLayout.findViewById(currentLife-1));
                                            currentLife--;
                                            healthProgressBar.setProgress(Global_Variable.STARTED_HP);
                                            if(currentLife <=0 ){
                                                openScoreSheetActivity();
                                            }
                                        }
                                        v1.setX(0);
                                        v1.setY(0);
                                        v1.animate().cancel();
                                        fireAttackslinearWrapper.removeView(v1);
                                }else {
                                        pointsButton.setText(Global_Variable.CURRENT_POINTS + " : " + myPoints);
                                    }
                            }
                        });
                    }
                }catch (InterruptedException e) {}}};
        t.start();
    }
    private synchronized void startRainFire(final int fireWidth){
        Thread t = new Thread() {
            int fireXlocation;            @Override
            public void run() {
                try {
                    while (isToCreateRainFire) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run(){
                                ImageView fireAttack = getFireAttack();
                                collisionDetection(fireAttack,spaceShip);
                                fireAttackslinearWrapper.addView(fireAttack);
                            }
                        });
                    }
                }
                catch (InterruptedException e)
                {}}};
        t.start();
    }
    private static boolean isCllision(ImageView v1, ImageView v2) {
        int v1_width = v1.getDrawable().getMinimumWidth();
        int v1_height = v1.getDrawable().getMinimumHeight();

        int v2_width = v2.getDrawable().getMinimumWidth();
        int v2_height = v2.getDrawable().getMinimumHeight();
        double gracePercentY = 0.3;
        double gracePercentX = 0.4;

        Rect R1=new Rect((int) ((int) v1.getX() + v1_width*gracePercentX), (int) ((int) v1.getY() + gracePercentY*v1_height), (int) (v1.getX() + v1_width*(1-gracePercentX)), (int) ((int) v1.getY() + v1_height*(1+gracePercentY)));
        Rect R2=new Rect((int) v2.getX(),(int) v2.getY() , (int) (v2.getX() + v2_width), (int) v2.getY() + v2_height);
        return R1.intersect(R2);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void addTouchEventToSpaceShip(){
        spaceShip.setOnTouchListener(new View.OnTouchListener()
        {
            PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
            PointF StartPT = new PointF(); // Record Start Position of 'img'
            double gracePercentX = 0.4;

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE :
                        int nextX = (int)(StartPT.x + event.getX() - DownPT.x);
                        nextX = nextX > 0? nextX:0;
                        nextX = nextX < mainActivityWitdh - spaceShip.getWidth()? nextX:mainActivityWitdh-spaceShip.getWidth();
                        spaceShip.setX(nextX);
                        StartPT.set( spaceShip.getX(), spaceShip.getY() );
                        break;
                    case MotionEvent.ACTION_DOWN :
                        DownPT.set( event.getX(), event.getY() );
                        StartPT.set( spaceShip.getX(), spaceShip.getY() );
                        break;
                    case MotionEvent.ACTION_UP :
                        // Nothing have to do
                        break;
                    default :
                        break;
                }
                return true;
            }
        });
    }
    private void setSettingOfActivity(){
        mainActivityWitdh = getResources().getDisplayMetrics().widthPixels;
        mainActivityHeight = getResources().getDisplayMetrics().heightPixels;
        linearLayout = findViewById(R.id.linear_layout);
        //Set pointsButton Settings
        pointsButton = new Button(this);
        pointsButton.setText(Global_Variable.CURRENT_POINTS);
        pointsButton.setTextSize(mainActivityWitdh/35);
        pointsButton.setWidth(mainActivityWitdh);
        pointsButton.setHeight(mainActivityHeight/12);
        pointsButton.setEnabled(false);;
        pointsButton.setBackgroundResource(R.color.headerColor);
        pointsButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(pointsButton);
    }
    private ImageView getFireAttack(){
        final ImageView fireAttack = fireAttackProducer.getFireAttack(this, mainActivityWitdh);
        //fireAttack.setImageBitmap(fireBmp);
        fireAttack.animate()
                .translationY((int)(mainActivityHeight*0.85))
                .setDuration(Global_Variable.DURATION_OF_FIRE_ANIMATE)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        fireAttackslinearWrapper.removeView(fireAttack);
                        myPoints+=Global_Variable.POINTS_PER_FIREATTACK;
                    }
                }).start();
        return fireAttack;
    }
    @Override
    public void onBackPressed(){
        openScoreSheetActivity();
    }
    private void openScoreSheetActivity() {
        initialCurrentLifeAndHp();
        isToCreateRainFire = false;
        Intent myIntent = new Intent(GameActivity.this,
                ScoreSheetActivity.class);
        //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        myIntent.putExtra(Global_Variable.MY_POINTS, myPoints);
        isToCreateRainFire = false;
        myPoints = 0;
        startActivity(myIntent);
    }
}
