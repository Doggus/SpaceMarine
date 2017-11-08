package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {
    public abstract void update(float delta);
    public abstract void move(float newX, float newY);
    public abstract void draw(SpriteBatch batch);
    public abstract float getX();
    public abstract float getY();
    public abstract float getDX();
    public abstract float getDY();
    public abstract float getWidth();
    public abstract float getHeight();
    public abstract void setDirection(SpaceMarine.Direction direction);
    public abstract void follow(float targetX, float targetY);


}
