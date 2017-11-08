package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


public class Player extends Entity{  //type 1

    private ArrayList<Bullet> bullets;
    private int armor;

    public Player(float x, float y, ArrayList<Bullet> b)
    {
        super(new Texture("player1.png"),x,y,36,42,120f); //texture represents accurate width and height
                                                                                 //player will have same width and height always
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        speed = 120.0f;
        bullets = b;
        direction = SpaceMarine.Direction.D;
        armor = 200;
        alive=true;

    }

    public void loseArmor()
    {
        armor--;
    }

    public void gainArmor(int a)
    {
        armor+=a;
    }

    public int getArmor()
    {
        return armor;
    }

    @Override
    public void update(float delta) {

        dx = 0;
        dy = 0;

        // move
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            dy = speed * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            dy = -speed * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx = -speed * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx = speed * delta;
        }

    }


}
