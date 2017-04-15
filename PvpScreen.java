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
import java.util.Random;

/**
 * Created by seungjun on 2017-04-13.
 */
public class PvpScreen implements Screen {

    final Pew game;
    Random r = new Random();
    OrthographicCamera camera;
    Player top;
    JoyStick topMove;
    JoyStick topShoot;
    float botcharge = 0;
    float topcharge = 0;
    JoyStick botMove;
    JoyStick botShoot;
    Player bottom;
    Array<Bullet> bullets = new Array<Bullet>();
    Texture playerTex;
    Array<Enemy> enemys = new Array<Enemy>();

    public PvpScreen(final Pew pew) {
        game = pew;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        playerTex = new Texture(Gdx.files.internal("hos.png"));

        topMove = new JoyStick(360, 680, 80, playerTex);
        game.touchables.add(topMove);
        topShoot = new JoyStick(120, 680, 80, playerTex);
        game.touchables.add(topShoot);
        top = new Player(240, 600, 150, 300, playerTex, true, topMove, topShoot);

        botMove = new JoyStick(120, 120, 80, playerTex);
        game.touchables.add(botMove);
        botShoot = new JoyStick(360, 120, 80, playerTex);
        game.touchables.add(botShoot);
        bottom = new Player(240, 200, 150, 300, playerTex, false, botMove, botShoot);

        AddEnemy(true);
        AddEnemy(false);
    }

    public void AddEnemy(boolean top) {
        float x, y, speed, life, radius;
        radius = r.nextFloat() * 10 + 15;
        speed = r.nextFloat() * 100 + 50;
        life = r.nextFloat() * 100 + 50;
        y = r.nextFloat() * 400;
        x = 240 + (r.nextInt(2) * 2 - 1) * (240 + radius);
        if(top)
            y += 400;
        enemys.add(new Enemy(x, y, radius, life, speed, playerTex, top));
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

        for(Enemy enemy : enemys){
            enemy.draw(game.batch);
        }

        topShoot.draw(game.batch);
        topMove.draw(game.batch);
        botShoot.draw(game.batch);
        botMove.draw(game.batch);

        game.batch.setColor(Color.WHITE);
        game.font.draw(game.batch, String.valueOf((int) bottom.life) + " : " + String.valueOf((int) top.life), 120, 400);

        game.batch.end();

        game.touchHandler.update();

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while(bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.move(delta);
            if(!bullet.alive)
                bulletIterator.remove();
        }

        for(Iterator<Enemy> enemyIterator = enemys.iterator(); enemyIterator.hasNext(); ) {
            Enemy enemy = enemyIterator.next();
            enemy.move(delta, this);
            if (!enemy.alive) {
                AddEnemy(!enemy.top);
                AddEnemy(!enemy.top);
                enemyIterator.remove();
            }
        }

        top.move(delta, this);
        bottom.move(delta, this);

        if(!top.alive) {
            game.setScreen(new MainMenuScreen(game, 1));
            dispose();
        }
        else if(!bottom.alive) {
            game.setScreen(new MainMenuScreen(game, 2));
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
        playerTex.dispose();

    }
}
