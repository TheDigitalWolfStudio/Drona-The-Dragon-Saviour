package com.digitalwolf.drona.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class AISprite extends Sprite{

	public Vector2 velocity = new Vector2();
	public int waypoint =0;
	public float speed = 150;
	public Array<Vector2> path;
	public float stateTime;
	
	//VARIABLES FOR TRACKING THE LIFE TIME OF THE EGGS
		private float startTime = System.nanoTime();
		public static float SECONDS_TIME = 0;
		public static float LIFE_TIME = 0;
	
	public AISprite(Array<Vector2> path, float x, float y){
		super();
		setX(x);
		setY(y);
		this.path = path;
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
	@Override
	public void draw(SpriteBatch batch){
		super.draw(batch);
	}

	public float getLifeTime() {
		return LIFE_TIME;
	}
	
	public void setLifeTime(float secondsTime) {
		LIFE_TIME = secondsTime;
	}

	public void update(float deltaTime) {	
		stateTime += deltaTime;
		
		if (System.nanoTime() - startTime >= 1000000000) {
			SECONDS_TIME++;
			LIFE_TIME++;
			startTime = System.nanoTime();
		}
		// Responsible for setting appropriate angle and adding velocity to the position
		float angle =(float)Math.atan2(path.get(waypoint).y -getY(), path.get(waypoint).x -getX());
		velocity.set((float) Math.cos(angle)*speed, (float)Math.sin(angle)*speed);
		setPosition(getX()+ velocity.x * deltaTime, getY() + velocity.y * deltaTime);
		setRotation(angle * MathUtils.degRad);
	}
	
	public void update(float deltaTime, float x, float y) {	
		update(deltaTime);
		
	}
	
}
