package com.digitalwolf.drona.world;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.screens.GameScreen;
import com.digitalwolf.drona.sprites.BlackDragon;
import com.digitalwolf.drona.sprites.Conspirator;
import com.digitalwolf.drona.sprites.Drona;
import com.digitalwolf.drona.sprites.IntelligentBossEnemy;
import com.digitalwolf.drona.sprites.MagicalPower;
import com.digitalwolf.drona.sprites.Pawn;
import com.digitalwolf.drona.sprites.PowerUp;
import com.digitalwolf.drona.sprites.Weapon;
import com.digitalwolf.gamedata.GameOverInfo;
import com.digitalwolf.gamedata.Settings;
import com.moribitotech.mtx.settings.AppSettings;

public class World {

	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_BOSS_ENTRY = 1;
	public static final int WORLD_STATE_NEXT_LEVEL = 2;
	public static final int WORLD_STATE_GAME_OVER = 3;

	public static final float GRAVITY = -0.09f;

	public int state;
	public boolean showVictory;
	private int victoryTimer;
    
	// These variable are used to store the player achievements
	// These variables are not currently used in the game.. you can use it to show how nicely the player has performed
	// I felt lazy to code more for it
	public static int score;
	public static int numTotalPawns;
	public static int numPawnsKilled;
	public static int numTotalConspirators;
	public static int numConspiratorsKilled;
	public static int numTotalBlackDragons;
	public static int numBlackDragonsKilled;
	public static int numTotalPowerUps;
	public int numMagicalPowerAccquired;
	
	public static int creditsStar =0;
	
	// DEFINING ALL THE GAME ENTITIES/SPRITES
	public Drona drona;
	public ArrayList<Pawn> pawns;
	public ArrayList<BlackDragon> blackDragons;
	public ArrayList<Conspirator> conspirators;
	public ArrayList<PowerUp>powerUp;
	public ArrayList<MagicalPower>magicalPowers;
	public IntelligentBossEnemy bossEnemy;
	public Array<Vector2> path;
	
	public int distance;
	public boolean bossEnemyCreated;
	
	public World(int currentLevel) {
		//Initialize the Lists that contains the Game Entities
		drona = new Drona(20, 300);
		pawns = new ArrayList<Pawn>();
		powerUp = new ArrayList<PowerUp>();
		blackDragons = new ArrayList<BlackDragon>();
		conspirators = new ArrayList<Conspirator>();
		magicalPowers = new ArrayList<MagicalPower>();
		path = new Array<Vector2>();
		
		numTotalPawns =0;
		numPawnsKilled =0;
		numTotalConspirators =0;
		numConspiratorsKilled =0;
		numTotalBlackDragons = 0;
		numBlackDragonsKilled =0;
		numTotalPowerUps =0;
		//numMagicalPowerAccquired =0;
		
		bossEnemyCreated = false;
		showVictory = false;
		victoryTimer = 0;
		creditsStar =0;
	}

	private void createBossEnemy(){
		path.add(new Vector2(3*AppSettings.SCREEN_W/4, AppSettings.SCREEN_H-3*IntelligentBossEnemy.height));
		bossEnemy = new IntelligentBossEnemy(path, 3*AppSettings.SCREEN_W/4, AppSettings.SCREEN_H+IntelligentBossEnemy.height);
	}

	public void update(float deltaTime) {

		if (deltaTime == 0)
			return;
        
		if(state == WORLD_STATE_BOSS_ENTRY){
			if(!bossEnemyCreated){
			GameScreen.gameOverInfo = GameOverInfo.DRONA_KILLED_BY_BOSS_ENEMY;
			createBossEnemy();
			bossEnemyCreated = true;
			//state = WORLD_STATE_RUNNING;
			}
		}
		
		distance++;
		if(numMagicalPowerAccquired < 3){
			GameScreen.isSpecialPowerActive = false;
		}
		
		// My concept is .. the Power shot can be used only for some time..
		// It will continuosly be decremented ... collect more Magical Power to retain it..
		if(distance%Settings.MAX_LEVEL_LENGTH/5 ==0){
			if(numMagicalPowerAccquired > 0)
			numMagicalPowerAccquired--;
		}
		
		updateDrona(deltaTime);
		updateDronaWeapons(deltaTime);
		checkGameOver();
		
		if(pawns.size()>0){
		updatePawns(deltaTime);
		checkCollisionDronaWeaponvsPawns();
		checkCollisionDronavsPawns();
		}
		
		if(magicalPowers.size()>0){
			updateMagicalPowers(deltaTime);
			checkCollisionDronavsMagicalPowers();
			checkCollisionDronaWeaponvsMagicalPowers();
			}
		
		if(conspirators.size() > 0){
			updateConspirators(deltaTime);
			checkCollisionDronavsConspirators();
			checkCollisionDronaWeaponvsConspirators();
		}
		
		if(powerUp.size()>0){
		updatePowerUp(deltaTime);
		checkCollisionDronavsPowerUp();
		}
		
		if(blackDragons.size()>0){
			updateBlackdragons(deltaTime);
			checkCollisionDronaWeaponvsBlackDragon();
			checkCollisionDronavsBlackDragon();
		}
		
		
		if(bossEnemy!= null){
		updateBossEnemy(deltaTime);
		updateBossEnemyWeapons(deltaTime);
		checkCollisionBossEnemyWeaponvsDrona();
		checkCollisionDronaWeaponvsBossEnemy();
		
		if(showVictory){
			showVictory();
		}
		}
	}

