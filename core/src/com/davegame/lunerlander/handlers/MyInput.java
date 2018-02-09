package com.davegame.lunerlander.handlers;

public class MyInput {
	
	public static boolean[] keys;
	public static boolean[] pkeys;
	
	public static final int NUM_KEYS = 10;
	public static final int BUTTON1 = 0; //z
	public static final int BUTTON2 = 1;// x
	public static final int BUTTON_ESCAPE= 2;// ESCAPE
	public static final int BUTTON_ENTER = 3;// ENTER
	public static final int BUTTON_LEFT = 4;
	public static final int BUTTON_RIGHT = 5;
	public static final int BUTTON_LEFT_SHIFT = 6;
	public static final int BUTTON_SPACE = 7;
	public static final int BUTTON_UP = 8;
	public static final int BUTTON_DOWN = 9;
	
	
	
	static{
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void update(){
		for(int i = 0; i<NUM_KEYS; i++){
			pkeys[i] = keys[i];
		}
	}
	public static void setKey(int i,boolean b){
		keys[i] =b;
	}
	public static boolean isDown(int i){
		return keys[i];
	}
	
	public static boolean isPressed(int i){
		return keys[i]&&!pkeys[i];
	}

}
