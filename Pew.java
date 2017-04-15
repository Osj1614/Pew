package com.sjo.pew;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Pew extends Game {
	SpriteBatch batch;
	BitmapFont font;
	Array<Touchable> touchables = new Array<Touchable>();
	TouchHandler touchHandler;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		touchHandler = new TouchHandler(this);
		Gdx.input.setInputProcessor(touchHandler);
		setScreen(new MainMenuScreen(this, 0));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
