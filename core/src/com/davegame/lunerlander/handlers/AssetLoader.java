package com.davegame.lunerlander.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AssetLoader {
	
	public static Texture craftTex;
	public static Texture rocketTexture;
	public static Sound rocketSound;
	public static Sprite shipSprite;
	
	public static void load(){
		
		craftTex = new Texture(Gdx.files.internal("assets/images/craft.png"));
		rocketTexture = new Texture(Gdx.files.internal("assets/images/rocket.png"));
		rocketSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/rocketSound.mp3"));
		shipSprite = new Sprite(new Texture("assets/images/craft.png"));
		
	}
	
	public static void dispose(){
		
	}

}
