package com.davegame.lunerlander.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.davegame.lunerlander.handlers.GameStateManager;
import com.davegame.lunerlander.main.Game;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected Game game;
	
	protected SpriteBatch sb;
	protected SpriteBatch sb2;
	protected OrthographicCamera cam; // main cam
	protected OrthographicCamera hudCam;// hud cam
	
	protected Texture fontTexture;
	protected BitmapFont font ;	

	protected BitmapFont headingFont;
	protected BitmapFont hudFont;
	
	protected BitmapFont menuFontItems;
	protected FreeTypeFontGenerator generator;
	protected FreeTypeFontParameter parameter;
	
	protected ShapeRenderer shapeRenderer2;
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
		game = gsm.game();
		sb = game.getSpriteBatch();
		sb2 = game.getSpriteBatch2();
		cam = game.getCamera();
		hudCam = game.getHUDCamera();
		fontTexture = new Texture(Gdx.files.internal("assets/fonts/font2.png"),true);
		fontTexture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);
		font =  new BitmapFont(Gdx.files.internal("assets/fonts/font2.fnt"), new TextureRegion(fontTexture), false);
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/XPED Expanded.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 72;
		headingFont = generator.generateFont(parameter); // font size 12 pixels
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/XPED Expanded.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 28;
		menuFontItems = generator.generateFont(parameter); // font size 12 pixels
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Dev Gothic.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 28;
		hudFont = generator.generateFont(parameter); // font size 12 pixels
		
		shapeRenderer2 = new ShapeRenderer();
		shapeRenderer2.setProjectionMatrix(hudCam.combined);
		shapeRenderer2.setAutoShapeType(true);
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
}
