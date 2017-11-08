package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OtherTiles {

    Sprite sprite;
    Texture texture;
    float x;
    float y;

    public OtherTiles(float x, float y, Texture t)
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

    public  void setTexture(Texture t){
        this.texture = t;
    }


}
