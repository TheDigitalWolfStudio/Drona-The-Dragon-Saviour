package com.digitalwolf.drona.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.digitalwolf.drona.assets.Assets;
import com.moribitotech.mtx.settings.AppSettings;

public class Conspirator extends AISprite{

	public static final int ALIVE = 0;
    public static final int DEAD = 1;

    public static final float width = 125*0.5f*AppSettings.getWorldSizeRatio();
    public static final float height = 141*0.5f*AppSettings.getWorldSizeRatio();
    
	public int state;
	public float stateTime;
	public boolean visible;
	private float tolerance =3;
	
	public Conspirator(Array<Vector2> path, float x, float y) {
		super(path, x, y);
		state = ALIVE;
		stateTime = 0;
		visible =true;
	}

	@Override
	public void draw(SpriteBatch batch){
		update(Gdx.graphics.getDeltaTime());
		
		TextureRegion frame = Assets.conspiratorAnim.getKeyFrame(getStateTime());
		
		batch.begin();
		batch.draw(frame, getX(), getY(), width, height);
		batch.end();
	}
	
	public void update(float deltaTime) {
		// Update the Conspirators
		
		stateTime += deltaTime;
		float angle =(float)Math.atan2(path.get(waypoint).y -getY(), path.get(waypoint).x -getX());
		velocity.set((float) Math.cos(angle)*speed, (float)Math.sin(angle)*speed);
		setPosition(getX()+ velocity.x * deltaTime, getY() + velocity.y * deltaTime);
		setRotation(angle * MathUtils.degRad);
		
		
		if(state == DEAD){
		visible = false;
	    }
		
		if(getX() <= -width){
			visible = false;
		}
		
		if(isWayPointReached()){
		    setPosition(path.get(waypoint).x, path.get(waypoint).y);
			if(waypoint+1 >= path.size){
			}
			else{
			waypoint+=1;
			setPosition(getX()+ 5 * deltaTime, getY());
			}
		}
    }

	public boolean isWayPointReached() {
		return path.get(waypoint).x - getX() <= speed/tolerance* Gdx.graphics.getDeltaTime() && path.get(waypoint).y -getY() <= speed/tolerance* Gdx.graphics.getDeltaTime() ;
		
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
	
	public Rectangle getBounds(){
    	return new Rectangle(getX(), getY(), width, height);
    }
}
