package com.davegame.lunerlander.states;

import java.awt.Font;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.davegame.lunerlander.handlers.AssetLoader;
import com.davegame.lunerlander.handlers.GameStateManager;
import com.davegame.lunerlander.handlers.MyInput;


public class Menu extends GameState{

	private Sprite shipSprite;
	
	
	
	private static final int START = 0;
	private static final int LEVEL_SELECT =1 ;
	private static final int HIGH_SCORE = 2;
	private int MENU_SELECTION;
	
	private Stack<Integer> menuStack;
	
	public Menu(GameStateManager gsm){
		super(gsm);
		
		//cam.setToOrtho(false, Game.V_WIDTH,Game.V_HEIGHT);
		
		
		shipSprite = AssetLoader.shipSprite;
		
		//shapeRenderer
		shapeRenderer2 = new ShapeRenderer();
		shapeRenderer2.setProjectionMatrix(hudCam.combined);
		
		menuStack = new Stack<Integer>();
		menuStack.push(START);
		menuStack.push(LEVEL_SELECT);
		
		MENU_SELECTION = START;
	}

	@Override
	public void handleInput() {
		
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)){
			//cam.update();
			switch(MENU_SELECTION){
			case 0:
				gsm.setState(GameStateManager.PLAY);
				break;
			case 1:
				gsm.setState(GameStateManager.LEVEL_SELECT);
				break;
			case 2:
				System.out.println("HIGH SCORE");
				break;
				
				}
			
			}
		if(MyInput.isPressed(MyInput.BUTTON_ESCAPE)){
			Gdx.app.exit();
			//System.exit(0);
		}
		if(MyInput.isPressed(MyInput.BUTTON_DOWN)){
			if(MENU_SELECTION==START){
				MENU_SELECTION = LEVEL_SELECT;
			}else if(MENU_SELECTION==LEVEL_SELECT){
				MENU_SELECTION = HIGH_SCORE;
			}else if(MENU_SELECTION==HIGH_SCORE){
				MENU_SELECTION = START;
			}
		}
		if(MyInput.isPressed(MyInput.BUTTON_UP)){
			if(MENU_SELECTION==START){
				MENU_SELECTION = HIGH_SCORE;
			}else if(MENU_SELECTION==LEVEL_SELECT){
				MENU_SELECTION = START;
			}else if(MENU_SELECTION==HIGH_SCORE){
				MENU_SELECTION = LEVEL_SELECT;
			}
		}
		
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		
	}
	private void menuSelection(){
		shapeRenderer2.setAutoShapeType(true);

		shapeRenderer2.begin();
		
		shapeRenderer2.set(ShapeType.Line);
		
		switch(MENU_SELECTION){
		case 0:
			
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-55, Gdx.graphics.getHeight()/2+27, 120, 32);//Start
			break;
		case 1:
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-130, Gdx.graphics.getHeight()/2-24, 275, 32);//LevelSelect
			break;
		case 2:
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-105, Gdx.graphics.getHeight()/2-73, 230, 32);//LevelSelect
			break;
			
			
		}
		shapeRenderer2.end();
	}

	@Override
	public void render() {
		//clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(hudCam.combined);
	
		
		sb.begin();
		//font.getData().setScale(2f,2f);
		headingFont.draw(sb, "LANDER ",  Gdx.graphics.getWidth()/2-170, Gdx.graphics.getHeight()/2+200);
		menuFontItems.draw(sb, "START",  Gdx.graphics.getWidth()/2-45 , Gdx.graphics.getHeight()/2+50);
		menuFontItems.draw(sb, "LEVEL SELECT",  Gdx.graphics.getWidth()/2-115, Gdx.graphics.getHeight()/2);
		menuFontItems.draw(sb, "HIGH SCORE",  Gdx.graphics.getWidth()/2-92, Gdx.graphics.getHeight()/2-50);
		
		sb.draw(shipSprite, Gdx.graphics.getWidth()/2, 0,150,150);
		
		
		sb.end();
		
		menuSelection();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
