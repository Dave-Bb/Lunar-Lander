package com.davegame.lunerlander.states;


import javax.management.timer.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import com.davegame.lunerlander.gameobjects.LandingZone;
import com.davegame.lunerlander.gameobjects.Level_One;
import com.davegame.lunerlander.gameobjects.Player;
import com.davegame.lunerlander.handlers.CamAdjuster;
import com.davegame.lunerlander.handlers.GameStateManager;
import com.davegame.lunerlander.handlers.MyInput;
import com.davegame.lunerlander.main.Game;

public class Play extends GameState{
	
	
	
	private World world; //create a physics worls
	
	
	private Level_One levelOne;
	
	private Level_One levelTwo;
	
	private Level_One level;
	
	private Box2DDebugRenderer b2dr;
	
	//Box 2d Cam
	private OrthographicCamera b2dCam;
	
	private Player playerCraft;
	
	
	private static boolean isCrashed;
	
	private boolean debug;
	
	private boolean freezeRender = false;
	
	private ShapeRenderer shapeRenderer;
	private ShapeRenderer fuelShape;
	
	
	
	private SpriteBatch hudSb;
	//private OrthogonalTiledMapRenderer tmr;
	
	//Cam adjuster
	private CamAdjuster camAdjuster;
	
	//Level states
	public static final int LEVEL_1 = 1; // create a game state
	public static final int LEVEL_2 = 2;
	public static final int LEVEL_3 = 3; // create a game state
	public static final int GAMEOVER = 4;
	
	//pause menu select
	public static final int RESUME = 0;
	public static final int QUIT = 1;
	public static final int RESTART = 2;
	
	private int currentLevel;
	
	private int pauseSelect;
	
	private boolean pause;
	
	private Timer timer;
	
	private float playTime;
	
	
	
