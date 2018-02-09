package com.davegame.lunerlander.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.davegame.lunerlander.gameobjects.LandingZone;
import com.davegame.lunerlander.states.Play;

public class MyContactListener implements ContactListener{
	
	private boolean levelCompleate = false;
	private boolean landed = false;
	private boolean crashed = false;
	
	private LandingZone lz;
	
	Object[] ud;
	
	public void resetCL(){
		levelCompleate = false;
		landed = false;
		crashed = false;
	}
	
	//Called when two fixtures collide 
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		//System.out.println("LANDED");
		//ud = (Object[]) fa.getUserData();
		//lz = (LandingZone) fa.getUserData();
		//System.out.println(ud[0]);
		//System.out.println("A: "+fa.getUserData()+ "B: "+fb.getUserData());
		//System.out.println("CRAFT VEL WAS "+fa.getBody().getLinearVelocity().y);
		//System.out.println("faBody LV x ="+fa.getBody().getLinearVelocity().x);
		if(fb.getBody().getLinearVelocity().y<-4f){
			if(!crashed){
				Play.hasCrashed();
				crashed = true;
			}
			
			//System.out.println("CRASH");
			
			
		}else if(fa.getBody().getLinearVelocity().y<0.1f){}
			//Play.fullTank();
		
	}
	
	//Called when two fixtures no longet
	@Override
	public void endContact(Contact contact) {
		
		if(landed){
		//	System.out.println("LIFT OFF");
			landed = false;
		}
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		//ud = (Object[]) contact.getFixtureA().getUserData();
		lz = (LandingZone) contact.getFixtureA().getUserData();
		//ud = lz.getUserData();
		
		if(!landed&& !Play.tankFull()&& contact.getFixtureA().getUserData()!=null&&
				contact.getFixtureB().getUserData()!=null&&
				contact.getFixtureB().getUserData().equals("LG")&&
				contact.getFixtureB().getBody().getLinearVelocity().x<0.1f &&
				contact.getFixtureB().getBody().getLinearVelocity().x>-0.1f){
			//if(contact.getFixtureA().getUserData().equals("landingZone")){
			//boolean landed = (Boolean) ud[2];
			
			if(!landed){
				//System.out.println("Is landed, is stoped");
				//landed = true;
				// System.out.println(ud[1]+" POINTS");
				//ud[2] = true;
				lz.changeToLanded();
				Play.fullTank();
			}else if(contact.getFixtureA().getUserData().equals("finalLz")){
				levelCompleate = true;
			}
			
		}
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	
		
	}
	
	public boolean levelCompleated(){
		return levelCompleate;
	}

}
