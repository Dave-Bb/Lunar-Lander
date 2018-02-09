package com.davegame.lunerlander.gameobjects;

import com.badlogic.gdx.math.Rectangle;

public class LandingZone {
	
	private boolean hasLanded;
	private String points;
	private static String type;
	private boolean isFinish;
	private Rectangle rect;
	private Object[] userData;
	private boolean pointsSent;
	
	public LandingZone(String points, String type, Rectangle rect){
		hasLanded = false;
		this.points = points;
		this.type = type;
		isFinish = false;
		this.rect = rect;
		userData = new Object[3];
		userData[0] = type;
		userData[1] = points;
		userData[2] = hasLanded;
		pointsSent = false;
	}
	
	public LandingZone(String points, String type){
		hasLanded = false;
		this.points = points;
		this.type = type;
		isFinish = false;
		
	}
	
	public static String getGType(){
		return type;
	}
	
	public int getPoints(){
		if(hasLanded){
			if(pointsSent){
				return Integer.parseInt("0");
			}
			else{
				pointsSent = true;
				System.out.println("Points! "+points);
				return Integer.parseInt("1");
				
			}
			
		}else
			return 0;
		
		
			
		
	}
	
	public void changeToLanded(){
		hasLanded = true;
	}
	
	public boolean hasLanded(){
		return hasLanded;
	}
	
	public Rectangle getRect(){
		return rect;
	}
	
	public Object[] getUserData(){
		return userData;
	}

}