	public Play(GameStateManager gsm){
		super(gsm);
		currentLevel = Game.currentLevel;
		
		cam.position.x = 16;
		cam.position.y = 12;
		
		timer = new Timer();
		
		initLevels();
		
		initGameStart();
		
		initBox2DRenderer(false);
		
		//shapeRenderer
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		fuelShape = new ShapeRenderer();
		fuelShape.setProjectionMatrix(hudCam.combined);
		
		
		hudSb = new SpriteBatch();
		hudSb.setProjectionMatrix(hudCam.combined);
		
		
		
		
		camAdjuster = new CamAdjuster(playerCraft, cam);
	
		
		
		playTime = 0.0f;
		
		
	}

	
	private void initBox2DRenderer(boolean willRender){
		debug = willRender;
		b2dr = new Box2DDebugRenderer();
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH,Game.V_HEIGHT);
		
	}
	
	public void restart(){
		world.destroyBody(playerCraft.getBody());
		playTime = 0.0f;
		world = level.getWorld();
		newLevel();
		resetCamera();
		cam.update();
		pause = false;
		unFreezeRender();
		
	}
	
	public void initLevels(){
		levelOne = new Level_One(new Vector2(0,-1f),"assets/levels/testLevel.tmx","Level 1");
		levelTwo = new Level_One(new Vector2(0,-2.0f),"assets/levels/level2.tmx","Level 2");
		//initLevel();
		if(currentLevel==LEVEL_1){
			level = levelOne;
		}else if(currentLevel == LEVEL_2){
			level = levelTwo;
		}
		
		
		
		
	}
	
	public void initGameStart(){
		//level = levelOne;
		playTime = 0.0f;
		world = level.getWorld();
		
		playerCraft = new Player(currentLevel);
		playerCraft.createBody(world);
		
		isCrashed = false;
		pause = false;
		
		pauseSelect = RESUME;
		
		timer.start();
	}
	
	public void nextLife(){
		
		//world.destroyBody(playerCraft.getBody());
		//world.dispose();
		world = level.getWorld();
	}
	
	public void pauseGame(){
		

		if(!pause){
			pause = true;
			freezeRender();
			timer.stop();
			System.out.println(playTime);
			//pauseMenu();
		}else{
			unFreezeRender();
			pause = false;
			timer.start();
		}
			
	
	}
	
	private void pauseMenu(){
		
		shapeRenderer2.begin();
		
		shapeRenderer2.set(ShapeType.Line);
		if(pauseSelect == RESUME){
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-70, Gdx.graphics.getHeight()/2+27, 150, 32);//resume highlight
		}else if(pauseSelect == QUIT){
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-40, Gdx.graphics.getHeight()/2-24, 95, 32);//quit highlight
		}
		else if(pauseSelect == RESTART){
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-72, Gdx.graphics.getHeight()/2+77, 158, 32);//quit highlight
		}
		
		shapeRenderer2.end();
		sb.setProjectionMatrix(hudCam.combined);
		sb.begin();
		menuFontItems.draw(sb, "RESTART",  Gdx.graphics.getWidth()/2-65 , Gdx.graphics.getHeight()/2+100);
		
		menuFontItems.draw(sb, "RESUME",  Gdx.graphics.getWidth()/2-65 , Gdx.graphics.getHeight()/2+50);
		
		menuFontItems.draw(sb, "QUIT",  Gdx.graphics.getWidth()/2-30 , Gdx.graphics.getHeight()/2+0);
		sb.end();
		
	}
	

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ESCAPE)){
			
			//
			pauseGame();

			}
		//cam.update();
		
		if(pause){
			if(MyInput.isPressed(MyInput.BUTTON_ENTER)){
				if(pauseSelect == RESUME){
					pauseGame();
				}else if(pauseSelect == QUIT){
					gsm.setState(GameStateManager.MENU);
				}else if(pauseSelect == RESTART){
					restart();
				}
				
			}
			if(MyInput.isPressed(MyInput.BUTTON_DOWN)){
				if(pauseSelect==RESUME){
					pauseSelect = QUIT;
				}else if(pauseSelect==QUIT){
					pauseSelect = RESTART;
				}else if(pauseSelect==RESTART){
					pauseSelect = RESUME;
				}
			}
			if(MyInput.isPressed(MyInput.BUTTON_UP)){
				if(pauseSelect==RESUME){
					pauseSelect = RESTART;
				}else if(pauseSelect==QUIT){
					pauseSelect = RESUME;
				}else if(pauseSelect ==RESTART){
					pauseSelect = QUIT;
				}
			}
		}
		if(MyInput.isPressed(MyInput.BUTTON_SPACE)){
			if(isCrashed|playerCraft.getFuel()<0){
				if(playerCraft.getLives()==1){
					gameOver();
				}else{
					int lives = playerCraft.getLives();
					world.destroyBody(playerCraft.getBody());
					//world.dispose();
					world = level.getWorld();
					
					
					newLevel();
					playerCraft.setLives(lives-1);
					cam.update();
					level.resetLevel();
					
					isCrashed = false;
					cam.setToOrtho(false, Game.V_WIDTH*1,Game.V_HEIGHT*1);
					unFreezeRender();
				}
				
			}
		}
		
		
		
	}
	
	public void nextLevel(){
		if(currentLevel == LEVEL_1){
			//levelTwo = new Level_One(new Vector2(0,-1.1f),"assets/levels/level2.tmx","Level 2");
			level = levelTwo;
			world.destroyBody(playerCraft.getBody());
			//world.dispose();
			world = level.getWorld();
			
			currentLevel = LEVEL_2;
			System.out.println("Change to level 2");
			newLevel();
			cam.update();
		}
		else if(currentLevel == LEVEL_2){
			levelOne = new Level_One(new Vector2(0,-1f),"assets/levels/testLevel.tmx","Level 1");
			level = levelOne;
			world.destroyBody(playerCraft.getBody());
			world = level.getWorld();
			currentLevel = LEVEL_1;
			newLevel();
			System.out.println("Change to level 1");
			resetCamera();
			cam.update();
			
		}
		
	}
	
	private void newLevel(){
		
		playerCraft = new Player(currentLevel);
		playerCraft.createBody(world);
	}
	
	
	public static void hasCrashed(){
		isCrashed = true;
	
	}
	
	public void freezeRender(){
		freezeRender = true;
	}
	
	public void unFreezeRender(){
		freezeRender = false;
	}
	
	public static void fullTank(){
		//fuel = 100;
		Player.fullTank();
	}
	
	public static boolean tankFull(){
		return Player.isFull();
	}
	
	public void resetCamera(){
		cam.position.set(playerCraft.getPosition().x,playerCraft.getPosition().y,0);
	}
	
	public void gameOver(){
		world.dispose();
		gsm.setState(GameStateManager.GAME_OVER);
	}
	
	@Override
	public void update(float dt) {
		//update the world
		handleInput();
		playerCraft.playerUpdate();
		camAdjuster.adjustCamera();
		level.updateLevelPoints();
		playerCraft.setLandingPoints(level.getLandingPoints());
		
		if(level.allLandingsCompleate()){
			System.out.println("ALL DONE!");
			levelCompleateState();
		}
		if(!freezeRender ){
			world.step(dt, 6, 2);
		}
		
		
		if(level.levelCompleate()){
			
			nextLevel();
		}
		
		playTime+= dt;
		
	}
	
	private void levelCompleateState(){
		//world.destroyBody(playerCraft.getBody());
		//playerCraft.destroy();
		playerCraft.stopSound();
		gsm.setState(GameStateManager.LEVEL_SCORE);
		Game.totalFuelUsed += playerCraft.totalFuelUsed();
		Game.totalLivesLost += playerCraft.totalLivesLost();
		Game.totalLandingPoints += playerCraft.getLandingPoints();
		Game.totalPlayTime += playTime;
	}
	
	private void checkCraftState(){
		
		if(isCrashed){
			sb.setProjectionMatrix(hudCam.combined);
			//font.getData().setScale(1.4f,1.4f);
			font.draw(sb,"YOU CRASHED!",Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2);
			font.getData().setScale(.9f);
			font.draw(sb,"PRESS SPACE TO RESPAWN",Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2-20);
			playerCraft.hasCrashed();
			cam.update();
			freezeRender();
			
			
		}
		
		if(playerCraft.getFuel()<0){
			sb.setProjectionMatrix(hudCam.combined);
			font.getData().setScale(2);
			font.draw(sb,"NO FUEL!",Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2);
			font.getData().setScale(.9f);
			font.draw(sb,"PRESS SPACE TO RESPAWN",Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2-20);
			//freezeRender();
			cam.update();
			
		}
		
	}
	

	@Override
	public void render() {
		//clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//if true render debug
		if(debug){
			b2dr.render(world, cam.combined);
		}
		
		sb.begin();
		
		drawHud();
		
		sb.setProjectionMatrix(cam.combined);
		playerCraft.render(sb);
		
		sb.end();
		
		if(pause){
			pauseMenu();
		}
		
		///DRAW TERRAIN
		drawTerrain();
		sb.begin();
		checkCraftState();
		sb.end();
		 
		cam.update();
	}
	
	private void drawHud(){
		sb.setProjectionMatrix(hudCam.combined);
		
		
		hudFont.draw(sb,"FUEL: "+String.format("%.1f", playerCraft.getFuel()),5,470);  //Fuel 
		hudFont.draw(sb,"Lives: "+playerCraft.livesLeft(),5,445); //Lives
		if(playerCraft.getVelocity().y>-0.1f&playerCraft.getVelocity().y<0.1f){
			hudFont.draw(sb,"Velocity: 0",5,420); //Speed
			
		}else{
			hudFont.draw(sb,"Velocity: "+String.format("%.1f",playerCraft.getVelocity().y),5,420); //Speed
		}
		hudFont.draw(sb,"Landing Points: "+playerCraft.getLandingPoints(),5,390); //Landing Points
		hudFont.draw(sb,level.getZoneLandedCount(),5,365); //Landing Points
		hudFont.draw(sb,level.getLevelString(),Gdx.graphics.getWidth()/2-25,470);  //Level
		
		

		
		
		
	}
	
	private void drawTerrain(){
		
		shapeRenderer.begin(ShapeType.Line);
       
		
		shapeRenderer.polygon(level.getpolys().get(0).getVertices());
       
        Array<Polygon> pol =level.getpolys();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        for(Polygon p : pol){
        	
        	shapeRenderer.setColor(55/255.0f,88/255.0f,100/255.0f,1);
        	shapeRenderer.setProjectionMatrix(cam.combined); ////////Changed b2dCam
        	
        	shapeRenderer.polygon(p.getTransformedVertices());
        }
        
        Array<Rectangle> rec = level.getRects();
        Array<LandingZone> Zrec = level.getZoneRect();
        
        for(LandingZone r: Zrec){
        	Rectangle rLz = r.getRect();
        	
        	if(r.hasLanded()){
        		shapeRenderer.setColor(25/255.0f,255/255.0f,10/255.0f,1);
        		
        	}else{
        		
        		shapeRenderer.setColor(200/255.0f,45/255.0f,5/255.0f,1);
        	}
        	
        	shapeRenderer.setProjectionMatrix(cam.combined);
        	shapeRenderer.rect(rLz.x, rLz.y, rLz.width, rLz.height);
        	
        }
        /*
         * for(Rectangle p: rec){
        	
        	shapeRenderer.setColor(1/255.0f,255/255.0f,100/255.0f,1);
        	shapeRenderer.setProjectionMatrix(cam.combined);
        	shapeRenderer.rect(p.x, p.y, p.width, p.height);
        }
         */
        
		
		shapeRenderer.end();
		
		
	}
	
	
	
	@Override
	public void dispose() {
		
		
		
	}
	
	
	
}




