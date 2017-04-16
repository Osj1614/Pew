package com.sjo.pew;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

/**
 * Created by seungjun on 2017-04-14.
 */
public class Enemy extends Entity {

    float shoottimer = 1;
    float totimer = 0;
    float length;
    boolean top;
    Random random = new Random();

    public Enemy(float _x, float _y, float _radius, float _life, float _speed, boolean _top) {
        super(_x, _y, _radius, _life, _speed);
        top = _top;
        color = Color.GRAY;
    }

    @Override
    public void move(float delta, PvpScreen game) {
        super.move(delta, game);
        totimer -= delta;
        length -= delta * speed;
        if(length <= 0) {
            vx = 0;
            vy = 0;
        }
        if (totimer <= 0) {
            totimer = random.nextFloat() * 5;
            float tox = random.nextFloat() * (480 - radius * 2) + radius;
            float toy = random.nextFloat() * (400 - radius * 2) + radius;
            if (top)
                toy += 400;
            float dx = tox - x;
            float dy = toy - y;
            length = (float) Math.hypot(dx, dy);
            vx = dx / length * speed;
            vy = dy / length * speed;
        }

        shoottimer -= delta;
        if (shoottimer <= 0) {
            shoottimer = random.nextFloat() * 3;
            float shootx, shooty;
            if (top) {
                shootx = game.top.x;
                shooty = game.top.y;
            }
            else {
                shootx = game.bot.x;
                shooty = game.bot.y;
            }
            shootx += random.nextFloat() * 40 - 20;
            shooty += random.nextFloat() * 40 - 20;

            float normal = (float) Math.hypot(x - shootx, y - shooty);
            float nx = (shootx - x) / normal;
            float ny = (shooty - y) / normal;
            float r = random.nextFloat() * radius / 2 + radius / 2;
            game.bullets.add(new Bullet(x + (radius + r) * nx, y + (radius + r) * ny, vx / 3 + nx * (speed / 2 + r * 5), vy / 3 + ny * (speed / 2 + r * 5), r, id));
        }
    }
}
