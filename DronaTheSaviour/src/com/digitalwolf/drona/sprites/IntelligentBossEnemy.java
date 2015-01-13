package com.digitalwolf.drona.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.digitalwolf.drona.assets.Assets;
import com.moribitotech.mtx.settings.AppSettings;

public class IntelligentBossEnemy extends AISprite{

	public static final int APPEARING = 0;
	public static final int FLYING = 1;
    public static final int ATTACKING = 2;
    public static final int HIT = 3;
    public static final int DYING = 4;

    public static float width = 288/3f*AppSettings.getWorldSizeRatio();
    public static float height = 384/4f*AppSettings.getWorldSizeRatio();
    private boolean toggleBoolean;
    
    public int life;
	private int state;
	public boolean visible;

	private ArrayList<Weapon> weapons;
	boolean reloaded = false;   // Start with a reload because the weapons of the enemy will not reload itself
	float reloadTime = 100;     // This is the "timer" (The value doesn't matter)
	final float startReloadTime = 100;
	private boolean rescued;
	
	public IntelligentBossEnemy(Array<Vector2> path, float x, float y) {
		super(path, x, y);
		stateTime = 0;
		visible =true;
		weapons = new ArrayList<Weapon>();
		life = 10;
		state = APPEARING;
		rescued = false;
		toggleBoolean = false;
	}


	@Override
	public void draw(SpriteBatch batch){
		//DRAW THE LIFE INDICATOR
				TextureRegion indicator = null;
		 
		    	switch(life/2){
		    	case 0:
		    		indicator = Assets.enerylevel1;
		    		break;
		    		
		    	case 1:
		    		indicator = Assets.enerylevel1;
		    		break;
		    		
		    	case 2:
		    		indicator = Assets.enerylevel2;
		    		break;
		    		
		    	case 3:
		    		indicator = Assets.enerylevel3;
		    		break;
		    		
		    	case 4:
		    		indicator = Assets.enerylevel4;
		    		break;
		    		
		    	case 5:
		    		indicator = Assets.enerylevel5;
		    		break;  		
		    	}
		    
		//DRAW THE CHARACTER
		TextureRegion frame = null;
		switch(state){
		case 0:
			frame = Assets.bossEnemyAppearing.getKeyFrame(getStateTime());
			break;

		case 1:
			frame = Assets.bossEnemyFlying.getKeyFrame(getStateTime());
			break;
			
		case 2:
			frame = Assets.bossEnemyFlying.getKeyFrame(getStateTime());
			break;
			
		case 3:
			frame = Assets.bossEnemyFlying.getKeyFrame(getStateTime());
			break;
			
		case 4:
			frame = Assets.bossEnemyFlyAway.getKeyFrame(getStateTime());
			break;			
		}
		
		batch.begin();
		batch.draw(frame, getX(), getY(), 0, 0, width, height, 1.0f, 1.0f, getRotation());
		batch.draw(indicator, AppSettings.SCREEN_W - 60, 100, 50,150);
		
		//DRAW THE WEAPONS
		if(weapons.size() >0){
			for(int i=0; i< weapons.size();i++){
				Weapon w = weapons.get(i);				
				TextureRegion weaponFrame = Assets.splashAnim.getKeyFrame(getStateTime());//change
				if(w.visible){
					batch.draw(weaponFrame, w.position.x, w.position.y, 282/6*0.7f, 66*0.7f);
				}				
			}
		}
		
		batch.end();
		
	}
	
	public void fire(){
		state = ATTACKING;
		Weapon w = new Weapon(getX()-width/2, getY(), Weapon.NORMAL);
		weapons.add(w);
	}
	
	public void appearing(){
		setY(getY()-1);
		if(getY()<= AppSettings.SCREEN_H/2){
			state = FLYING;
		}
	}
	
	
	public ArrayList<Weapon> getWeapons(){
		return this.weapons;
	}
	
	@Override
	public void update(float deltaTime, float targetX, float targetY) {
		super.update(deltaTime,targetX,targetY);

		//CHECK THIS CONDITION IN WORST CASE
		if(state == APPEARING && getLifeTime()>=5){
			state = FLYING;
		}
		
		if(state == HIT || state == ATTACKING){
			if(!rescued){
			setLifeTime(0);
			rescued = true;
			}
			
			if(getLifeTime() >=3){
			setState(FLYING);	
			rescued = false;
			}
		}
		
		if(state == APPEARING){
			appearing();
		}
		
		if(state!=APPEARING){
		// The enemy will be updated once it has appeared from the top		
		
		if (!reloaded) {
	        reloadTime -= 500*deltaTime; // As I said, this is the timer

	        // If the reload finished, set the timer back to the reload time
	        if (reloadTime <= 0) {
	            reloaded = true; // reloaded, we can shoot
	            reloadTime = startReloadTime;
	        }
	    }
		
		if(isWayPointReached()){
		    setPosition(path.get(waypoint).x, path.get(waypoint).y);
			if(waypoint+1 >= path.size){		
			}
			else{
			waypoint+=1;
			}
			
		}
		
		if(state == FLYING){
		if(Math.abs(targetY- getY())<=30 && reloaded){
			fire();
			reloaded = false;
		}

			if(SECONDS_TIME%1==0){
			boolean moveDecision = getNextToggle();
			if(moveDecision){
				getFarPositionPath(targetY);
			}
			else{
				getNearPositionPath(targetY);
			}
			}
		}
		}
		
		if(path.size >=3){
		path.removeIndex(0);
		}
				
	//}//END OF IF CONDITION
}
			
	private void getNearPositionPath(float targetY) {
		path.add(new Vector2(AppSettings.SCREEN_W/2, targetY));
	}
	
	private void getFarPositionPath(float targetY) {
		path.add(new Vector2(AppSettings.SCREEN_W-width, targetY ));
	}
	
	public boolean getNextToggle(){
		return toggleBoolean=!toggleBoolean;
	}

	private boolean isWayPointReached() {		
		return path.get(waypoint).x - getX() <= speed* Gdx.graphics.getDeltaTime() && path.get(waypoint).y -getY() <= speed* Gdx.graphics.getDeltaTime() ;		
	}

	public Array<Vector2>  getPath(){
		return this.path;
	}
	
    public void setPath( Array<Vector2>  path){
		this.path = path;
	}

	public int getWayPoint(){
		return waypoint;
	}


	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), width, height);
	}
	
	public int getState(){
		return this.state;
	}
	
	public void setState(int s){
		stateTime = 0;
		this.state = s;
	}
	
}
