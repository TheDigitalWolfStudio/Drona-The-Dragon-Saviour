package com.digitalwolf.drona.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moribitotech.mtx.settings.AppSettings;

/*
 * This class makes use of the Particle Effect .. read it carefully 
 */
public class Weapon extends GameEntity{

	public float MAX_VELOCITY = 1.5f;
	
	public static final int NORMAL = 0;
	public static final int MEDIUM = 1;
	public static final int LARGE = 2;
	
	public static final int EXPLODE = 3;
	
	public boolean visible;
    public float width = 0;
    public float height = 0;
    
    public int state;
	public float stateTime;
	boolean reloaded = false;   // Start with a reload because weapons not reload itself
	float reloadTime = 100;     // This is the "timer" (The value doesn't matter)
	final float startReloadTime = 100;
	
	public ParticleEffect dronaNormalShot;
	public ParticleEffect dronaMediumShot;
	public ParticleEffect dronaLargeShot;
	public static ParticleEffect effect;
	
	public Weapon(float x, float y, int state) {
		super(x, y);
		
		this.state = state;
		
		//SET UP DIMENSION DEPENDING UPON THE STATE
		if(state == NORMAL){
			width = 30;
			height = 30;
		}
		if(state == MEDIUM){
			width = 50;
			height = 50;
		}
		if(state == LARGE){
			width = 80;
			height = 80;
		}
		
		stateTime = 0;

	    visible = true;
	     
	    dronaNormalShot = new ParticleEffect();
	    dronaMediumShot = new ParticleEffect();
	    dronaLargeShot = new ParticleEffect();
	    effect = new ParticleEffect();
	    
        dronaNormalShot.load(Gdx.files.internal("effect/final effect/dronaWeponeNormal.p"), Gdx.files.internal("effect"));
        dronaMediumShot.load(Gdx.files.internal("effect/final effect/dronaWeponeMedium.p"), Gdx.files.internal("effect"));
        dronaLargeShot.load(Gdx.files.internal("effect/final effect/dronaWeponeLarge.p"), Gdx.files.internal("effect"));
        effect.load(Gdx.files.internal("effect/fireballblast.p"), Gdx.files.internal("effect"));
	}

	public void update(float deltaTime) { 
		super.update(deltaTime);
		stateTime+= deltaTime;
		velocity.x += MAX_VELOCITY;		
		position.add(velocity);
		
		if(position.x >= AppSettings.SCREEN_W + width){
			visible = false;
		}
		
    }
	
	public Rectangle getBounds(){
    	return new Rectangle(position.x, position.y, width, height);
    }

	public void updateForBossEnemy(float deltaTime) {
		
		super.update(deltaTime);
		stateTime+= deltaTime;
		velocity.x -= MAX_VELOCITY*stateTime;		
		position.add(velocity);
		
		if(position.x <= -width){
			visible = false;
		}
	}
	public void dronaWeaponNormal(SpriteBatch batch,float deltaTime,float x, float y){
		batch.begin();
		dronaNormalShot.setPosition(x, y);
		dronaNormalShot.start();
		dronaNormalShot.draw(batch, deltaTime);	
		batch.end();
	}
	
	public void dronaWeaponMedium(SpriteBatch batch,float deltaTime,float x, float y){
		batch.begin();
		dronaMediumShot.setPosition(x, y);
		dronaMediumShot.start();
		dronaMediumShot.draw(batch, deltaTime);
		batch.end();	
	}
	public void dronaWeaponLarge(SpriteBatch batch,float deltaTime,float x, float y){
		batch.begin();
		dronaLargeShot.setPosition(x, y);
		dronaLargeShot.start();
		dronaLargeShot.draw(batch, deltaTime);
		batch.end();	
	}
	
	public static void createParticleEffect(SpriteBatch batch,float deltaTime,float x, float y){
		batch.begin();
		effect.setPosition(x, y);
		effect.start();
		effect.draw(batch, deltaTime);
		batch.end();	
	}

}
