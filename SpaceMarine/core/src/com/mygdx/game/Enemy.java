package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity{ //type 2

    private Vector2 position;
    private Vector2 velocity;
    int type;

    public Enemy(Texture t,float x, float y,float w, float h){

        super(t,x,y,w,h,120f);

        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        direction = SpaceMarine.Direction.D;
        position = new Vector2();
        velocity = new Vector2();
        position.x = x;
        position.y = y;
        alive=true;
        type = 0;
    }

    public int getType()
    {
        return  type;
    }

    @Override
    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);
        velocity.scl(1 - (0.98f * delta)); // Linear dampening, otherwise the ball will keep going at the original velocity forever

        move(position.x,position.y);
    }

    @Override
    public void follow(float targetX, float targetY) {
        velocity.set(targetX - position.x, targetY - position.y).nor().scl(Math.min(position.dst(targetX, targetY), speed));
    }

}
