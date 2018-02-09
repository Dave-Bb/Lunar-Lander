package com.davegame.lunerlander.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.davegame.lunerlander.gameobjects.Player;

public class CamAdjuster {
	
	private Player player;
	private OrthographicCamera cam;
	
	public CamAdjuster(Player player, OrthographicCamera cam){
		this.player = player;
		this.cam = cam;
	}
	
	public void adjustCamera(){
		//System.out.println("CAM ADJUST PLAYER X: "+player.getPosition().x);
		if(player.getPosition().x-cam.position.x>4){
			//System.out.println("SLIDE "+(player.getPosition().x-cam.position.x));////////Changed b2dCam
			slideCameraRight();
			cam.update();
		}
		
		 if(player.getPosition().x>8&&player.getPosition().x-cam.position.x<-10){////////Changed b2dCam
			slideCameraLeft();
			cam.update();
		}
		
		if(player.getPosition().y>8 &&player.getPosition().y-cam.position.y<-4){////////Changed b2dCam
			slideCameraDown();
			cam.update();
		}
		 
		if(player.getPosition().y>10 &&player.getPosition().y-cam.position.y>8){ ////////Changed b2dCam
			slideCameraUp();
			cam.update();
		}
	}
	
	private void slideCameraUp(){
		cam.position.set(cam.position.x,player.getPosition().y-8f,0);////////Changed b2dCam
		
	}
	
	private void slideCameraDown(){
		cam.position.set(cam.position.x,player.getPosition().y+4f,0);////////Changed b2dCam
	}
	
	private void slideCameraRight(){
		cam.position.set(player.getPosition().x-4f,cam.position.y,0);////////Changed b2dCam
	}
	
	private void slideCameraLeft(){
		cam.position.set(player.getPosition().x+10f,cam.position.y,0);////////Changed b2dCam
	}

}
