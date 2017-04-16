package com.sjo.pew;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by seungjun on 2017-04-13.
 */
public class Player extends Entity {
    boolean top;
    JoyStick move;
    JoyStick shoot;
    float charge = 0;
    float power = 20;
    float heal = 10;

    public Player(float _x, float _y, float _life, float _speed, boolean _top, JoyStick _move, JoyStick _shoot) {
        super(_x, _y, 20, _life, _speed);
        top = _top;
        move = _move;
        shoot = _shoot;
        color = Color.GREEN;
    }

    public void move(float delta, PvpScreen game)
    {
        vx = (float) (move.percentage * Math.cos(move.theta)) * speed;
        vy = (float) (move.percentage * Math.sin(move.theta)) * speed;
        super.move(delta, game);

        life += heal * delta;
        if(life > maxlife)
            life = maxlife;

        if(x < radius)
            x = radius;
        else if(x > 480 - radius)
            x = 480 - radius;

        if(top)
        {
            if(y < 400 + radius)
                y = 400 + radius;
            else if(y > 800 - radius)
                y = 800 - radius;
        }
        else
        {
            if(y < radius)
                y = radius;
            else if(y > 400 - radius)
                y = 400 - radius;
        }

        if(shoot.touchindex != -1 && charge < 1)
            charge += delta;

        if(shoot.touchup)
        {
            shoot.touchup = false;
            float r = charge * power / 2 + power;
            charge = 0;
            float nx = (float) Math.cos(shoot.theta);
            float ny = (float) Math.sin(shoot.theta);
            game.bullets.add(new Bullet(x + (radius + r) * nx, y + (radius + r) * ny, vx / 3 + nx * (speed + r * 5), vy / 3 + ny * (speed + r * 5), r, id));
        }
    }
}
