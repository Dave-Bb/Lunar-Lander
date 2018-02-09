package com.davegame.lunerlander.gameobjects;

import static com.davegame.lunerlander.handlers.B2DVars.PPM;

import java.sql.Struct;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.davegame.lunerlander.handlers.MyContactListener;

//A level is a new world
// with static platforms, landing zones, gravity
// start and end positions
// and a craft


public abstract class Level {
	
	protected World world;
	
	protected BodyDef bdef;
	protected BodyDef bdef2;
	protected FixtureDef fdef; 
	protected Vector2 startPos;
	
	protected Vector2 gravity;
	
	protected Array<Polygon> polys;
	protected Array<Rectangle> rects;
	protected Array<LandingZone> lzArray;
	protected TiledMap tileMap;
	protected TiledMapTileLayer layer;
	protected MyContactListener cl;
	protected FixtureDef fdef2;
	protected String levelNumber;
	protected int landingPoints;
	protected int landingZoneCount;
	protected boolean levelCompleate;
	protected int landingZonesReached;
	
	public Level(Vector2 gravity, String lvlN){
		this.gravity = gravity;
		bdef = new BodyDef();
		fdef = new FixtureDef();
		world = new World(gravity, true);
		world.setContactListener(cl = new MyContactListener());
		polys = new Array<Polygon>();
		rects = new Array<Rectangle>();
		lzArray = new Array<LandingZone>();
		levelNumber = lvlN;
		landingPoints = 0;
		landingZoneCount = 0;
		levelCompleate = true;
		
	}
	
	public int getZoneCount(){
		return landingZoneCount;
	}
	
	public String getZoneLandedCount(){
		return "Landed "+getNumberOfZonesReached()+"/"+landingZoneCount;
		
	}
	
	private int getNumberOfZonesReached(){
		int landedOn = 0;
		for(LandingZone lz:lzArray){
			if(lz.hasLanded()){
				landedOn += 1;
				//checkLevelCompleate();
			}
		}
		return landedOn;
	}
	
	
	public boolean allLandingsCompleate(){
		return getNumberOfZonesReached()==landingZoneCount;
		
	}
	
	public void addLaningZoneCount(){
		landingZoneCount++;
	}
	public String getLevelString(){
		return levelNumber;
	}
	
	public World getWorld(){
		return world;
	}
	
	protected abstract void createLevel();
	
	public Vector2 getStartPos(){
		return startPos;
	}
	
	public void resetLevel(){
		cl.resetCL();
	}
	
	public boolean levelCompleate(){
		return cl.levelCompleated();
	}
	
	public Array<Polygon> getpolys(){
		return polys;
	}
	
	public Array<Rectangle> getRects(){
		return rects;
	}
	
	public Array<LandingZone> getZoneRect(){
		return lzArray;
	}
	
	public void updateLevelPoints(){
		for(LandingZone lz:lzArray){
			landingPoints += lz.getPoints();
		}
	}
	
	public int getLandingPoints(){
		return landingPoints;
	}
	
	public void newRect(Rectangle rect, LandingZone lz){
		//Creates Landing Zones
		float x=rect.getX()/10;//worldToBox
        float y=rect.getY()/10;//WorldToBoc
        //Width and height 
        float width = rect.getWidth();
        float height = rect.getHeight();
        float density=1;
        float restitution=0.2f;
        LandingZone lz2 = lz;
        bdef2 = new BodyDef();
        bdef2.type = BodyType.StaticBody;
        bdef2.position.set(x+width/PPM,y+height/PPM);
        fdef2 = new FixtureDef();
        
        bdef2.angularDamping=1;
        bdef2.linearDamping=10;
        bdef2.fixedRotation=false;
        Body Tbody = world.createBody(bdef2);
        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(width/PPM, height/PPM);
        Object[] ud = new Object[3];
        /*
         *  ud[0] = new String(lz.getGType());
        ud[1] = new String(lz.getPoints());
        ud[2] = new Boolean(false);
         */
        ud = lz.getUserData();
        //System.out.println(lz.getGType());
        fdef2.density=density;
        fdef2.restitution=restitution;
        fdef2.shape=bodyShape;
        
        Tbody.createFixture(fdef2).setUserData(lz);
        

        //LandingZone lz2 = (LandingZone) Tbody.getUserData();
        
        bodyShape.dispose();
      
        
		
	}
	
	
	public void newPolygon(Polygon poly){
		//Creates Landscape
		
		float x=poly.getX()/10;//worldToBox
        float y=poly.getY()/10;//WorldToBoc
        float density=1;
        float restitution=0.2f;
        
        bdef.type = BodyType.StaticBody;
        bdef.position.set(x,y);
        //System.out.println("BOX2dPOLY X :"+poly.getOriginX());


        bdef.angularDamping=1;
        bdef.linearDamping=10;
        bdef.fixedRotation=false;
        Body Tbody = world.createBody(bdef);
        PolygonShape bodyShape = new PolygonShape();
        float[] vertices = poly.getVertices();
        
        


        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            //System.out.println(vertices[i]);
            worldVertices[i] = vertices[i]/10;//WorldToBox
        }

        bodyShape.set(worldVertices);



         
        fdef.density=density;
        fdef.restitution=restitution;
        fdef.shape=bodyShape;

        Tbody.createFixture(fdef);


         bodyShape.dispose();
       Polygon p=new Polygon(vertices);
       //p.setPosition(x/1f, y/1f);
       //p.setScale(0.1f, 0.1f);
       
       //p.setOrigin(poly.getOriginX(), poly.getOriginY());
       poly.scale(-0.9f);
      //poly.setScale(poly.getScaleX()/10, poly.getScaleY()/10);
       //
       poly.setPosition(poly.getX()/10, poly.getY()/10);
       polys.add(poly);

    
		
	}
	
	
}
