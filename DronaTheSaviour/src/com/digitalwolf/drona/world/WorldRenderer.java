package com.digitalwolf.drona.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.sprites.BlackDragon;
import com.digitalwolf.drona.sprites.Conspirator;
import com.digitalwolf.drona.sprites.Drona;
import com.digitalwolf.drona.sprites.IntelligentBossEnemy;
import com.digitalwolf.drona.sprites.MagicalPower;
import com.digitalwolf.drona.sprites.Pawn;
import com.digitalwolf.drona.sprites.PowerUp;
import com.digitalwolf.drona.sprites.Weapon;

/*
 * This class handles all the drawing.. the methods are called from the GameSreen.java
 */
public class WorldRenderer  {

	public OrthogonalTiledMapRenderer renderer;
	public SpriteBatch batch;
	private World world;
	
	public ParticleEffect pawnsDeadEffect;
	public ParticleEffect enemyDeadEffect;
	public ParticleEffect conspiratorKilledEffect;
	public ParticleEffect dronaPowerUp;
	public ParticleEffect explosionEffect;
		
	public WorldRenderer(World world, SpriteBatch batch) {
        this.world = world;
        dronaPowerUp = new ParticleEffect();
        pawnsDeadEffect = new ParticleEffect();
        explosionEffect = new ParticleEffect();
        enemyDeadEffect = new ParticleEffect();
        conspiratorKilledEffect = new ParticleEffect();
        
        dronaPowerUp.load(Gdx.files.internal("effect/largeExplosion1.p"), Gdx.files.internal("effect"));
        pawnsDeadEffect.load(Gdx.files.internal("effect/fireballblast.p"), Gdx.files.internal("effect"));
        enemyDeadEffect.load(Gdx.files.internal("effect/fireballblast.p"), Gdx.files.internal("effect"));
        explosionEffect.load(Gdx.files.internal("effect/largeExplosion1.p"), Gdx.files.internal("effect"));
        conspiratorKilledEffect.load(Gdx.files.internal("effect/fireballblast.p"), Gdx.files.internal("effect"));

        this.batch = batch;
    }
	
	public void effectListRenderer(float deltaTime){
		
		batch.begin();		
			
		pawnsDeadEffect.draw(batch, deltaTime);
		enemyDeadEffect.draw(batch, deltaTime);
		conspiratorKilledEffect.draw(batch, deltaTime);
		
		if(world.showVictory)	
		explosionEffect.draw(batch, deltaTime);
		batch.end();
		
	}

	
	public void renderDronaWeapons(float deltaTime)
	{		
		for(int i=0; i< world.drona.getWeapons().size();i++){
			Weapon w = world.drona.getWeapons().get(i);
			
			switch(w.state)
			{
			case 0:
				w.dronaWeaponNormal(batch, deltaTime, w.position.x, w.position.y);
				break;
			
			case 1:
				w.dronaWeaponMedium(batch, deltaTime, w.position.x, w.position.y);
				break;
			
			case 2:
				w.dronaWeaponLarge(batch, deltaTime, w.position.x, w.position.y);
				break;
			
			}							
	}
}
	
	public void renderPawns(float deltaTime)
	{
		TextureRegion frame = null;
		batch.begin();
		for(int i=0; i< world.pawns.size();i++){
			Pawn p = world.pawns.get(i);
			
			switch(p.state)
			{
			case 0:
				if(p.type == Pawn.BLUE)
				frame = Assets.pawn1Flying.getKeyFrame(p.stateTime);
				if(p.type == Pawn.ORANGE)
				frame = Assets.pawn2Flying.getKeyFrame(p.stateTime);
				if(p.type == Pawn.RED)
				frame = Assets.dragonAnimation.getKeyFrame(p.stateTime);
				break;
			
			case 1:
				
				pawnsDeadEffect.start();
				pawnsDeadEffect.setPosition(p.position.x, p.position.y);
				break;
			}
			
			if(p.visible){
			if(p.type!=Pawn.RED)
			batch.draw(frame, p.position.x, p.position.y, Pawn.width, Pawn.height);
			else
			batch.draw(frame, p.position.x, p.position.y, -Pawn.width, Pawn.height);
			}			
		}		
		batch.end();
	}
	
