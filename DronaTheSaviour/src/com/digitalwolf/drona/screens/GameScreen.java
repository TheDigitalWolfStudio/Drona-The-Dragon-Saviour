package com.digitalwolf.drona.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.sprites.Drona;
import com.digitalwolf.drona.world.World;
import com.digitalwolf.drona.world.WorldRenderer;
import com.digitalwolf.gamedata.LevelGenerator;
import com.digitalwolf.gamedata.Settings;
import com.digitalwolf.parallaxbackground.BackgroundGenerator;
import com.digitalwolf.parallaxbackground.ParallaxBackground;
import com.digitalwolf.screenhelpers.DronaWeaponSelectScreenHelper;
import com.digitalwolf.screenhelpers.GameAchievementMenuScreenHeper;
import com.digitalwolf.screenhelpers.GameLevelEndScreenHelper;
import com.digitalwolf.screenhelpers.GameOverScreenHelper;
import com.digitalwolf.screenhelpers.GamePauseScreenHelper;
import com.digitalwolf.screenhelpers.GameReadyScreenHelper;
import com.moribitotech.mtx.AbstractScreen;
import com.moribitotech.mtx.settings.AppSettings;

public class GameScreen extends AbstractScreen{
	
	//DEFINING ALL THE SCREEN HELPERS
	public GameAchievementMenuScreenHeper gameAchievementMenuScreenHeper;
	public GameLevelEndScreenHelper gameLevelEndScreenHelper;
	public GameOverScreenHelper gameOverScreenHelper;
	public GamePauseScreenHelper gamePauseScreenHelper;
	public GameReadyScreenHelper gameReadyScreenHelper;
	public DronaWeaponSelectScreenHelper dronaWeaponSelectScreenHelper;
	
	//DEFINING ALL THE SCREEN HELPER STATES
    public boolean isReadyMenuDisplaying;
    public boolean isPauseMenuDisplaying;
    public boolean isLevelEndMenuDisplaying;
    public boolean isAchievementMenuDisplaying;
    public boolean isGameOverMenuDisplaying;
       
	//DEFINING THE VARIOUS STATES OF THE GAME
	
	public static final int GAME_READY = 0;    
	public static final int GAME_RUNNING = 1;
	public static final int GAME_PAUSED = 2;
	public static final int GAME_LEVEL_END = 3;
	public static final int GAME_OVER = 4;
	public static int state;
	
	public static int currentlevel;
	public boolean isGameSaved;
	public SpriteBatch batch ;
	public World world;
	public WorldRenderer renderer;
	private ParallaxBackground background;
	private LevelGenerator levelGenerator;
	private float buttonSize = 100*AppSettings.getWorldSizeRatio();
	//VARIABLES TO CONTROL SHOOTING OF THE PLAYER
	boolean reloaded = false;   // Start with a reload because weapons don't reload itself
	float reloadTime = 100;     // This is the "timer" (The value doesn't matter)
	final float startReloadTime = 100;
	
	//VARIABLE FOR SPECIAL POWER
	public static boolean isSpecialPowerActive;
	
	//VARIABLES FOR THE RANDOM LEVEL GENERATION
	boolean generateTimerReady = false;
	float  generationTime = 100;	
	
	public int lastScore;
	public static String scoreString;
	public static int gameOverInfo;
	public boolean playerContinuesGame;
			
	public GameScreen(Game game, String screenName) {
		super(game, screenName);
		
		state = GAME_READY;
		setUpScreenElements();
		setUpMenu();
		
		batch = getStage().getSpriteBatch();
		world = new World(currentlevel);
		renderer = new WorldRenderer(world,batch);
		
		levelGenerator = new LevelGenerator(world);
		currentlevel =1;
		background = BackgroundGenerator.getBackground();
		lastScore =0;
		gameOverInfo =-1;
		isGameSaved = false;
		isSpecialPowerActive = false;
	}
	
