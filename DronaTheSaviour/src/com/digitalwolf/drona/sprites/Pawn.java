package com.digitalwolf.drona.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.moribitotech.mtx.settings.AppSettings;

public class Pawn extends GameEntity{

	public boolean visible;
	public static float width = 90f*AppSettings.getWorldSizeRatio();
	public static float height = 90f*AppSettings.getWorldSizeRatio();
	
	public static final int NORMAL = 0;
	public static final int DESTROYING = 1;
	public int state;
	
	public static final int BLUE = 0;
	public static final int ORANGE = 1;
	public static final int RED = 2;
	public int type;
    
    public float stateTime;  
    public float MAX_VELOCITY = 1.5f;
    
	public Pawn(float x, float y, int type) {
		super(x, y);
		visible = true;
		stateTime =0;
		state = NORMAL;
		this.type = type;
	}
	
	public void update(float deltaTime) { 
		super.update(deltaTime);
		stateTime+= deltaTime;
		
		if(position.x <= -width){
			visible = false;
		}
		else{
		    
			position.x -= MAX_VELOCITY*stateTime;
		}
		
	    position.add(velocity);
		
    }
	
	public Rectangle getBounds(){
    	return new Rectangle(position.x, position.y, width, height);
    }

}
