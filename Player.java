package com.sjo.pew;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by seungjun on 2017-04-13.
 */
public class Player {
    float x;
    float y;
    float vx = 0;
    float vy = 0;
    Texture tex;
    float radius = 20;
    float life;
    float speed;
    boolean top;

    public Player(float _x, float _y, float _life, float _speed, Texture _tex, boolean _top) {
        x = _x;
        y = _y;
        life = _life;
        speed = _speed;
        tex = _tex;
        top = _top;
    }

    public void move(float delta)
    {
        float ax = x + vx * delta;
        if(ax >= radius && ax <= 480 - radius)
            x = ax;

        float ay = y + vy * delta;
        if(!top && ay >= radius && ay <= 400 - radius || top && ay >= 400 + radius && ay <= 800 - radius)
            y = ay;
    }

    public void draw(SpriteBatch batch) {
        batch.setColor(0, 1, 0, 1);
        batch.draw(tex, x - radius, y - radius, radius * 2, radius * 2);
    }
}