    public void sendAwayAllScreenHelpers(){
    	
    	if(isAchievementMenuDisplaying){
	    	gameAchievementMenuScreenHeper.sendAwayMenu(GameScreen.this);
	    }
    	if(isReadyMenuDisplaying){
	    	gameReadyScreenHelper.sendAwayMenu(GameScreen.this);
	    }
    	if(isPauseMenuDisplaying){
	    	gamePauseScreenHelper.sendAwayMenu(GameScreen.this);
	    }
    	if(isLevelEndMenuDisplaying){
	    	gameLevelEndScreenHelper.sendAwayMenu(GameScreen.this);
	    }
    	if(isGameOverMenuDisplaying){
	    	gameOverScreenHelper.sendAwayMenu(GameScreen.this);
	    }
    	
    }
    
	private void setUpMenu() {
		// TODO Auto-generated method stub
		gameAchievementMenuScreenHeper = new GameAchievementMenuScreenHeper();
		gameLevelEndScreenHelper = new GameLevelEndScreenHelper();
		gameOverScreenHelper = new GameOverScreenHelper();
		gamePauseScreenHelper = new GamePauseScreenHelper();
		gameReadyScreenHelper = new GameReadyScreenHelper();
		dronaWeaponSelectScreenHelper = new DronaWeaponSelectScreenHelper();
		
		gameAchievementMenuScreenHeper.setUpMenu(GameScreen.this);
		gameLevelEndScreenHelper.setUpMenu(GameScreen.this);
		gameOverScreenHelper.setUpMenu(GameScreen.this);
		gamePauseScreenHelper.setUpMenu(GameScreen.this);
		gameReadyScreenHelper.setUpMenu(GameScreen.this);
		dronaWeaponSelectScreenHelper.setUpMenu(GameScreen.this);
	}

	private void setUpScreenElements() {
		// Set Up the background
		setBackgroundTexture(Assets.bg);
	}


	//THIS IS THE MAIN GAME LOOP
	@Override
	public void render(float delta) {		
		super.render(delta);
	    
		update(delta);
		
	    switch(state) {
	    case GAME_READY:
	        renderReady();
	        break;
	        
	    case GAME_RUNNING:	    
	    	renderRunning(delta);
	        break;
	        
	    case GAME_PAUSED:
	    	renderPaused();
	        break;
	        
	    case GAME_LEVEL_END:
	    	renderLevelEnd();
	        break;
	        
	    case GAME_OVER:
	    	renderGameOver();
	        break;
	    }		   
	    
	}


	private void renderGameOver() {
		if(!isGameOverMenuDisplaying){
			//Save the Scores each time the game is over
			saveGameStates();
			gameOverScreenHelper.sendInMenu(GameScreen.this);
		}
	}


	private void renderLevelEnd() {
		if(!isLevelEndMenuDisplaying){
			gameLevelEndScreenHelper.sendInMenu(GameScreen.this);
		}
	}


	private void renderPaused() {
		//Drawing code that goes when the state is PAUSED
		if(!isPauseMenuDisplaying){
			gamePauseScreenHelper.sendInMenu(GameScreen.this);
		}
	}


