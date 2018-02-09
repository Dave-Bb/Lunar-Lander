package com.davegame.lunerlander.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.davegame.lunerlander.handlers.MyInputProcessor;
import com.davegame.lunerlander.handlers.AssetLoader;
import com.davegame.lunerlander.handlers.GameStateManager;
import com.davegame.lunerlander.handlers.MyInput;

public class Game implements ApplicationListener {
	
	public static final String TITLE = "Luner Lander";
	public static final int V_WIDTH = 32;
	public static final int V_HEIGHT = 24;
	public static final int SCALE = 20;
	//when app starts
	
	//Using fixed frame rate
	public static final float STEP = 1/60f;
	private float accum;
	
	private SpriteBatch sb;
	private SpriteBatch sb2;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;	
	
	public static float totalFuelUsed =0;
	public static int totalLivesLost=0;
	public static int totalLandingPoints=0;
	public static int currentLevel = 1;
	public static float totalPlayTime = 0.0f;

	@Override
	public void create () {
		AssetLoader.load();
		sb = new SpriteBatch();
		sb2 = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH*20, V_HEIGHT*20);;
		
		gsm = new GameStateManager(this);
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		
		
	}
	
	//looping method
	@Override
	public void render () {
		//Gdx.gl.glClearColor(0,0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		////hudCam.update();
		//cam.update();
		//onbly update and render if enough time has passed for a step
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP){
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			MyInput.update();
			
		}
		
	}
	
	@Override
	public void dispose () {
		Gdx.app.exit();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public SpriteBatch getSpriteBatch(){
		return sb;
	}
	public SpriteBatch getSpriteBatch2(){
		return sb2;
	}
	
	public OrthographicCamera getCamera(){
		return cam;
	}
	
	public OrthographicCamera getHUDCamera(){
		return hudCam;
	}
	
}
