package com.sjo.pew;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by seungjun on 2017-04-13.
 */
public class JoyStick implements Touchable
{
    float x;
    float y;
    float ix;
    float iy;
    int touchindex = -1;

    float iradius;
    float radius;
    double theta = 0;
    float percentage = 0;
    boolean touchup = false;
    Texture tex;

    public JoyStick(float _x, float _y, float _radius, Texture _tex)
    {
        x = ix = _x;
        y = iy = _y;
        radius = _radius;
        iradius = radius / 2;
        tex = _tex;
    }

    public void draw(SpriteBatch batch)
    {
        batch.setColor(1, 1, 1, 0.2f);
        batch.draw(tex, x - radius, y - radius, radius * 2, radius * 2);
        batch.setColor(1, 1, 1, 0.8f);
        batch.draw(tex, ix - iradius, iy - iradius, iradius * 2, iradius * 2);
    }

    @Override
    public void Touch(TouchHandler th)
    {
        if(touchindex == -1)
        {
            for(int i = 0; i < 20; i++)
            {
                if(th.pstate[i] == 3) {
                    if (Math.hypot(th.px[i] - x, th.py[i] - y) <= radius) {
                        touchindex = i;
                        ix = th.px[i];
                        iy = th.py[i];
                        break;
                    }
                }
            }
        }
        else
        {
            if(th.pstate[touchindex] != 1) {
                ix = th.px[touchindex];
                iy = th.py[touchindex];
                theta = Math.atan2(iy - y, ix - x);

                float length =(float) Math.hypot(ix - x, iy - y);
                if(length > radius)
                {
                    ix = (float) (x + Math.cos(theta) * radius);
                    iy = (float) (y + Math.sin(theta) * radius);
                    length = radius;
                }
                percentage = (float) length / radius;
            }
            else {
                ix = x;
                iy = y;
                percentage = 0;
                touchindex = -1;
                touchup = true;
            }
        }
    }
}

