package com.davegame.lunerlander.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.davegame.lunerlander.handlers.GameStateManager;
import com.davegame.lunerlander.handlers.MyInput;

public class LevelSelect extends GameState {
	
	private static final int LEVEL_1 = 0;
	private static final int LEVEL_2 =1 ;
	private static final int LEVEL_3 = 2;
	private static final int BACK = 3;
	private int LEVEL_SELECTION;
	
	

	public LevelSelect(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		
	
		
	}

	@Override
	public void handleInput() {
		
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)){
			//cam.update();
			switch(LEVEL_SELECTION){
			case 0:
				gsm.setState(GameStateManager.PLAY);
				break;
			case 1:
				System.out.println("LEVEL 2");
				break;
			case 2:
				System.out.println("LEVEL 3");
				break;
			case 3:
				gsm.setState(GameStateManager.MENU);
				break;
				}
			
			
			}
		
		if(MyInput.isPressed(MyInput.BUTTON_ESCAPE)){
			gsm.setState(GameStateManager.MENU);
		}
		
		if(MyInput.isPressed(MyInput.BUTTON_DOWN)){
			if(LEVEL_SELECTION==LEVEL_1){
				LEVEL_SELECTION = LEVEL_2;
			}else if(LEVEL_SELECTION==LEVEL_2){
				LEVEL_SELECTION = LEVEL_3;
			}else if(LEVEL_SELECTION==LEVEL_3){
				LEVEL_SELECTION = BACK;
			}else if(LEVEL_SELECTION==BACK){
				LEVEL_SELECTION = LEVEL_1;
			}
		}
		if(MyInput.isPressed(MyInput.BUTTON_UP)){
			if(LEVEL_SELECTION==LEVEL_1){
				LEVEL_SELECTION = LEVEL_3;
			}else if(LEVEL_SELECTION==LEVEL_2){
				LEVEL_SELECTION = LEVEL_1;
			}else if(LEVEL_SELECTION==LEVEL_3){
				LEVEL_SELECTION = LEVEL_2;
			}
			else if(LEVEL_SELECTION==BACK){
				LEVEL_SELECTION = LEVEL_3;
			}
		}
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		
	}
	
	private void menuSelection(){
		shapeRenderer2.begin();
		
		shapeRenderer2.set(ShapeType.Line);
		
		switch(LEVEL_SELECTION){
		case 0:
			
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-70, Gdx.graphics.getHeight()/2+27, 150, 32);//Level 1
			break;
		case 1:
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-70, Gdx.graphics.getHeight()/2-24, 150, 32);//Level 2
			break;
		case 2:
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-70, Gdx.graphics.getHeight()/2-73, 150, 32);//Level 3
			break;
		case 3:
			shapeRenderer2.rect(Gdx.graphics.getWidth()/2-70, Gdx.graphics.getHeight()/2-123, 150, 32);//Level 3
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
		
		headingFont.draw(sb, "LEVEL ",  Gdx.graphics.getWidth()/2-130, Gdx.graphics.getHeight()/2+200);
		menuFontItems.draw(sb, "LEVEL 1",  Gdx.graphics.getWidth()/2-65 , Gdx.graphics.getHeight()/2+50);
		menuFontItems.draw(sb, "LEVEL 2",  Gdx.graphics.getWidth()/2-65, Gdx.graphics.getHeight()/2);
		menuFontItems.draw(sb, "LEVEL 3",  Gdx.graphics.getWidth()/2-65, Gdx.graphics.getHeight()/2-50);
		menuFontItems.draw(sb, "BACK",  Gdx.graphics.getWidth()/2-40, Gdx.graphics.getHeight()/2-100);
		
		
		
		
		sb.end();
				
				menuSelection();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
