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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Random;

public class GameActivity extends AppCompatActivity  {
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private RelativeLayout fireAttackslinearWrapper;
    private LinearLayout userHPAndLifeChancesLayout;
    private int mainActivityWitdh;
    private int mainActivityHeight;
    private Button pointsButton;
    private ImageView spaceShip;
    private ProgressBar healthProgressBar;
    private final int LIFECHANCES = 3;
    private int currentLife;
    private final int AMOUNT_OF_HORIZENTAL_LAYOUTS = 1;
    private int myPoints = 0;
    private final String FIREICONID = "lifeHeartIcon";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);
        setSettingOfActivity();
        userHPAndLifeChancesLayout = new LinearLayout(this);
        userHPAndLifeChancesLayout.setOrientation(LinearLayout.HORIZONTAL);
        //1.1 Set life Image settings
        currentLife = 3;
        Bitmap bmp = getUIBmpFireAttack();
        for(int i = 0 ;i < LIFECHANCES ; i++){
            ImageView lifeHeartIcon = new ImageView(this);
            lifeHeartIcon.setId(i);
            lifeHeartIcon.setImageBitmap(bmp);
            userHPAndLifeChancesLayout.addView(lifeHeartIcon);
        }
        linearLayout.addView(userHPAndLifeChancesLayout);
        initialLayoutFireAttack();
        linearLayout.addView(fireAttackslinearWrapper);
        addHealthProgressBar();
        addSpaceShip();
        // set Settings for fire attack
        final int fireWidth=mainActivityWitdh/10;
        final int fireHeight=mainActivityHeight/10;
        Bitmap fireBmp;
        fireBmp=BitmapFactory.decodeResource(getResources(),R.drawable.fireattack);//image is your image
        fireBmp=Bitmap.createScaledBitmap(fireBmp, fireWidth,fireHeight, true);
        LinearLayout.LayoutParams fireImageLayoutParams = new LinearLayout.LayoutParams(fireWidth, fireHeight);
        fireImageLayoutParams.setMargins(0
                ,0
                ,0
                ,0);
        fireImageLayoutParams.gravity = Gravity.LEFT;
        Random r = new Random();
        startRainFire(fireBmp,fireWidth,fireHeight,fireImageLayoutParams);
        linearLayout.addView(spaceShip);
        addTouchEventToSpaceShip();
    }
    private void initialLayoutFireAttack() {
        fireAttackslinearWrapper = new RelativeLayout(this);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(mainActivityWitdh,(int)(mainActivityHeight *0.73));
        fireAttackslinearWrapper.setLayoutParams(lparams);
    }
    private Bitmap getUIBmpFireAttack() {
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
        healthProgressBar.setProgress(100);
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
        spaceShipLayoutParams.gravity = Gravity.CENTER;
        spaceShip.setLayoutParams(spaceShipLayoutParams);
        //  Start spaceShipBmp
        Bitmap spaceShipBmp = BitmapFactory.decodeResource(getResources(),R.drawable.spaceship);
        spaceShipBmp =Bitmap.createScaledBitmap(spaceShipBmp,spaceShipWidth, spaceShipHeight, true);
        spaceShip.setImageBitmap(spaceShipBmp);
        //  End spaceShipBmp
    }
    private final int LIFE_DIDACTIC = 20;
    private synchronized void collisionDetection(final ImageView v1,final ImageView v2){

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(30);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run(){
                                    if (isCllision(v1, v2)) {
                                        healthProgressBar.setProgress((int) (healthProgressBar.getProgress() - LIFE_DIDACTIC));
                                        if(healthProgressBar.getProgress() == 0){
                                            userHPAndLifeChancesLayout.removeView(userHPAndLifeChancesLayout.findViewById(currentLife-1));
                                            currentLife--;
                                            healthProgressBar.setProgress(100);
                                            if(currentLife <=0 ){
                                                currentLife = LIFECHANCES;
                                                Intent myIntent = new Intent(GameActivity.this,
                                                        ScoreSheetActivity.class);
                                                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                myIntent.putExtra("myPoints", myPoints);
                                                myPoints = 0;
                                                startActivity(myIntent);
                                            }
                                        }
                                        v1.setX(0);
                                        v1.setY(0);
                                        v1.animate().cancel();
                                        fireAttackslinearWrapper.removeView(v1);
                                }else {
                                        pointsButton.setText("Points: " + myPoints);
                                    }
                            }
                        });
                    }
                }catch (InterruptedException e) {}}};
        t.start();
    }
    private synchronized void startRainFire(final Bitmap fireBmp,final int fireWidth,final int fireHeight,final LinearLayout.LayoutParams fireImageLayoutParams){
        Thread t = new Thread() {
            Random r = new Random();
            int fireXlocation;            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run(){
                                ImageView fireAttack = getFireAttack(fireBmp, fireImageLayoutParams);
                                int wi = mainActivityWitdh;
                                fireXlocation = r.nextInt(wi - fireWidth) ;
                                fireAttack.setX(fireXlocation);
                                fireAttack.setY(0);
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
        pointsButton.setText("Points:");
        pointsButton.setTextSize(mainActivityWitdh/35);
        pointsButton.setWidth(mainActivityWitdh);
        pointsButton.setHeight(mainActivityHeight/12);
        pointsButton.setEnabled(false);;
        pointsButton.setBackgroundResource(R.color.headerColor);
        pointsButton.setTextColor(getApplication().getResources().getColor(R.color.colorBlack));
        linearLayout.addView(pointsButton);
    }
    private ImageView getFireAttack(Bitmap fireBmp,LinearLayout.LayoutParams fireImageLayoutParams){
        final ImageView fireAttack = new ImageView(this);
        fireAttack.setLayoutParams(fireImageLayoutParams);
        fireAttack.setImageBitmap(fireBmp);
        fireAttack.animate()
                .translationY(mainActivityHeight)
                .setDuration(5000)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        fireAttackslinearWrapper.removeView(fireAttack);
                        myPoints+=10;
                    }
                }).start();
        return fireAttack;
    }
    @Override
    public void onBackPressed(){
        currentLife = LIFECHANCES;
        Intent myIntent = new Intent(GameActivity.this,
                ScoreSheetActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myIntent.putExtra("myPoints", myPoints);
        myPoints = 0;
        startActivity(myIntent);
    }
}
