package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item extends GameObject {
    Sprite sprite;
    Texture texture;
    float x;
    float y;
    private float width;
    private float height;

    public Item(Texture t, float x, float y)
    {
        texture = t;
        sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        sprite.setPosition(x,y);
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void move(float newX, float newY) {

    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return x;
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
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setDirection(SpaceMarine.Direction direction) {

    }

    @Override
    public void follow(float targetX, float targetY) {

    }

}
