package com.davegame.lunerlander.handlers;

import java.util.Stack;

import com.davegame.lunerlander.main.Game;
import com.davegame.lunerlander.states.GameOver;
import com.davegame.lunerlander.states.GameState;
import com.davegame.lunerlander.states.LevelScore;
import com.davegame.lunerlander.states.LevelSelect;
import com.davegame.lunerlander.states.Menu;
import com.davegame.lunerlander.states.Play;

public class GameStateManager {
	
	private Game game;
	
	private Stack<GameState> gameStates;
	
	public static final int PLAY = 1; // create a game state
	public static final int MENU = 2; // create a game state
	public static final int GAME_OVER = 3;
	public static final int LEVEL_SELECT = 4;
	public static final int LEVEL_SCORE = 5;
	//private int currentState;
	
	public GameStateManager(Game game){
		this.game = game;
	
		 gameStates = new Stack<GameState>();
		//pushState(GAME_OVER);
		//pushState(LEVEL_SELECT);
		//pushState(PLAY); //push state onto stack
		
		
		//pushState(LEVEL_SCORE); 
		pushState(MENU);
		
		setState(MENU);
		
		
	}
	
	public Game game(){
		return game;
		
	}
	
	public void update(float dt){
		
		gameStates.peek().update(dt);
		
		
	}
	
	public void render(){
		gameStates.peek().render();
		
	}
	
	//Creates new states 
	private GameState getState(int state){
		if(state == PLAY){
			
			return new Play(this);
		}else if(state == MENU){
			return new Menu(this);
		}else if(state == GAME_OVER){
			return new GameOver(this);
		}else if(state == LEVEL_SELECT){
			return new LevelSelect(this);
		}else if(state == LEVEL_SCORE){
			return new LevelScore(this);
		}
			
		return null;
	}
	
	public void setState(int state){
		popState();
		pushState(state);
		
	}
	
	public void pushState(int state){
		gameStates.push(getState(state));
	}
	
	public void popState(){
		GameState g = gameStates.pop();
		g.dispose();
	}

}
