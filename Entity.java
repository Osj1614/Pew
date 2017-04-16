package com.sjo.pew;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import java.util.Iterator;

/**
 * Created by seungjun on 2017-04-14.
 */
public abstract class Entity {
    float x;
    float y;
    float vx = 0;
    float vy = 0;
    float radius;
    float life;
    float maxlife;
    float speed;
    float timer = 0;
    boolean alive = true;
    Color color = Color.WHITE;
    static int ids = 0;
    int id;

    public Entity(float _x, float _y, float _radius, float _life, float _speed) {
        x = _x;
        y = _y;
        radius = _radius;
        maxlife = life = _life;
        speed = _speed;
        id = ids++;
    }

    public void move(float delta, PvpScreen game) {
        x += vx * delta;
        y += vy * delta;
        Iterator<Bullet> bulletIterator = game.bullets.iterator();
        while(bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (id != bullet.id) {
                if (bullet.intersectsWith(this)) {
                    life -= bullet.damage;
                    bulletIterator.remove();
                    if (life <= 0)
                        alive = false;
                }
            }
        }
    }

    public void draw(SpriteBatch batch, PvpScreen game) {
        batch.setColor(color);
        batch.draw(game.playerTex, x - radius, y - radius, radius * 2, radius * 2);
        batch.setColor(1, 0, 0, 1);
        batch.draw(game.Rect, x - radius, y + radius, radius * 2 * life / maxlife, 5);
    }
}