	public void renderDrona(float deltaTime)
	{		
		TextureRegion poof = Assets.poofAnimation.getKeyFrame(world.drona.stateTime);
		// Based on the Player state, get the animation frame
		if(world.drona.visible){
		TextureRegion frame = null;
		switch (world.drona.state)
		{
		case 0:
			frame = Assets.dronaFlying.getKeyFrame(world.drona.stateTime);
			break;

		case 1:
			frame = Assets.dronaFlying.getKeyFrame(world.drona.stateTime);
			break;			
		}
		
		batch.begin();
		
		if(world.drona.isPoweredUp){
			world.drona.isPoweredUp=false;
		
			dronaPowerUp.setPosition(world.powerUp.get(0).position.x + PowerUp.width/2, world.powerUp.get(0).position.y + PowerUp.height/2);
			dronaPowerUp.start();
			dronaPowerUp.draw(batch, deltaTime);
	    }
			batch.draw(frame, world.drona.position.x, world.drona.position.y, Drona.width, Drona.height);
			if(world.drona.position.y <= 0)
				batch.draw(poof, world.drona.position.x+Drona.width/4, world.drona.position.y+Drona.height/3, 50, 50);
		batch.end();
	   }
}

	public void renderPowerUp(float deltaTime) {
		TextureRegion frame = null;
		batch.begin();
		for(int i=0; i< world.powerUp.size();i++){
			PowerUp p = world.powerUp.get(i);
			
			switch(p.state)
			{
			case 0:
				frame = Assets.powerUpAnimation.getKeyFrame(world.drona.stateTime);
				break;
			
			case 1:
				frame = Assets.powerUpAnimation.getKeyFrame(world.drona.stateTime);
				break;
			}
			
			if(p.visible){
			batch.draw(frame, p.position.x, p.position.y, PowerUp.width, PowerUp.height);
			}			
		}		
		batch.end();
		
	}
	
	public void renderBossEnemy(float deltaTime) {

		if(world.bossEnemy.visible && world.bossEnemyCreated){
		world.bossEnemy.draw(batch);
		}
		
		if(world.bossEnemy.getState()==IntelligentBossEnemy.DYING && world.showVictory){
			explosionEffect.setPosition(world.bossEnemy.getX(),world.bossEnemy.getY());
			explosionEffect.start();
				
			}
	}

	
	public void renderConspirators(float deltaTime) {
		
		for(int i=0; i< world.conspirators.size();i++){
			Conspirator p = world.conspirators.get(i);
			
			if(p.visible){
			p.draw(batch);
			}
			
			if(p.state == Conspirator.DEAD){
				conspiratorKilledEffect.start();
				conspiratorKilledEffect.setPosition(p.getX(), p.getY());					
				}			
		}
	}
	
	public void renderBlackDragons(float delta) {
		// TODO Auto-generated method stub
		TextureRegion frame = null;
		
		for(int i=0; i< world.blackDragons.size();i++){
			BlackDragon p = world.blackDragons.get(i);
			
			switch(p.state)
			{
			case 0:
				frame = Assets.blackDragonAnim.getKeyFrame(world.drona.stateTime);
				break;
				
			case 1:
				enemyDeadEffect.start();
				enemyDeadEffect.setPosition(p.position.x, p.position.y);
				break;
			}
						
			if(p.visible && p.state==BlackDragon.NORMAL){
			batch.begin();
			batch.draw(frame, p.position.x, p.position.y, -BlackDragon.width, BlackDragon.height);		
			batch.end();
			}
			
		}
		
	}
	
	public void renderMagicalPowers(float delta) {
		
		batch.begin();
		for(int i=0; i< world.magicalPowers.size();i++){
			MagicalPower p = world.magicalPowers.get(i);
		    if(p.visible)
			batch.draw(Assets.splashAnim.getKeyFrame(world.drona.stateTime), p.position.x, p.position.y, MagicalPower.width, MagicalPower.height);
	  }
		batch.end();
     }

	public void dispose() {
		// Dispose all the Effects
		 pawnsDeadEffect.dispose();
	     enemyDeadEffect.dispose();
	     conspiratorKilledEffect.dispose();
	     dronaPowerUp.dispose();
	     explosionEffect.dispose();
	}
}
