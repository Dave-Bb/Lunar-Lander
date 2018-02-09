package com.davegame.lunerlander.gameobjects;

import static com.davegame.lunerlander.handlers.B2DVars.PPM;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.davegame.lunerlander.handlers.AssetLoader;
import com.davegame.lunerlander.handlers.MyContactListener;
import com.davegame.lunerlander.handlers.MyInput;

public class Player {
	
	private Body craft;
	private BodyDef bdef;
	private FixtureDef fdef; 
	private PolygonShape shape;
	private MyContactListener cl;
	private boolean isLanded;
	private static boolean isCrashed;
	
	private static float fuel;
	private static int livesLeft;
	
	private Vector2 startPos;
	private static Vector2 position;
	private Vector2 velocity;
	
	protected float width;
	protected float height;
	
	private Texture craftTex;
	private Texture rocketTexture;
	
	private SpriteBatch hudSb;
	private Sprite sprite;
	
	private boolean rocketOn;
	
	private Sound rocketSound;
	
	private float totalFuelUsed;
	private int totalLivesLost;
	private int totalLandings;
	
	private int currentLevel;
	public static final float LEVEL_1_TANK = 100;
	public static final float LEVEL_2_TANK = 150;
	
	public static float fuelTank;
	
	private int landingPoints;
	public Player(int currentLevel){
		isLanded = false;
		isCrashed = false;
		rocketOn = false;
		if(currentLevel ==1){
			fuelTank = LEVEL_1_TANK;
		}else if(currentLevel ==2){
			fuelTank = LEVEL_2_TANK;
		}
		fuel = fuelTank;
		livesLeft = 3;
		
		cl = new MyContactListener();
		bdef = new BodyDef();
		
		
		fdef = new FixtureDef();
		
		shape = new PolygonShape();
		
		
		startPos = new Vector2(160/PPM,4050/PPM);
		position = new Vector2(160/PPM,400/PPM);
		velocity = new Vector2();
		
		craftTex = AssetLoader.craftTex;
		rocketTexture = AssetLoader.rocketTexture;
		
		
		rocketSound = AssetLoader.rocketSound;
		
		landingPoints = 0;
		
		totalFuelUsed =0;
		totalLivesLost =0;
		totalLandings =0;
		
		
		
	}
	
	private void createShip(){
		
		
		
	}
	
	public float totalFuelUsed(){
		return totalFuelUsed;
	}
	
	public int totalLivesLost(){
		return totalLivesLost;
	}
	
	public void setLandingPoints(int lp){
		landingPoints = lp;
	}
	
	public int getLandingPoints(){
		return landingPoints;
	}
	
	public Texture getTexture(){
		return craftTex;
	}
	
	public void handleInput() {
		
		if(MyInput.isPressed(MyInput.BUTTON_LEFT_SHIFT)){
			if(fuel>0){
				rocketSound.loop();
			}
			
		}
		if(MyInput.isDown(MyInput.BUTTON_LEFT)){
			
			rotateLeft();
			
		}if(MyInput.isDown(MyInput.BUTTON_RIGHT)){
			rotateRight();
			
		}if(!MyInput.isDown(MyInput.BUTTON_LEFT)&!MyInput.isDown(MyInput.BUTTON_RIGHT)){
			
			stopRotate();
		}
		if(MyInput.isDown(MyInput.BUTTON_LEFT_SHIFT)){
			rocketOn();
			rocketOn = true;
			
		}if(!MyInput.isDown(MyInput.BUTTON_LEFT_SHIFT)){
			rocketOn = false;
			rocketSound.stop();
		}
		
	}

	
	public void render(SpriteBatch sb){
		
		sb.setColor(255/255.0f,255/255.0f,255/255.0f,1);
		float angle = (float)(craft.getAngle()*(180/Math.PI));
		sb.draw(craftTex, 
				getPosition().x-1.5f, getPosition().y-1.75f, 
				1.5f, 1.75f, 3f, 2.8f, 1, 1, 
				angle, 0, 0, 378, 342, false, false);
	
		//sb.draw(craftTex, craft.getPosition().x-1.5f, craft.getPosition().y-1.75f, 1.5f, 1.5f, 3, 3, 1, 1, angle, 0, 0, 378, 342, false, false);
		//sb.draw(craftTex,position.x+30, ((position.y)), 15f, 7f, 30, 30f, 2f, 2f, angle, 0, 0, 378, 342, false, false);
		if(rocketOn & fuel>0){
			sb.setColor(255/255.0f,88/255.0f,100/255.0f,1);
			//System.out.println("ROCKET On!");
			sb.draw(rocketTexture, 
					getPosition().x-.5f, getPosition().y-1.9f, 
					0.5f, 1.9f, 1f, 1f, 1, 1, 
					angle, 0, 0, 26, 17, false, false);
		}
		
	}
	
	public float getBodyX(){
		return craft.getPosition().x;
	}
	
	public Body getBody(){
		return craft;
	}
	
	public boolean isCrashed(){
		if(velocity.y<-4 || velocity.y >4)
			return isCrashed;
		return false;
	}
	
	public Vector2 getPosition(){
		return position;
	}
	
	public Vector2 getVelocity(){
		return velocity;
	}
	
