package com.digitalwolf.screenhelpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.screens.GameScreen;
import com.digitalwolf.gamedata.GameOverInfo;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.models.base.TableModel;
import com.moribitotech.mtx.settings.AppSettings;

public class GameOverScreenHelper implements ScreenHelper{
	public TableModel readyMenuTable;
	private ButtonGame screenShotButton,playButton;
	private final float dipRatioWidth = 500*AppSettings.getWorldPositionXRatio();
	private final float dipRatioHeight = 90*AppSettings.getWorldPositionYRatio();
	
	@Override
	public void setUpMenu(final GameScreen gameScreen) {
		
		readyMenuTable = new TableModel(Assets.highscore_table,
				560*AppSettings.getWorldPositionXRatio(), 4f*dipRatioHeight);
			
	    readyMenuTable.setPosition(200*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H + 1.2f*readyMenuTable.getHeight());
	  
	    screenShotButton = MenuCreator.createCustomGameButton(null,
			Assets.nothing, Assets.nothing);	
	    screenShotButton.setTextureExternalPosXY(50*AppSettings.getWorldPositionXRatio(), 60*AppSettings.getWorldPositionYRatio());
	    screenShotButton.setTextureExternalSize(dipRatioWidth/1.3f, 2f*dipRatioHeight);
	    
	    playButton = MenuCreator.createCustomGameButton(Assets.whiteFont,
			Assets.nothing, Assets.nothing);
	  
	    playButton.setPosition(220*AppSettings.getWorldPositionXRatio(), -2*playButton.getHeight());
	    playButton.setTextPosXY(60*AppSettings.getWorldPositionXRatio(), 70*AppSettings.getWorldPositionYRatio());
	    
	readyMenuTable.add(screenShotButton).size(dipRatioWidth, 3.5f*dipRatioHeight).center();
	
	gameScreen.getStage().addActor(readyMenuTable);
	gameScreen.getStage().addActor(playButton);
	}

	@Override
	public void sendInMenu(final GameScreen gameScreen) {
		// SEND AWAY ALL THE SCREEN HELPERS FIRST
		
		gameScreen.sendAwayAllScreenHelpers();
		gameScreen.dronaWeaponSelectScreenHelper.sendAwayMenu(gameScreen);
		// Set the Game Over Texture
		 TextureRegion info = null;
		    switch(GameScreen.gameOverInfo){
		    
		    case GameOverInfo.DRONA_FELL_DOWN:
		    	info = Assets.infoDronaDead;
		    	break;
		    	
		    case GameOverInfo.DRONA_KILLED_BY_PAWNS:
		    	info = Assets.infoKilledByPawns;
		    	break;
		    	
		    case GameOverInfo.DRONA_KILLED_BY_CONSPIRATORS:
		    	info = Assets.infoKilledByConspirators;
		    	break;
		    	
		    case GameOverInfo.DRONA_KILLED_BY_BOSS_ENEMY:
		    	info = Assets.infoKilledByBossEnemy;
		    	break;
		    	
		    }
		    
		screenShotButton.setTextureExternal(info, true);
		playButton.setText(""+GameScreen.scoreString, true);
	
		gameScreen.isGameOverMenuDisplaying = true;
		readyMenuTable.addAction(Actions.moveTo(200*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H - 1.1f*readyMenuTable.getHeight(), 0.5f));
		playButton.addAction(Actions.moveTo(220*AppSettings.getWorldPositionXRatio(), playButton.getHeight()/2, 0.5f));	
	}

	@Override
	public void sendAwayMenu(GameScreen gameScreen) {
		
		gameScreen.isGameOverMenuDisplaying = false;
		readyMenuTable.addAction(Actions.moveTo(200*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H + 1.2f*readyMenuTable.getHeight(), 0.5f));
		playButton.addAction(Actions.moveTo(220*AppSettings.getWorldPositionXRatio(), -2*playButton.getHeight(), 0.5f));
	}
}
