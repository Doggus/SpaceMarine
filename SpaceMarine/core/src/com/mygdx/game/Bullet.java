package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends GameObject{ //type 3

    private Texture texture;
    private Sprite sprite;
    private float lifeTime;
    private float lifeTimer;
    private boolean remove;
    private float x;
    private float y;
    private SpaceMarine.Direction direction;
    private float speed;

    private Vector2 position;
    private Vector2 velocity;
    boolean shot;
    private int damage;

    public Bullet(float x, float y,int d)
    {
        texture = new Texture("bullet.png");
        sprite = new Sprite(texture);
        this.x = x; //x-position
        this.y = y; //x-position
        sprite.setPosition(x,y);
        this.speed = 300; //speed of bullet

        lifeTimer = 0;
        lifeTime = 1f; //how long the bullet lasts for

       velocity = new Vector2();
       position = new Vector2();
       position.x = x;
       position.y = y;
       shot = false;
       damage =d;

    }

    public boolean Remove()
    {
        return remove;
    }

    public void shoot(float targetX, float targetY)
    {
        velocity.set(targetX - position.x, targetY - position.y).nor().scl(speed);
        shot = true;
    }

    public int getDamage()
    {
        return damage;
    }

    @Override
    public void setDirection(SpaceMarine.Direction d)
    {
        direction = d;
    }

    @Override
    public void follow(float targetX, float targetY) {

    }

    @Override
    public void update(float delta)
    {

        lifeTimer += delta;
        if (lifeTimer > lifeTime)
        {
            remove = true;
        }

        position.add(velocity.x * delta, velocity.y * delta);
        //velocity.scl(1 - (0.98f * delta));

        move(position.x,position.y);
    }

    @Override
    public void move(float newX, float newY)
    {
        x = newX;
        y = newY;

        sprite.setPosition(newX,newY);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
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
        return 0;
    }

    @Override
    public float getDY() {
        return 0;
    }

    @Override
    public float getWidth() {
        return sprite.getWidth();
    }

    @Override
    public float getHeight() {
        return sprite.getWidth();
    }

}
