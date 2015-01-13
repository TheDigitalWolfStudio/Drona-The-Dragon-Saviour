package com.digitalwolf.drona.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.moribitotech.mtx.settings.AppSettings;

public class BlackDragon extends GameEntity{

	public static final float width = (2048/8)*0.5f*AppSettings.getWorldSizeRatio();
	public static final float height = 275*0.5f*AppSettings.getWorldSizeRatio();
	
	public boolean visible;
	
	public static final int NORMAL =0;
	public static final int KILLED =1;
	
	public final float MAX_VELOCITY = 2.5f;
	private boolean rescued;
	public int state;
	public float stateTime;
    public int life;
	
  //VARIABLES FOR TRACKING THE LIFE TIME OF THE BLACK DRAGONS
  		private float startTime = System.nanoTime();
  		public static float SECONDS_TIME = 0;
  		public static float LIFE_TIME = 0;
  		
	public BlackDragon(float x, float y) {
		super(x, y);
		visible = true;
		state = NORMAL;
		rescued = false;
	}
	
	public void update(float deltaTime){
		stateTime+=deltaTime;
		
		if (System.nanoTime() - startTime >= 1000000000) {
			SECONDS_TIME++;
			LIFE_TIME++;
			startTime = System.nanoTime();
		}
		
		if(state == KILLED){
			if(!rescued){
			setLifeTime(0);
			rescued = true;
			}
			
			if(getLifeTime() >=3){
			visible = false;	
			rescued = false;
			}
		}
		
		if(position.x <= -width){
			visible = false;
		}
		else{
			position.x -= MAX_VELOCITY*stateTime;
		}
		
	    position.add(velocity);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(position.x,position.y+height/4, width, height/2);
	}
	
	public float getLifeTime() {
		return LIFE_TIME;
	}
	
	public void setLifeTime(float secondsTime) {
		LIFE_TIME = secondsTime;
	}
	 
}
