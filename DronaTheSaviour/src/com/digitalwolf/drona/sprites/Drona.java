package com.digitalwolf.drona.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.digitalwolf.drona.assets.Assets;
import com.moribitotech.mtx.settings.AppSettings;

public class Drona extends GameEntity{

	public float MAX_VELOCITY = 2.5f;
	public static float DAMPING = 0.5f;
	
	public static final int FLYING = 0;
	public static final int ATTACK = 1;
	
	public boolean visible;
    public static final float width = 2048/8*0.5f*AppSettings.getWorldSizeRatio();
    public static final float height = 275*0.5f*AppSettings.getWorldSizeRatio();
    public boolean isPoweredUp;
    public int state;
	public float stateTime;
	
	public int numSpecialPower;
	public boolean hasSpecialPower;
	public float health;
	
	public static int currentWeapon;
	
	private ArrayList<Weapon> weapons;
	
	public Drona(float x, float y) {
		super(x, y);
		
		state = FLYING;
		stateTime = 0;
		weapons = new ArrayList<Weapon>();
	    visible = true;
	    isPoweredUp = false;
	    health = 500;
	    
	    hasSpecialPower = false;
	    numSpecialPower = 0;
	    currentWeapon = Weapon.NORMAL;
	}

	public static void setWeapon(int type){
		currentWeapon = type;
	}
	
	private int getCurrentWeapon(){
		return currentWeapon;
	}
	
	public void update(float deltaTime) { 
		super.update(deltaTime);
		stateTime+= deltaTime;
    }
	
	public Rectangle getBounds(){
    	return new Rectangle(position.x, position.y, width, height);
    }
	
	public void fire(){
		Assets.playSound(Assets.shoot);
		Weapon w = new Weapon(position.x+2*width/3,position.y+height/3.3f, getCurrentWeapon());
		weapons.add(w);
	}
	
	public ArrayList<Weapon> getWeapons(){
		return this.weapons;
	}
}
