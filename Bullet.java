package com.sjo.pew;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by seungjun on 2017-04-13.
 */
public class Bullet {
    float x;
    float y;
    float vx = 0;
    float vy = 0;
    float radius;
    float damage;
    boolean alive = true;
    int id;

    public Bullet(float _x, float _y, float _vx, float _vy, float _radius, int _id)
    {
        x = _x;
        y = _y;
        vx = _vx;
        vy = _vy;
        radius = _radius;
        damage = radius;
        id = _id;
    }

    public void move(float delta)
    {
        x += vx * delta;
        y += vy * delta;
        if(x <= -radius || x >= 480 + radius || y <= -radius || y >= 800 + radius)
            alive = false;
    }

    public boolean intersectsWith(Entity entity)
    {
        return Math.hypot(entity.x - x, entity.y - y) <= radius + entity.radius;
    }

    public void draw(SpriteBatch batch, PvpScreen game)
    {
        batch.setColor(0, 0, 1, 1);
        batch.draw(game.playerTex, x - radius, y - radius, radius * 2, radius * 2);
    }
}
