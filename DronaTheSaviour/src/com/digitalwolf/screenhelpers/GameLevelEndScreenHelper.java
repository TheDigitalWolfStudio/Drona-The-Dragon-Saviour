package com.digitalwolf.screenhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.screens.GameScreen;
import com.digitalwolf.drona.screens.MainMenuScreen;
import com.digitalwolf.drona.world.World;
import com.digitalwolf.gamedata.Settings;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.models.base.EmptyAbstractActor;
import com.moribitotech.mtx.models.base.TableModel;
import com.moribitotech.mtx.settings.AppSettings;

public class GameLevelEndScreenHelper implements ScreenHelper{

	public TableModel readyMenuTable;
	private boolean isDronaPlacedAtLeft = true;
	private ButtonGame levelInfoButton,playButton,backButton,creditsButton;
	private EmptyAbstractActor dronaAnimation;
	private final float dipRatioWidth = 580*AppSettings.getWorldPositionXRatio();
	private final float dipRatioHeight = 85*AppSettings.getWorldPositionYRatio();
	
	
	@Override
	public void setUpMenu(final GameScreen gameScreen) {
		
		dronaAnimation = new EmptyAbstractActor(2048/8f, 275, true);
		dronaAnimation.setPosition(-dronaAnimation.getWidth(),300*AppSettings.getWorldPositionYRatio());
		dronaAnimation.setAnimation(Assets.dronaFlying, true, true);
		
		
		readyMenuTable = new TableModel(Assets.highscore_table,
				750*AppSettings.getWorldPositionXRatio(), 420*AppSettings.getWorldPositionYRatio());
			
	    readyMenuTable.setPosition(100*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H + 1.2f*readyMenuTable.getHeight());
		
	    levelInfoButton = MenuCreator.createCustomGameButton(Assets.gameFont,
			Assets.rectangle_button2, Assets.rectangle_button2);	
	    levelInfoButton.setTextPosXY(40*AppSettings.getWorldPositionXRatio(), 50*AppSettings.getWorldPositionYRatio());
	    
	  	

	    playButton = MenuCreator.createCustomGameButton(Assets.gameFont,
			Assets.rectangle_button2, Assets.rectangle_button2);	
	    playButton.setTextPosXY(50*AppSettings.getWorldPositionXRatio(), 50*AppSettings.getWorldPositionYRatio());
	    playButton.setText("Continue", true);
	    playButton.addListener(new ActorGestureListener() {
		@Override
		public void touchUp(InputEvent event, float x, float y,
				int pointer, int button) {
			super.touchUp(event, x, y, pointer, button);
			
			sendAwayMenu(gameScreen);
			 Gdx.app.log("A HIT", "GAME WENT FROM READY TO RUNNING STATE"); 
		    
			gameScreen.playerContinuesGame = true;
			GameScreen.currentlevel++;
			Settings.addToUnLockedLevel(GameScreen.currentlevel);
			gameScreen.createNewWorld();
			GameScreen.state = GameScreen.GAME_READY;
		}
	});

	    
	    creditsButton = MenuCreator.createCustomGameButton(Assets.gameFont,
				Assets.nothing, Assets.nothing, 400*AppSettings.getWorldPositionXRatio(), 85*AppSettings.getWorldPositionYRatio(), true);
	    creditsButton.setPosition(260*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H +2*creditsButton.getHeight());
	    creditsButton.setTextureExternalSize(400*AppSettings.getWorldPositionXRatio(), 85*AppSettings.getWorldPositionYRatio());
	    gameScreen.getStage().addActor(creditsButton);
	    
	    backButton = MenuCreator.createCustomGameButton(Assets.gameFont,
				Assets.rectangle_button2, Assets.rectangle_button2);	
	    backButton.setTextPosXY(70*AppSettings.getWorldPositionXRatio(), 50*AppSettings.getWorldPositionYRatio());
	    backButton.setText("Main Menu", true);
	    backButton.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				sendAwayMenu(gameScreen);
				 Gdx.app.log("A HIT", "GAME WENT FROM READY TO RUNNING STATE"); 
			     
				gameScreen.playerContinuesGame = true;
				gameScreen.saveGameStates();
				gameScreen.resetGame();
				gameScreen.getGame().setScreen(new MainMenuScreen(gameScreen.getGame(), "MainMenuScreen"));
				}
		});

	    
	readyMenuTable.add(levelInfoButton).size(dipRatioWidth, dipRatioHeight).center();
	readyMenuTable.row();
	readyMenuTable.add(backButton).size(2*dipRatioWidth/3, dipRatioHeight).center();
	readyMenuTable.row();
	readyMenuTable.add(playButton).size(dipRatioWidth/2, dipRatioHeight).center();


	gameScreen.getStage().addActor(readyMenuTable);
	gameScreen.getStage().addActor(dronaAnimation);

	         
	}

	@Override
	public void sendInMenu(GameScreen gameScreen) {
		// SEND AWAY ALL THE SCREEN HELPERS FIRST
		Assets.playSound(Assets.victory);
		if(!isDronaPlacedAtLeft){
			dronaAnimation.setPosition(-dronaAnimation.getWidth(),300*AppSettings.getWorldPositionYRatio());
		}
		gameScreen.sendAwayAllScreenHelpers();
		
		//Display How Bravely the player Played the Level
		
		switch(World.creditsStar){
		case 1:
			creditsButton.setTextureExternal(Assets.star1, true);
			break;
			
		case 2:
			creditsButton.setTextureExternal(Assets.star2, true);
			break;
			
		case 3:
			creditsButton.setTextureExternal(Assets.star3, true);
			break;
			
		case 4:
			creditsButton.setTextureExternal(Assets.star4, true);
			break;
			
		case 5:
			creditsButton.setTextureExternal(Assets.star5, true);
			break;
			
		default:
			creditsButton.setTextureExternal(Assets.star5, true);
			break;
				
		}
		
		levelInfoButton.setText(" LEVEL "+GameScreen.currentlevel+" COMPLETED", true);
		gameScreen.isLevelEndMenuDisplaying = true;
		
		creditsButton.addAction(Actions.moveTo(260*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H -1.1f*creditsButton.getHeight(), 0.5f));
		readyMenuTable.addAction(Actions.moveTo(100*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H - 1.2f*readyMenuTable.getHeight(), 0.5f));
		dronaAnimation.addAction(Actions.moveTo(AppSettings.SCREEN_W-2*dronaAnimation.getWidth(), 300*AppSettings.getWorldPositionYRatio(), 1.5f));	
	}

	@Override
	public void sendAwayMenu(GameScreen gameScreen) {
		
		gameScreen.isLevelEndMenuDisplaying = false;
		isDronaPlacedAtLeft = false;
		readyMenuTable.addAction(Actions.moveTo(100*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H + 1.2f*readyMenuTable.getHeight(), 0.5f));
		dronaAnimation.addAction(Actions.moveTo(AppSettings.SCREEN_W+dronaAnimation.getWidth(), AppSettings.SCREEN_H, 1.0f));	
		creditsButton.addAction(Actions.moveTo(260*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H +2*creditsButton.getHeight(), 0.5f));
		Assets.victory.stop();
	}
}
