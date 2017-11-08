package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tiles{

    Sprite sprite;
    Texture texture;
    float x;
    float y;

    public Tiles(float x, float y, Texture t)
    {
        texture = t;
        sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        sprite.setPosition(x,y);
    }


    public  void draw(SpriteBatch batch){
        sprite.draw(batch);
    }


    public float getX() {
        return x;
    }


    public float getY() {
        return y;
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public Sprite getSprite() {
        return sprite;
    }

}
