package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animate {

    private TextureRegion[] animationFrames;
    private Animation animation;
    Texture texture;
    private int w;
    private int h;
    private Boolean type;

    public Animate(Texture tex,int w,int h,boolean t)
    {
        this.texture = tex;
        this.w = w;
        this.h = h;
        type = t; //type = true: horizontal, type = false: vertical
    }

    public Animation animatePlayer()
    {
        TextureRegion[][] tmpFrames = TextureRegion.split(texture,w,h);

        animationFrames = new TextureRegion[8];

        if(type == true) {

            animationFrames[0] = tmpFrames[0][0];
            animationFrames[1] = tmpFrames[0][1];
            animationFrames[2] = tmpFrames[0][2];
            animationFrames[3] = tmpFrames[0][3];
            animationFrames[4] = tmpFrames[1][0];
            animationFrames[5] = tmpFrames[1][1];
            animationFrames[6] = tmpFrames[1][2];
            animationFrames[7] = tmpFrames[1][3];
        }
        else
        {
            animationFrames[0] = tmpFrames[0][0];
            animationFrames[1] = tmpFrames[1][0];
            animationFrames[2] = tmpFrames[2][0];
            animationFrames[3] = tmpFrames[3][0];
            animationFrames[4] = tmpFrames[0][1];
            animationFrames[5] = tmpFrames[1][1];
            animationFrames[6] = tmpFrames[2][1];
            animationFrames[7] = tmpFrames[3][1];
        }

        animation = new Animation(1f/8f,animationFrames);
        return animation;
    }

    public Animation bulletHit()
    {
        TextureRegion[][] tmpFrames = TextureRegion.split(texture,w,h);

        animationFrames = new TextureRegion[3];

        animationFrames[0] = tmpFrames[0][0];
        animationFrames[1] = tmpFrames[0][1];
        animationFrames[2] = tmpFrames[0][2];

        animation = new Animation(1f/3f,animationFrames);
        return animation;
    }

    public Animation animateEnemy()
    {
        TextureRegion[][] tmpFrames = TextureRegion.split(texture,w,h);

        animationFrames = new TextureRegion[3];

        animationFrames[0] = tmpFrames[0][0];
        animationFrames[1] = tmpFrames[0][1];
        animationFrames[2] = tmpFrames[0][2];

        animation = new Animation(1f/3f,animationFrames);
        return animation;
    }

    public void SwapAnimation(Texture t,int w, int h, boolean ty)
    {
        this.texture = t;
        this.w = w;
        this.h = h;
        this.type = ty;

    }


}

