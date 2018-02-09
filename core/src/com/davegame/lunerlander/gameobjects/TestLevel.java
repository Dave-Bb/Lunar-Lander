package com.davegame.lunerlander.gameobjects;

import static com.davegame.lunerlander.handlers.B2DVars.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class TestLevel extends Level {
	
	
	public TestLevel(Vector2 gravity){
		super(gravity,"NULL");
		
		createLevel();
		startPos = new Vector2(160/PPM,130/PPM);
	}
	
	
	@Override
	protected void createLevel(){
		
		
		//Playform 1
		bdef.position.set(160/PPM,0/PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50/PPM, 5/PPM);
		
		
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("GROUND");
		
		//Platform2
		bdef.position.set(500/PPM,300/PPM);
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);
		shape.setAsBox(100/PPM, 5/PPM);
		
		
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("GROUND");
		
		//Platrorm 3
		//Platform2
		bdef.position.set(50/PPM,59/PPM);
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);
		shape.setAsBox(100/PPM, 5/PPM);
		
		
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("GROUND");
		
		//Platform2
		bdef.position.set(40/PPM,100/PPM);
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);
		shape.setAsBox(5/PPM, 5/PPM);
		
		
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("GROUND");
	}

	
	
	

}
