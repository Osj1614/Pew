package com.sjo.pew;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by seungjun on 2017-04-15.
 */
public class MainMenuScreen implements Screen {
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    final Pew game;
    OrthographicCamera camera;
    int win;

    public MainMenuScreen(final Pew _game, int _win) {
        game = _game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        win = _win;
    }

    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        CharSequence message;
        switch(win){
            case 1: message = "아래승~";
                break;
            case 2: message = "위승~";
                break;
            default: message = "퓨!";
                break;
        }
        game.batch.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, message, 40, 400);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new PvpScreen(game));
            dispose();
        }
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
