package com.davegame.lunerlander.gameobjects;

import static com.davegame.lunerlander.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;


public class Level_One extends Level{
	
	
	
	public Level_One(Vector2 gravity, String map, String levelNumber) {
		
		super(gravity, levelNumber);
		// TODO Auto-generated constructor stub
		//createLevel();
		startPos = new Vector2(160/PPM,400/PPM);
		
		
		tileMap = new TmxMapLoader().load(map);
		//layer = (TiledMapTileLayer) tileMap.getLayers().get("terrain");
		
		
		
		for (MapLayer layer : tileMap.getLayers()) {
			if(layer.getName().matches("terrain")){
				for (MapObject mo : layer.getObjects()){
					if(mo instanceof PolygonMapObject){
						 PolygonMapObject pol=(PolygonMapObject)mo;
                         Polygon poly=pol.getPolygon();
                         
                         //poly.setOrigin(pol.getPolygon().getX()/10,pol.getPolygon().getY()/10);
                         newPolygon(poly);
						
					}
					
				}
				
			}
			if(layer.getName().matches("landingZone")|layer.getName().matches("finalLz")){
				
				for(MapObject mo: layer.getObjects()){
					if(mo instanceof RectangleMapObject){
						RectangleMapObject rect = (RectangleMapObject)mo;
						Rectangle rectA = rect.getRectangle();
						
						LandingZone lz = new LandingZone(mo.getName(),
								layer.getName(),
								(new Rectangle(rectA.x/10,
										rectA.y/10,
										rectA.getWidth()/10,
										rectA.getHeight()/10)));
								
						rects.add(new Rectangle(rectA.x/10,rectA.y/10,rectA.getWidth()/10,rectA.getHeight()/10));//newRect(rectA,layer.getName());
						
						newRect(rectA,lz);
						
						lzArray.add(lz);
						addLaningZoneCount();
					}
				}
			}
			
		}
	}
	
	
	
	
	
	

	@Override
	protected void createLevel() {
		//Playform 1
			bdef.position.set(850/PPM,20/PPM);
			bdef.type = BodyType.StaticBody;
			Body body = world.createBody(bdef);
			
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(800/PPM, 5/PPM);
			
			
			fdef.shape = shape;
			body.createFixture(fdef).setUserData("GROUND");
			
		//Platform
			PolygonShape triangle = new PolygonShape();
			
			Vector2[] vertices = new Vector2[8];
			bdef.position.set(10,10);
			bdef.type = BodyType.StaticBody;
			body = world.createBody(bdef);
			
			float angle ;
			vertices = new Vector2[3];
		    for (int i = 0; i < 3; i++) {
		    	angle =  (float) (-i/1.0f*10 * 60f*(Math.PI/180));
		    	//System.out.println((float)Math.sin(angle));
		      //vertices[i].set(angle, angle);
		    	vertices[i] = new Vector2(2*(float)Math.sin(angle),5*(float)Math.cos(angle));
		      //vertices[i].x = (float)Math.sin(angle);
		      //vertices[i].y = (float)Math.cos(angle);
		    }
		    
		    //vertices[3].set( 0, 0 ); //change one vertex to be pointy
		    shape.set(vertices);
		   
			fdef.shape = shape;
			body.createFixture(fdef).setUserData("GROUND");
			
	}
	
	public void render(ShapeRenderer shapeRenderer){
		shapeRenderer.begin(ShapeType.Line);
		Array<Polygon> pol =getpolys();
		for(Polygon p : pol){
        	//p.setScale(0.1f, 0.1f);
        	//System.out.println("XSXALE: "+p.getScaleX());
        	////////Changed b2dCam
        	//shapeRenderer.scale(.01f, 2f, 2);
        	shapeRenderer.polygon(p.getTransformedVertices());
        }
        
       
        
        for(Rectangle p: rects){
        	shapeRenderer.setColor(255/255.0f,88/255.0f,100/255.0f,1);
        	shapeRenderer.rect(p.x, p.y, p.width, p.height);
        }
        
        shapeRenderer.end();
        
		
	}

}
