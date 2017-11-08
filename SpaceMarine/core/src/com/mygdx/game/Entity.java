package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity extends GameObject {

    Sprite sprite;
    Texture texture;
    float x;
    float y;
    float dx;
    float dy;
    float speed;
    private float width;
    private float height;
    SpaceMarine.Direction direction;
    int health;
    boolean alive;

    public Entity(Texture t, float x, float y,float w, float h, float s){ //type 0

        texture = t;
        sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        sprite.setPosition(x,y);
        dx = 0;
        dy = 0;
        speed = s;
        width = w;
        height = h;
        direction = SpaceMarine.Direction.D;
        health = 100;
        alive = true;
    }

    public void loseHealth(int h)
    {
        health-=h;
    }

    public void gainHealth(int h)
    {
        health+=h;
    }

    public int getHealth()
    {
        return health;
    }

    public boolean isAlive()
    {
        return alive;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void move(float newX, float newY) {
        x = newX;
        y = newY;

        sprite.setPosition(newX,newY);
    }

    @Override
    public void draw(SpriteBatch batch) {
        //sprite.draw(batch);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getDX() {
        return dx;
    }

    @Override
    public float getDY() {
        return dy;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setDirection(SpaceMarine.Direction direction) {
        this.direction = direction;
    }

    @Override
    public void follow(float targetX, float targetY) {

    }

}
