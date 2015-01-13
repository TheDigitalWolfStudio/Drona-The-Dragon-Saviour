package com.digitalwolf.drona.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.moribitotech.mtx.settings.AppSettings;

public class MagicalPower extends GameEntity{

   public float MAX_VELOCITY = 4f;
	
	
	public boolean visible;
    public static float width = 60*AppSettings.getWorldSizeRatio();
    public static float height = 60*AppSettings.getWorldSizeRatio();
    
	public MagicalPower(float x, float y) {
		super(x, y);
		visible = true;
	}
	
	public void update(float deltaTime) { 
		super.update(deltaTime);	
		position.add(velocity);
		
		if(position.x <= -width){
			visible = false;
		}
		else{
		    position.x -= MAX_VELOCITY;
		}		
    }
	
	public Rectangle getBounds(){
    	return new Rectangle(position.x, position.y, width, height);
    }
}
