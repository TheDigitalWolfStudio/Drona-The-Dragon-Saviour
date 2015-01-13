package com.digitalwolf.drona.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.moribitotech.mtx.settings.AppSettings;

public class PowerUp extends GameEntity {

	public boolean visible;
	public static float width = 847/9f*AppSettings.getWorldSizeRatio();
	public static float height = 63f*AppSettings.getWorldSizeRatio();
	
	public static final int ONSCREEN = 0;
	public static final int AQUIRED = 1;
	
	public int state;
	
	public float MAX_VELOCITY = 4f;
	
	public PowerUp(float x, float y) {
		super(x, y);
		state = ONSCREEN;
		visible = true;
	}
	
	public void update(float deltaTime) { 
		super.update(deltaTime);
				
		if(position.x <= -width){
			visible = false;
		}
		else{
		    position.x -= MAX_VELOCITY;
		}
		
	    position.add(velocity);		
    }
	
	public Rectangle getBounds(){
    	return new Rectangle(position.x, position.y, width/2, height);
    }	
}
