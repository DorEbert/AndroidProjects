package com.example.asteroidfallinggame;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Random;
public class AstroidProducer {
    public int fireWidth;
    public int fireHeight;
    public Bitmap fireBmp;
    public Bitmap coinBmp;
    private Random r;

    public AstroidProducer(int fireWidth, int fireHeight){
        this.fireWidth  = fireWidth;
        this.fireHeight = fireHeight;
        r = new Random();
    }
    public ImageView getFireAttack(GameActivity gameActivity,int mainActivityWitdh){
        ImageView fireAttack = IsToCreateCoinOrFire(new ImageView(gameActivity));
        fireAttack.setX(r.nextInt(mainActivityWitdh - fireWidth));
        fireAttack.setY(0);
        int wi = mainActivityWitdh;
        return fireAttack;
    }
    public ImageView IsToCreateCoinOrFire(ImageView AstroidFall){
        // Math.random() -> NUM BETWEEN (0,1)
        if(Math.random() < Global_Variable.CHANGE_OF_PRODUCE_FIRE_ATTACK) {
            AstroidFall.setImageBitmap(fireBmp);
            int id = Global_Variable.GetID();
                if(id%2==0)
                    AstroidFall.setId(id);
                else{
                    AstroidFall.setId(Global_Variable.GetID());
                }
        }
        else {
            AstroidFall.setImageBitmap(coinBmp);
            int id = Global_Variable.GetID();
            if(id%2==1)
                AstroidFall.setId(id);
            else{
                AstroidFall.setId(Global_Variable.GetID());
            }
        }
        return AstroidFall;
    }

}
