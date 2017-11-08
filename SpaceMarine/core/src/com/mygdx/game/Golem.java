package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Golem extends Enemy {

    public Golem(float x, float y, float w, float h) {
        super(new Texture("Golem_down.png"), x, y, w, h);
        speed = 70f;
        type = 2;
        health = 1000;
    }
}
