package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Slime extends Enemy {
    public Slime(float x, float y, float w, float h) {
        super(new Texture("Slime.png"), x, y, w, h);
        type = 3;
        speed = 140f;
        health = 250;
    }
}