	private void renderRunning(float delta) {
		//Drawing code that goes when the state is RUNNING
		background.render(delta);
		
		batch.begin();
		
		 //DRAW THE SCREEN HELPERS HERE
	    
		gameAchievementMenuScreenHeper.achievementTable.draw(batch, 1.0f);	    
		
		Assets.whiteFont.draw(batch, "Score : "+World.score, 10, AppSettings.SCREEN_H -20);
		//Assets.whiteFont.draw(batch, "conter : "+gameOverCounterForAds, 10, AppSettings.SCREEN_H -50);
		//Assets.whiteFont.draw(batch, "FPS : "+Gdx.graphics.getFramesPerSecond(), 10, AppSettings.SCREEN_H -50);
		/*	
		
		if(world.bossEnemy!= null){
		Assets.whiteFont.draw(batch, "showVictory : "+world.showVictory+" :: L:"+world.bossEnemy.life, 10, AppSettings.SCREEN_H -80);
		Assets.whiteFont.draw(batch, "Boss State : "+world.bossEnemy.getState()+" getLifeTime-->"+world.bossEnemy.getLifeTime(), 10, AppSettings.SCREEN_H -110);
		Assets.whiteFont.draw(batch, "Path Size : "+world.bossEnemy.path.size, 10, AppSettings.SCREEN_H -140);
		}
		*/
		
		//DRAW THE TWO PLAYER CONTROL BUTTONS ONLY WHEN THE STATE IS RUNNING
		
		if ((Gdx.app.getType() == ApplicationType.Android) || (Gdx.app.getType() == ApplicationType.iOS)){			
			batch.draw(Assets.flyButton, 0f, 0f, 0f, 0f, buttonSize, buttonSize, 1f, 1f, 0f);
			batch.draw(Assets.shoot_button, AppSettings.SCREEN_W -buttonSize, 0f, 0f, 0f, buttonSize, buttonSize, 1f, 1f, 0f);
		}
		
		batch.end();
			
			//RENDER THE ACHIEVEMENTS MENU
			if(!isAchievementMenuDisplaying){
		    	gameAchievementMenuScreenHeper.sendInMenu(GameScreen.this);
		    }
			
			
			
			renderer.renderDrona(delta);
			
			if(world.pawns.size() > 0)
			renderer.renderPawns(delta);
			
			if(world.powerUp.size() > 0)
			renderer.renderPowerUp(delta);
			
			if(world.drona.getWeapons().size() > 0)
		    renderer.renderDronaWeapons(delta);
		    
			if(world.blackDragons.size() > 0)
			renderer.renderBlackDragons(delta);
			
			if(world.magicalPowers.size() > 0)
				renderer.renderMagicalPowers(delta);
			
			if(world.conspirators.size() > 0)
			renderer.renderConspirators(delta);
			
			renderer.effectListRenderer(delta);
			
		    
		    if(world.bossEnemy!=null)
			renderer.renderBossEnemy(delta);
		    
	}


	private void renderReady() {
		//Drawing code that goes when the state is READY
		if(!isReadyMenuDisplaying){
			gameReadyScreenHelper.sendInMenu(GameScreen.this);
		}
	}


	private void update(float delta) {
		// The update() Loop that is executed continually
		 if(delta > 0.1f)
			 delta = 0.1f;
		    
		    switch(state) {
		    case GAME_READY:
		        updateReady();
		        break;
		    case GAME_RUNNING:
		        updateRunning(delta);
		        break;
		    case GAME_PAUSED:
		        updatePaused();
		        break;
		    case GAME_LEVEL_END:
		        updateLevelEnd();
		        break;
		    case GAME_OVER:
		        updateGameOver();
		        break;
		    }
	}

	private void updateGameOver() {		
		if (Gdx.input.justTouched()) { 
			
		gameOverScreenHelper.sendAwayMenu(GameScreen.this);
		resetGame();
		getGame().setScreen(new MainMenuScreen(getGame(), "Main Menu Screen"));
		}
	}

    private void updateAchievementMenu(){
    	TextureRegion frame = null;
    	int healthValue = Math.max(0, Math.min(5,(int)(world.drona.health/100)));
    	switch(healthValue){
    	case 0:
    		frame = Assets.enerylevel1;
    		break;
    		
    	case 1:
    		frame = Assets.enerylevel1;
    		break;
    		
    	case 2:
    		frame = Assets.enerylevel2;
    		break;
    		
    	case 3:
    		frame = Assets.enerylevel3;
    		break;
    		
    	case 4:
    		frame = Assets.enerylevel4;
    		break;
    		
    	case 5:
    		frame = Assets.enerylevel5;
    		break;  		
    	}
    	
    	gameAchievementMenuScreenHeper.healthBar.setTextureExternal(frame, true);
    }
    
	public void saveGameStates() {
        //GET THE EXISTING HIGHSCORES FROM PREFERENCES
        int[] scoresfromdb = Settings.getHighScores();
        //CHECK IF THE CURRENT SCORE IS GREATER THAN THE STORED ONE
        if(lastScore > scoresfromdb[4]) 
            scoreString = "NEW RECORD: " + lastScore;
        else
            scoreString = "SCORE: " + lastScore;
        //ADD THE NEW SCORE TO THE PREFERENCES IN DECREASING ORDER
        Settings.addScore(lastScore);
        Settings.savePefs(); 
	}


	private void updateLevelEnd() {
		// DO NOTHING... SCREEN HELPERS ARE DOING THE THINGS ... CHECK GameLevelEndScreenHelper.java
		
	}


