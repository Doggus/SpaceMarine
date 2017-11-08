package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Skull extends Enemy {

    public Skull(float x, float y, float w, float h) {
        super(new Texture("Flameskull_down.png"), x, y, w, h);
        speed = 80f; //can set speed to my liking
        health = 500;
        type = 1;
    }
}