	private void checkCollisionDronaWeaponvsMagicalPowers() {
		
		 ArrayList <Weapon> weapons = drona.getWeapons();
			
			for(int i=0;i<weapons.size();i++){
				Weapon w =  (Weapon)weapons.get(i);
				Rectangle weaponBounds = w.getBounds();
				
				for(int j=0;j<magicalPowers.size();j++){
					MagicalPower e =  (MagicalPower) magicalPowers.get(j);
					Rectangle powerBounds = e.getBounds();
					
		                if (powerBounds.overlaps(weaponBounds)) {
		                	if(w.state!=Weapon.LARGE)
			                w.visible=false;
		                	e.visible = false;
		                	Assets.playSound(Assets.dronaFall);
		                	break;
		                	}
		                }
			}
	}

	private void checkCollisionDronavsMagicalPowers() {
		
		for (int i = 0; i < magicalPowers.size(); i++) {
			MagicalPower e = magicalPowers.get(i);
			if (e.visible) {
				
				
				Rectangle powerBounds = e.getBounds();
				
				 if (powerBounds.overlaps(drona.getBounds())) {
					   e.visible = false;
	 				   numMagicalPowerAccquired++;
	 				  Assets.playSound(Assets.grab);
	 				   if(numMagicalPowerAccquired >=3){
	 					  GameScreen.isSpecialPowerActive = true;
	 				   }
	 				   break;
	 				}
			}
		}
	}

	private void updateMagicalPowers(float deltaTime) {
		
		for (int i = 0; i < magicalPowers.size(); i++) {
			MagicalPower p = magicalPowers.get(i);
			
			if (p.visible) {
				p.update(deltaTime);
			} else {
				magicalPowers.remove(p);
			}
		}
	}

	private void checkCollisionDronaWeaponvsConspirators() {
		
		 ArrayList <Weapon> weapons = drona.getWeapons();
			
			for(int i=0;i<weapons.size();i++){
				Weapon w =  (Weapon)weapons.get(i);
				Rectangle weaponBounds = w.getBounds();
				
				for(int j=0;j<conspirators.size();j++){
					Conspirator e =  (Conspirator) conspirators.get(j);
					Rectangle enemyBounds = e.getBounds();
					
		                if (enemyBounds.overlaps(weaponBounds)) {
		                	if(w.state!=Weapon.LARGE)
			                	w.visible=false;
		                	
		                	numConspiratorsKilled++;
		                	score+=300;
		                	Assets.playSound(Assets.killed1);
		                	e.state = Conspirator.DEAD;
		                	break;
		                	}
		                }
			}
	}

	private void updateConspirators(float deltaTime) {
		
			for(int i=0;i<conspirators.size();i++){
				Conspirator e = conspirators.get(i);
				if(e.visible){
					e.update(deltaTime);               	
				}
				else{
					conspirators.remove(e);
				}
			}
	}

	private void checkCollisionDronavsBlackDragon() {
		
		for (int i = 0; i < blackDragons.size(); i++) {
			BlackDragon e = blackDragons.get(i);
			if (e.visible) {
				
				
				Rectangle demonBounds = e.getBounds();
				
				 if (demonBounds.overlaps(drona.getBounds())) {
					   e.state = BlackDragon.KILLED;
	 				   drona.health-=200f;
	 				   GameScreen.gameOverInfo = GameOverInfo.DRONA_KILLED_BY_PAWNS;
	 				   break;
	 				}
			}
		}
	}

