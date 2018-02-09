package com.davegame.lunerlander.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;



public class MyInputProcessor extends InputAdapter {
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		System.out.println(screenX);
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		
		
		return true;
	}
	
	public boolean keyDown(int k){
		if(k == Keys.Z){
			MyInput.setKey(MyInput.BUTTON1, true);
		}
		if(k == Keys.X){
			MyInput.setKey(MyInput.BUTTON2, true);
		}
		if(k == Keys.ESCAPE){
			MyInput.setKey(MyInput.BUTTON_ESCAPE, true);
		}
		if(k == Keys.ENTER){
			MyInput.setKey(MyInput.BUTTON_ENTER, true);
		}
		if(k == Keys.LEFT){
			MyInput.setKey(MyInput.BUTTON_LEFT, true);
		}
		if(k == Keys.RIGHT){
			MyInput.setKey(MyInput.BUTTON_RIGHT, true);
		}
		if(k == Keys.SHIFT_LEFT){
			MyInput.setKey(MyInput.BUTTON_LEFT_SHIFT, true);
		}if(k == Keys.SPACE){
			MyInput.setKey(MyInput.BUTTON_SPACE, true);
		}
		if(k == Keys.UP){
			MyInput.setKey(MyInput.BUTTON_UP, true);
		}
		if(k == Keys.DOWN){
			MyInput.setKey(MyInput.BUTTON_DOWN, true);
		}
		
		return true;
	}
	
	public boolean keyUp(int k){
		if(k == Keys.Z){
			MyInput.setKey(MyInput.BUTTON1, false);
		}
		if(k == Keys.X){
			MyInput.setKey(MyInput.BUTTON2, false);
		}
		if(k == Keys.ESCAPE){
			MyInput.setKey(MyInput.BUTTON_ESCAPE, false);
		}
		if(k == Keys.ENTER){
			MyInput.setKey(MyInput.BUTTON_ENTER, false);
		}
		if(k == Keys.LEFT){
			MyInput.setKey(MyInput.BUTTON_LEFT, false);
		}
		if(k == Keys.RIGHT){
			MyInput.setKey(MyInput.BUTTON_RIGHT, false);
		}
		if(k == Keys.SHIFT_LEFT){
			MyInput.setKey(MyInput.BUTTON_LEFT_SHIFT, false);
		}if(k == Keys.SPACE){
			MyInput.setKey(MyInput.BUTTON_SPACE, false);
		}if(k == Keys.UP){
			MyInput.setKey(MyInput.BUTTON_UP, false);
		}
		if(k == Keys.DOWN){
			MyInput.setKey(MyInput.BUTTON_DOWN, false);
		}
		
		
		return true;
	}

}
