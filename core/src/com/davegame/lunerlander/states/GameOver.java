package com.davegame.lunerlander.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.davegame.lunerlander.handlers.GameStateManager;
import com.davegame.lunerlander.handlers.MyInput;
import com.davegame.lunerlander.main.Game;

public class GameOver extends GameState{

	public GameOver(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)){
			Game.currentLevel =1;
			gsm.setState(GameStateManager.MENU);
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
		headingFont.draw(sb, "GAME",  Gdx.graphics.getWidth()/2-130, Gdx.graphics.getHeight()/2+70);
		headingFont.draw(sb, "OVER ",  Gdx.graphics.getWidth()/2-120, Gdx.graphics.getHeight()/2+0);
		
		
		
		
		sb.end();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