	private void checkCollisionDronaWeaponvsBlackDragon() {
		
		 ArrayList <Weapon> weapons = drona.getWeapons();
			
			for(int i=0;i<weapons.size();i++){
				Weapon w =  (Weapon)weapons.get(i);
				Rectangle weaponBounds = w.getBounds();
				
				for(int j=0;j<blackDragons.size();j++){
					BlackDragon e =  (BlackDragon) blackDragons.get(j);
					Rectangle enemyBounds = e.getBounds();
					
		                if (enemyBounds.overlaps(weaponBounds)) {
		                	if(w.state!=Weapon.LARGE)
			                	w.visible=false;
		                	Assets.playSound(Assets.killed1);
		                	numBlackDragonsKilled++;
		                	score+=300;
		                	e.life--;
		                	e.state = BlackDragon.KILLED;		        			
		                	e.visible = false;
		                	break;
		                	}
		                }
			}
	}

	
	private void updateBlackdragons(float deltaTime) {
		
		for (int i = 0; i < blackDragons.size(); i++) {
			BlackDragon p = blackDragons.get(i);
			
			if (p.visible) {
				p.update(deltaTime);
			} else {
				blackDragons.remove(p);
			}
		}
	}

	private void checkCollisionDronaWeaponvsBossEnemy() {
		
		 ArrayList <Weapon> weapons = drona.getWeapons();
			
			for(int i=0;i<weapons.size();i++){
				Weapon w =  (Weapon)weapons.get(i);
				Rectangle weaponBounds = w.getBounds();
				
		                if (bossEnemy.getBounds().overlaps(weaponBounds)) {
			                	w.visible=false;
		                	
		                	if(bossEnemy.life > 0){
		                	score+=200;
		                	Assets.playSound(Assets.killed);
		                	bossEnemy.life--;
		                	bossEnemy.setState(IntelligentBossEnemy.HIT);
		                	break;
		                	}
		                	
		                	if(bossEnemy.life ==0){
			                	bossEnemy.setState(IntelligentBossEnemy.DYING);
			                	bossEnemy.visible = false;
			            		showVictory = true;
			                	break;
			                	}		                				    
		 				}
			}
	}

	private void showVictory() {
		// Wait for a while after the player kills the Boss Enemy.. it will appear good
		victoryTimer++;
		if(victoryTimer>=150){
			victoryTimer = 0;
			showVictory = false;
			bossEnemy = null;
			bossEnemyCreated = false;
			calculateCreditsStar();
    		state = WORLD_STATE_NEXT_LEVEL;
		}
		
	}

	private void calculateCreditsStar() {
		// TODO Auto-generated method stub
		float totalDestruction = (numPawnsKilled+numBlackDragonsKilled+numConspiratorsKilled)*5;
		float enemiesCount = Math.max(1,(numTotalPawns+numTotalBlackDragons+numTotalConspirators));
		float result = (totalDestruction/enemiesCount)+1;
		creditsStar = (int)result;
		Settings.setLastCreditStar(creditsStar);
	}

	private void checkCollisionBossEnemyWeaponvsDrona() {
		
		 ArrayList <Weapon> weapons = bossEnemy.getWeapons();
			
			for(int i=0;i<bossEnemy.getWeapons().size();i++){
				Weapon w =  (Weapon)weapons.get(i);
				Rectangle weaponBounds = w.getBounds();
				
		                if (drona.getBounds().overlaps(weaponBounds)) {
		                	w.visible = false;
		                	drona.health -=100;
		                	break;
		                	} 				    
			}
	}

	private void updateBossEnemyWeapons(float deltaTime) {
		
		ArrayList<Weapon> weapons = bossEnemy.getWeapons();

		for (int i = 0; i < weapons.size(); i++) {
			Weapon w = weapons.get(i);
			if (w.visible) {
				w.updateForBossEnemy(deltaTime);
			} else {
				weapons.remove(w);
			}
		}
	}

	private void updateBossEnemy(float deltaTime) {
		// Call the update method of Intelligent BossEnemy
		bossEnemy.update(deltaTime, drona.position.x,drona.position.y);		
	}

