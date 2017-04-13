package com.sjo.pew;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by seungjun on 2017-04-13.
 */
public class PvpScreen implements Screen {

    final Pew game;
    OrthographicCamera camera;
    Player top ;
    JoyStick topMove;
    JoyStick topShoot;
    float botcharge = 0;
    float topcharge = 0;
    JoyStick botMove;
    JoyStick botShoot;
    Player bottom;
    Array<Bullet> bullets = new Array<Bullet>();
    Texture playerTex;

    public PvpScreen(final Pew pew) {
        game = pew;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        playerTex = new Texture(Gdx.files.internal("hos.png"));

        top = new Player(240, 600, 100, 200, playerTex, true);
        topMove = new JoyStick(360, 680, 80, playerTex);
        game.touchables.add(topMove);
        topShoot = new JoyStick(120, 680, 80, playerTex);
        game.touchables.add(topShoot);

        bottom = new Player(240, 200, 100, 200, playerTex, false);
        botMove = new JoyStick(120, 120, 80, playerTex);
        game.touchables.add(botMove);
        botShoot = new JoyStick(360, 120, 80, playerTex);
        game.touchables.add(botShoot);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
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
        top.draw(game.batch);
        bottom.draw(game.batch);
        for(Bullet bullet : bullets) {
            bullet.draw(game.batch);
        }
        topShoot.draw(game.batch);
        topMove.draw(game.batch);
        botShoot.draw(game.batch);
        botMove.draw(game.batch);
        game.batch.setColor(Color.WHITE);
        game.font.draw(game.batch, String.valueOf((int) bottom.life) + " : " + String.valueOf((int) top.life), 120, 400);
        game.batch.end();

        game.touchHandler.update();
        top.vx = (float) (topMove.percentage * Math.cos(topMove.theta)) * top.speed;
        top.vy = (float) (topMove.percentage * Math.sin(topMove.theta)) * top.speed;
        bottom.vx = (float) (botMove.percentage * Math.cos(botMove.theta)) * bottom.speed;
        bottom.vy = (float) (botMove.percentage * Math.sin(botMove.theta)) * bottom.speed;
        if(topShoot.touchindex != -1 && topcharge < 1)
            topcharge += delta;
        if(botShoot.touchindex != -1 && botcharge < 1)
            botcharge += delta;

        if(topShoot.touchup)
        {
            topShoot.touchup = false;
            float radius = topcharge * 20;
            topcharge = 0;
            float nx = (float) Math.cos(topShoot.theta);
            float ny = (float) Math.sin(topShoot.theta);
            bullets.add(new Bullet( top.x + (radius + top.radius) * nx, top.y + (radius + top.radius) * ny, top.vx + nx * (10 + radius * 10), top.vy + ny * (10 + radius * 10), radius, playerTex));
        }

        if(botShoot.touchup)
        {
            botShoot.touchup = false;
            float radius = 5 + botcharge * 15;
            botcharge = 0;
            float nx = (float) Math.cos(botShoot.theta);
            float ny = (float) Math.sin(botShoot.theta);
            bullets.add(new Bullet(bottom.x + (radius + bottom.radius) * nx, bottom.y + (radius + bottom.radius) * ny, bottom.vx + nx * (10 + radius * 10), bottom.vy + ny * (10 + radius * 10), radius, playerTex));
        }

        top.move(delta);
        bottom.move(delta);
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while(bulletIterator.hasNext())
        {
            Bullet bullet = bulletIterator.next();
            bullet.move(delta);
            if(bullet.alive) {
                if (bullet.intersectsWith(top)) {
                    top.life -= bullet.damage;
                    bulletIterator.remove();
                    if(top.life <= 0)
                        game.dispose();
                } else if (bullet.intersectsWith(bottom)) {
                    bottom.life -= bullet.damage;
                    bulletIterator.remove();
                    if(bottom.life <= 0)
                        game.dispose();
                }
            }
            else
                bulletIterator.remove();
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
        playerTex.dispose();
    }
}
