package com.sjo.pew;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by seungjun on 2017-04-13.
 */
public class TouchHandler implements InputProcessor{

    float[] px = new float[20];
    float[] py = new float[20];
    float[] prevx = new float[20];
    float[] prevy = new float[20];
    int[] pstate = new int[20];
    Pew game;
    OrthographicCamera camera;
    Vector3 tp = new Vector3();


    public TouchHandler(Pew _game)
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        game = _game;
        for(int i = 0; i < 20; i++)
        {
            px[i] = py[i] = prevx[i] = prevy[i] = pstate[i] = 0;
        }
    }

    public void updatetouch(int index, float x, float y, int state)
    {
        prevx[index] = px[index];
        prevy[index] = py[index];
        px[index] = x;
        py[index] = y;
        if(state == 3)
            pstate[index] = 3;
        else if(state == 1)
            pstate[index] = 1;
    }

    public void update()
    {
        for(Touchable t : game.touchables)
            t.Touch(this);
        //터치업데이트
        for(int i = 0; i < 20; i++)
            if(pstate[i] == 3 || pstate[i] == 1)
                pstate[i]--;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        camera.unproject(tp.set(screenX, screenY, 0));
        updatetouch(pointer, tp.x, tp.y, 3);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        camera.unproject(tp.set(screenX, screenY, 0));
        updatetouch(pointer, tp.x, tp.y, 1);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        camera.unproject(tp.set(screenX, screenY, 0));
        updatetouch(pointer, tp.x, tp.y, 2);
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
