package com.digitalwolf.gamedata;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.digitalwolf.drona.sprites.BlackDragon;
import com.digitalwolf.drona.sprites.Conspirator;
import com.digitalwolf.drona.sprites.MagicalPower;
import com.digitalwolf.drona.sprites.Pawn;
import com.digitalwolf.drona.sprites.PowerUp;
import com.digitalwolf.drona.world.World;
import com.moribitotech.mtx.settings.AppSettings;

public class LevelGenerator {

	
	private final Random rand = new Random();
	private World world;
	private Array<Vector2> path;
	
	public LevelGenerator(World theWorld){
		world = theWorld;
		path = new Array<Vector2>();
	}
	
	public void generateLevel(int levelID){
		
		// Generate the Creatures and position them		
		float x = 3*AppSettings.SCREEN_W/2;
		float y = Pawn.height +  rand.nextFloat() *(AppSettings.SCREEN_H - 2*Pawn.height);
		
		//Generate Pawns	      
	    if (rand.nextFloat() >= 0.5f) {  
	    	float value = rand.nextFloat();
	    	int type =0;
	    	if(value <=0.1f){
	    		type = Pawn.RED;
	    	}
	    	if(value>= 0.2f && value <=0.6f){
	    		type = Pawn.ORANGE;
	    	}
	    	if(value>= 0.7f && value <=1.0f){
	    		type = Pawn.BLUE;
	    	}
	        Pawn pawn = new Pawn(x,y,type);
	        World.numTotalPawns++;
	        world.pawns.add(pawn);
		}
		
		//Generate 1 UP/ Health
	    if (rand.nextFloat() >= 0.9f && (world.powerUp.size() < 1)) {  
		   
        float tempX = AppSettings.SCREEN_W + PowerUp.width;			
		float tempY = (AppSettings.SCREEN_H/6) +  rand.nextFloat() *(2*AppSettings.SCREEN_H/3);
			
	    PowerUp powerup = new PowerUp(tempX, tempY);
        world.powerUp.add(powerup);
        World.numTotalPowerUps++;
        }
		
	  //Generate Magical Power
	    if (rand.nextFloat() > 0.9f && (world.magicalPowers.size() <1)) {  
		   
        float tempX = AppSettings.SCREEN_W + MagicalPower.width;			
		float tempY = (AppSettings.SCREEN_H/6) +  rand.nextFloat() *(2*AppSettings.SCREEN_H/3);
			
	    MagicalPower magicalPower = new MagicalPower(tempX, tempY);
        world.magicalPowers.add(magicalPower);
        }
	    
		
	  // Generate EVIL DEMON 	      
      if (rand.nextFloat() >= 0.8f && (world.blackDragons.size() <1)) {  
    	y = (AppSettings.SCREEN_H/5) +  rand.nextFloat() *(AppSettings.SCREEN_H/2);
    	
    	if(isSpaceEmpty(x, y, BlackDragon.width, BlackDragon.height)){
        BlackDragon ed = new BlackDragon(x,y);
        World.numTotalBlackDragons++;
        world.blackDragons.add(ed);
    	}
	  }
	}
	
	public void generateConspirators(float targetY){
		// Generate Conspirators after the first Level is Completed     
	      if (rand.nextFloat() >= 0.9f && (world.conspirators.size() <2)) {  
	 
	      float tempX = AppSettings.SCREEN_W + Conspirator.width;	
	  	  float y = Conspirator.height+rand.nextFloat() *(AppSettings.SCREEN_H - 2*Conspirator.height);
	  	  
	  	  path.add(new Vector2(-2*Conspirator.width, targetY));
	  	 if(isSpaceEmpty(tempX, y, Conspirator.width, Conspirator.height)){
	      Conspirator c1 = new Conspirator(path, tempX ,y);
	      Conspirator c2 = new Conspirator(path, tempX+1.5f*Conspirator.width ,y);
	      World.numTotalConspirators++;
	      world.conspirators.add(c1);
	      world.conspirators.add(c2);
	  	  }
		}
	}
	
	private boolean isSpaceEmpty(float x, float y, float width, float height) {
		// Would you like two enemies appearing together..?  so check whether the space the empty and then generate enemies
			boolean isSpaceEmpty = true;
			Rectangle thisBound =  new Rectangle(x ,y ,width, height);
			
			if(world.pawns.size() >0){
			for(int i=0;i<world.pawns.size();i++){
				Pawn p =  (Pawn)world.pawns.get(i);
				Rectangle pawnBounds =  new Rectangle(p.position.x, p.position.y,Pawn.width, Pawn.height);

		                if(pawnBounds.overlaps(thisBound)){
		                	isSpaceEmpty = false;
		    			}	
	        }
			}
			
			if(world.blackDragons.size() >0){
			for(int i=0;i<world.blackDragons.size();i++){
				BlackDragon d =  (BlackDragon)world.blackDragons.get(i);
				Rectangle dragonBounds =  new Rectangle(d.position.x,d.position.y,BlackDragon.width, BlackDragon.height);

		                if(dragonBounds.overlaps(thisBound)){
		                	isSpaceEmpty = false;
		    			}	
	        }	
			}
			
			if(world.conspirators.size() >0){
				for(int i=0;i<world.conspirators.size();i++){
					Conspirator c =  (Conspirator)world.conspirators.get(i);
					Rectangle conspiratorsBounds =  new Rectangle(c.getX(),c.getY(),Conspirator.width, Conspirator.height);

			                if(conspiratorsBounds.overlaps(thisBound)){
			                	isSpaceEmpty = false;
			    			}	
		        }	
				}
			
			if(world.magicalPowers.size() >0){
				for(int i=0;i<world.magicalPowers.size();i++){
					MagicalPower c =  (MagicalPower)world.magicalPowers.get(i);
					Rectangle bounds =  new Rectangle(c.position.x,c.position.y,MagicalPower.width, MagicalPower.height);

			                if(bounds.overlaps(thisBound)){
			                	isSpaceEmpty = false;
			    			}	
		        }	
				}
			return isSpaceEmpty;
	}
		
}