	private void checkCollisionDronavsPawns() {
		
		for (int i = 0; i < pawns.size(); i++) {
			Pawn p = pawns.get(i);
			if (p.visible) {				
				Rectangle pawnBounds = p.getBounds();			
				 if (pawnBounds.overlaps(drona.getBounds())) {
	                	p.state = Pawn.DESTROYING;
	 				    p.visible = false;
	 				    drona.health-=100f;
	 				   Assets.playSound(Assets.dronaFall);
	 				   GameScreen.gameOverInfo = GameOverInfo.DRONA_KILLED_BY_PAWNS;
	 				  break;
	 				}
			}
		}
	}
	
	private void checkCollisionDronavsConspirators() {
        
		for (int i = 0; i < conspirators.size(); i++) {
			Conspirator p = conspirators.get(i);
			if (p.visible) {				
				Rectangle enemyBounds = p.getBoundingRectangle();
				 if (enemyBounds.overlaps(drona.getBounds())) {
	                	p.state = Conspirator.DEAD;
	 				    drona.health-=100f;
	 				   Assets.playSound(Assets.dronaFall);
	 				   GameScreen.gameOverInfo = GameOverInfo.DRONA_KILLED_BY_CONSPIRATORS;
	 				  break;
	 				}
			}
		}
	}

	private void updatePowerUp(float deltaTime) {
		for (int i = 0; i < powerUp.size(); i++) {
			PowerUp p = powerUp.get(i);
			
			if (p.visible) {
				p.update(deltaTime);
			} else {
				powerUp.remove(p);
			}
		}
	}	

	private void checkCollisionDronaWeaponvsPawns() {
        
		 ArrayList <Weapon> weapons = drona.getWeapons();
			
			for(int i=0;i<weapons.size();i++){
				Weapon w =  (Weapon)weapons.get(i);
				Rectangle weaponBounds = w.getBounds();
				
				 for (int j = 0; j< pawns.size(); j++) {
		                Pawn pawn = (Pawn) pawns.get(j);
		                Rectangle pawnRect = pawn.getBounds();

		                if (pawnRect.overlaps(weaponBounds)) {
		                	if(w.state!=Weapon.LARGE)
		                	w.visible=false;
		                	
		                	pawn.state = Pawn.DESTROYING;
		                	pawn.visible = false;
		                	numPawnsKilled++;
		 				    score+=200;
		 				    break;
		 				}
	     }
			}
	}
	
	private void checkCollisionDronavsPowerUp() {
       
			for(int i=0;i<powerUp.size();i++){
				PowerUp w =  (PowerUp)powerUp.get(i);
				Rectangle powerUpBounds = w.getBounds();

		                if (drona.getBounds().overlaps(powerUpBounds)) {
		                	w.visible=false;
		                	if(drona.health <=500)
		                	drona.health+=100;
		                	w.state = PowerUp.AQUIRED;
		                	w.position.x = drona.position.x;
		                	w.position.y = drona.position.y;
		 				    score+=500;	
		 				    drona.isPoweredUp = true;
		 				   Assets.playSound(Assets.grab);
		 				  break;
		 				}
	     }
	}
	
	private void updatePawns(float deltaTime) {
		
		for (int i = 0; i < pawns.size(); i++) {
			Pawn p = pawns.get(i);
			System.out.println(pawns.size());
			if (p.visible) {
				p.update(deltaTime);
			} else {
				pawns.remove(p);
			}
		}
	}


	private void updateDronaWeapons(float deltaTime) {

		ArrayList<Weapon> weapons = drona.getWeapons();

		for (int i = 0; i < weapons.size(); i++) {
			Weapon w = weapons.get(i);
			if (w.visible) {
				w.update(deltaTime);
			} else {
				weapons.remove(w);
			}
		}

	}

	private void updateDrona(float deltaTime) {
		
		if (deltaTime == 0)
			return;

		// Updates the Player helping not to cross the map Boundaries
		drona.update(deltaTime);
		// Apply gravity if Drona is falling
		
		drona.velocity.add(0, GRAVITY);
		
		drona.position.add(drona.velocity);
	}

	private void checkGameOver() {
		if (drona.position.y <= -2*Drona.height) {
			GameScreen.gameOverInfo = GameOverInfo.DRONA_FELL_DOWN;
			state = WORLD_STATE_GAME_OVER;
			calculateCreditsStar();
		}
		
		if(drona.health <=0){
			state = WORLD_STATE_GAME_OVER;
			calculateCreditsStar();
		}
		
		if(distance>= Settings.MAX_LEVEL_LENGTH){
			if(state!= WORLD_STATE_GAME_OVER){
			state = WORLD_STATE_BOSS_ENTRY;			
			}
		}
	}
}
