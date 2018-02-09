package com.davegame.lunerlander.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.davegame.lunerlander.handlers.GameStateManager;
import com.davegame.lunerlander.handlers.MyInput;
import com.davegame.lunerlander.main.Game;

public class LevelScore extends GameState{
	private int n1;
	private int n2;

	public LevelScore(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		shapeRenderer2 = new ShapeRenderer();
		shapeRenderer2.setProjectionMatrix(hudCam.combined);
		
		n1 = 12;
		n2 = 343;
		Game.currentLevel += 1;
	}

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)){
			if(Game.currentLevel==3){
				gsm.setState(GameStateManager.GAME_OVER);
			}else
				gsm.setState(GameStateManager.PLAY);
			
			}
		
		
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(hudCam.combined);
	
		
		sb.begin();
		//font.getData().setScale(2f,2f);
		/////////////////HEADING
		headingFont.draw(sb, "LEVEL",  Gdx.graphics.getWidth()/2-135, Gdx.graphics.getHeight()/2+200);
		menuFontItems.getData().setScale(1);
		menuFontItems.draw(sb, "COMPLEATE",  Gdx.graphics.getWidth()/2-100, Gdx.graphics.getHeight()/2+140);
		//////////////////////////
		//SCORES 
		menuFontItems.getData().setScale(0.8f);
		menuFontItems.draw(sb, "FUEL USED\t",  Gdx.graphics.getWidth()/2-230 , Gdx.graphics.getHeight()/2+80);
		menuFontItems.draw(sb, " "+String.format("%.1f", Game.totalFuelUsed),  Gdx.graphics.getWidth()/2+150 , Gdx.graphics.getHeight()/2+80);
		
		menuFontItems.draw(sb, "LIVES LOST\t",  Gdx.graphics.getWidth()/2-230, Gdx.graphics.getHeight()/2+30);
		menuFontItems.draw(sb, ""+Game.totalLivesLost,  Gdx.graphics.getWidth()/2+150, Gdx.graphics.getHeight()/2+30);
		
		menuFontItems.draw(sb, "LANDING POINTS\t"+34,  Gdx.graphics.getWidth()/2-230, Gdx.graphics.getHeight()/2-20);
		menuFontItems.draw(sb, ""+Game.totalLandingPoints,  Gdx.graphics.getWidth()/2+150, Gdx.graphics.getHeight()/2-20);
		
		menuFontItems.draw(sb, "BONUS POINTS\t",  Gdx.graphics.getWidth()/2-230, Gdx.graphics.getHeight()/2-70);
		menuFontItems.draw(sb, ""+n1,  Gdx.graphics.getWidth()/2+150, Gdx.graphics.getHeight()/2-70);
		
		menuFontItems.draw(sb, "TIME\t"+n1,  Gdx.graphics.getWidth()/2-230, Gdx.graphics.getHeight()/2-120);
		menuFontItems.draw(sb, ""+String.format("%.2f", Game.totalPlayTime),  Gdx.graphics.getWidth()/2+150, Gdx.graphics.getHeight()/2-120);
		
		menuFontItems.getData().setScale(0.9f);
		menuFontItems.draw(sb, "NEXT",  Gdx.graphics.getWidth()/2-35, Gdx.graphics.getHeight()/2-170);
		
		
		
		sb.end();
		shapeRenderer2.setAutoShapeType(true);

		shapeRenderer2.begin();
		
		shapeRenderer2.set(ShapeType.Line);
		shapeRenderer2.rect(Gdx.graphics.getWidth()/2-50, Gdx.graphics.getHeight()/2-192, 100, 32);//Start
		shapeRenderer2.end();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
