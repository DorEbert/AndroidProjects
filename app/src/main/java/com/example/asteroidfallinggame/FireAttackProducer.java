package com.example.asteroidfallinggame;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Random;

public class FireAttackProducer {
    public int fireWidth;
    public int fireHeight;
    public Bitmap fireBmp;
    private Random r;

    public  FireAttackProducer(int fireWidth,int fireHeight){
        this.fireWidth  = fireWidth;
        this.fireHeight = fireHeight;
        r = new Random();
    }
    public ImageView getFireAttack(GameActivity gameActivity,int mainActivityWitdh){
        final ImageView fireAttack = new ImageView(gameActivity);
        fireAttack.setImageBitmap(fireBmp);
        fireAttack.setX(r.nextInt(mainActivityWitdh - fireWidth));
        fireAttack.setY(0);
        int wi = mainActivityWitdh;
        return fireAttack;
    }

}