	public void createBody(World world){
		//position = new Vector2(160/PPM,400/PPM);
		
		bdef.position.set(position); //160/PPM,250/PPM
		bdef.type = BodyType.DynamicBody;
		
		craft = world.createBody(bdef);
		
		Vector2[] vertices = new Vector2[8];
		
		
		float angle ;
		vertices = new Vector2[8];
	    for (int i = 0; i < 8; i++) {
	    	angle =  (float) (-i/6.0f * 360f*(Math.PI/180));
	    	
	    	vertices[i] = new Vector2((float)Math.sin(angle),(float)Math.cos(angle));
	      
	    }
	    
	    vertices[3].set( 0, 0 ); //change one vertex to be pointy
	    shape.set(vertices);
	   
		fdef.shape = shape;
		fdef.density = 0.1f;
		fdef.friction = 0.3f;
		
		craft.createFixture(fdef).setUserData("box");
		
		//CreateCraftBox
		
		//bdef.position.set(160/PPM,150/PPM);
		bdef.type = BodyType.DynamicBody;
		//box = world.createBody(bdef);
		shape.setAsBox(22/PPM, 4/PPM, new Vector2(0,-13f/PPM), 0);
		//shape.setAsBox(22/PPM, 8/PPM);
		fdef.shape = shape;
		craft.createFixture(fdef);
		
		//Leg Left
		shape.setAsBox(1/PPM, 10/PPM, new Vector2(20/PPM,-25f/PPM), (float)(30f*(Math.PI/180)));
		//shape.setAsBox(22/PPM, 8/PPM);
		fdef.shape = shape;
		fdef.density = 0.1f;
		fdef.friction = 0.3f;
		craft.createFixture(fdef);
		
		//Tank
		shape.setAsBox(1/PPM, 10/PPM, new Vector2(-20/PPM,-25f/PPM), (float)(-30f*(Math.PI/180)));
		//shape.setAsBox(22/PPM, 8/PPM);
		fdef.shape = shape;
		fdef.density = 0.1f;
		fdef.friction = 0.3f;
		craft.createFixture(fdef);
		
		//right foot
		
		shape.setAsBox(5/PPM, 0.5f/PPM, new Vector2(-25/PPM,-34f/PPM), 0);
		//shape.setAsBox(22/PPM, 8/PPM);
		fdef.shape = shape;
		fdef.density = 0.1f;
		fdef.friction = 0.3f;
		craft.createFixture(fdef).setUserData("LG");
		
		//Right foot
		
		shape.setAsBox(5/PPM, 0.5f/PPM, new Vector2(25/PPM,-34f/PPM), 0);
		//shape.setAsBox(22/PPM, 8/PPM);
		fdef.shape = shape;
		fdef.density = 0.1f;
		fdef.friction = 0.3f;
		craft.createFixture(fdef).setUserData("LG");
		
		
		
		
		
	}
	public void setStartPos(Vector2 startPos){
		this.startPos.x = startPos.x;
		this.startPos.y = startPos.y;
	}
	
	public boolean isLanded(){
		return isLanded;
	}

	public int livesLeft(){
		return livesLeft;
	
	}
	
	public int getLives(){
		return livesLeft;
	}
	
	public void setLives(int l){
		livesLeft = l;
	}
	public void hasCrashed(){
		isCrashed = true;
		
	}
	
	public static void resetPlayer(){
		livesLeft-=1;
		
		position.x = 160/PPM;
		position.y = 400/PPM;
	}
	

	public float getFuel(){
		return fuel;
	}
	
	private void burnFuel(){
		fuel -= 0.1f;
		totalFuelUsed += 0.1f;
	}
	
	public void rotateLeft(){
		
		craft.setAngularVelocity(2f);
		
	}
	
	public void rotateRight(){
		craft.setAngularVelocity(-2f);
		
		
	}
	
	public void stopRotate(){
		
		if(craft.getAngularVelocity()<0.0f){
			craft.setAngularVelocity(craft.getAngularVelocity()+0.1f);
		}
		
		if(craft.getAngularVelocity()>0.0f)
		{
			craft.setAngularVelocity(craft.getAngularVelocity()-0.1f);
		}
		
		
	}
	
	public void lifeLost(){
		livesLeft -=1;
		totalLivesLost +=1;
	}
	
	public void rocketOn(){
	
		
		if(!isCrashed){
			if(fuel>0){
				float x = (float)Math.sin(craft.getAngle());// X axis multiplyer
				float y = (float)Math.cos(craft.getAngle()); //Y multiplyer
				craft.applyForceToCenter(-1f*x,1f*y,true);
				burnFuel();
				
			}
			
		}
		
	}
	public static void fullTank(){
		
			fuel = fuelTank;
		
		
	}
	public void playerUpdate(){
		if(fuel>0|!isCrashed){
			handleInput();
		}
		
		position.x = craft.getPosition().x;
		position.y = craft.getPosition().y;
		velocity.y = craft.getLinearVelocity().y;
		velocity.x = craft.getLinearVelocity().x;
		if(rocketOn){
			
		}
	}
	

	public static boolean isFull() {
		return fuel==fuelTank;
		
	}
	public void stopSound(){
		rocketSound.stop();
	}
	
	public void destroy(){
		craftTex.dispose();
		rocketTexture.dispose();
		
		
		rocketSound.dispose();
		
	}
}