	private void updatePaused() {
		// DO NOTHING... SCREEN HELPERS ARE DOING THE THINGS ... CHECK GamePauseScreenHelper.java
	}


	private void updateRunning(float delta) {
		// The most important method ... it is called continually while the game is in running state
		updateAchievementMenu();
		world.update(delta);  
		
		// RELOAD FOR DRONA WEAPON
	    if (!reloaded) {
	        reloadTime -= 180*delta; // As I said, this is the timer

	        // If the reload finished, set the timer back to the reload time
	        if (reloadTime <= 0) {
	            reloaded = true; // reloaded, we can shoot
	            reloadTime = startReloadTime;
	        }
	    }
	    
	    if (!generateTimerReady) {
	        generationTime -= 100*delta; // This is again a timer .. that generates enemies at certain intervals

	        // If the reload finished, set the timer back to the reload time
	        if (generationTime <= 0) {
	        	generateTimerReady = true; // reloaded, we can continue generating enemies to the world
	        	generationTime = startReloadTime;
	        }
	    }
		
	    
	    if (generateTimerReady && (world.state == World.WORLD_STATE_RUNNING)) {	    
	    	levelGenerator.generateLevel(currentlevel);
	    	levelGenerator.generateConspirators(world.drona.position.y+Drona.height/2);
			generateTimerReady = false;
			}
	    
	   
		if(world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
	    }

		if(world.state == World.WORLD_STATE_NEXT_LEVEL) {
			state = GAME_LEVEL_END;
	    }
			    
		//UPDATE THE PLAYER FOR USER INPUT
		updatePlayerForUserInput(delta);
		lastScore = World.score;
	}


	public void resetGame() {
		// It should be called each time the game is over... reset the scores here
		lastScore = World.score =0;
		renderer.dispose();
	}

	private void updatePlayerForUserInput(float delta) {
		// The two buttons .. Fly and Attack are here ... 
		boolean fly = false;
		boolean attack = false;
		boolean pause = false;
		world.drona.state=Drona.FLYING;
		
		if (Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS) {

				for (int i = 0; i < 2; i++) {
					int x = (int)(Gdx.input.getX(i) / (float)Gdx.graphics.getWidth() * AppSettings.SCREEN_W);
					int y = (int)(Gdx.input.getY(i) / (float)Gdx.graphics.getWidth() * AppSettings.SCREEN_W);
					if (!Gdx.input.isTouched(i)) continue;
					
					if(y<= AppSettings.SCREEN_H && y>= AppSettings.SCREEN_H -buttonSize){
					if (x <=buttonSize) {						
						fly |= true;
					}

					if (x >= AppSettings.SCREEN_W - buttonSize && x < AppSettings.SCREEN_W) {
					
						attack |= true;
					}										
				}
					
                    if (x >= AppSettings.SCREEN_W -80 && y >= 0 && y <= 80) {
						pause |= true;
					}					
				}
			}
		
		// CHECK USER INPUT AND APPLY TO VELOCITY AND STATES OF THE MAIN PLAYER
				if ((Gdx.input.isKeyPressed(Keys.SPACE) || fly)&& world.drona.position.y<AppSettings.SCREEN_H- Drona.height)
				{					
					world.drona.velocity.y = world.drona.MAX_VELOCITY;
				}
				
				if (Gdx.input.isKeyPressed(Keys.UP) || attack)
				{
					
//					if(Drona.currentWeapon == Weapon.LARGE && !isSpecialPowerActive)
//						return;
		
					if(reloaded){
					reloaded = false;
					world.drona.fire();
					}
						
				}
				
				if (Gdx.input.isKeyPressed(Keys.P) || pause)
				{
					state = GAME_PAUSED;
				}
	}


	private void updateReady() {
		// DO NOTHING... SCREEN HELPERS ARE DOING THE THINGS ... CHECK GameReadyScreenHelper.java
	}


	public void createNewWorld() {
		// This method is called each time the player completes a level
		 World.score = lastScore;
		 world.state = World.WORLD_STATE_RUNNING;
		 world.distance =0;
	}
	
	
}
