package com.digitalwolf.screenhelpers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.screens.GameScreen;
import com.digitalwolf.drona.sprites.Drona;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.models.base.EmptyAbstractActor;
import com.moribitotech.mtx.models.base.TableModel;
import com.moribitotech.mtx.settings.AppSettings;

public class GameReadyScreenHelper implements ScreenHelper{
	public TableModel readyMenuTable;
	private ButtonGame levelInfoButton,screenShotButton;
	private EmptyAbstractActor dronaAnimation;
	
	private final float dipRatioWidth = 500*AppSettings.getWorldPositionXRatio();
	private final float dipRatioHeight = 100*AppSettings.getWorldPositionYRatio();
	
	@Override
	public void setUpMenu(final GameScreen gameScreen) {
		
		readyMenuTable = new TableModel(null, 
			500*AppSettings.getWorldPositionXRatio(), 4.5f*dipRatioHeight);
		
    readyMenuTable.setPosition(230*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H + 1.1f*readyMenuTable.getHeight());
	
    levelInfoButton = MenuCreator.createCustomGameButton(Assets.gameFont,
		Assets.rectangle_button2, Assets.rectangle_button2);	
    levelInfoButton.setTextPosXY(50*AppSettings.getWorldPositionXRatio(), 60*AppSettings.getWorldPositionYRatio());
    levelInfoButton.addListener(new ActorGestureListener() {
    	@Override
    	public void touchUp(InputEvent event, float x, float y,
    			int pointer, int button) {
    		super.touchUp(event, x, y, pointer, button);
    		
    		sendAwayMenu(gameScreen);
    		GameScreen.state = GameScreen.GAME_RUNNING;
    	}
    });
  
    screenShotButton = MenuCreator.createCustomGameButton(null,
		Assets.highscore_table, Assets.highscore_table);	
    screenShotButton.setTextureExternalPosXY(50*AppSettings.getWorldPositionXRatio(), 80*AppSettings.getWorldPositionYRatio());
    screenShotButton.setTextureExternalSize(dipRatioWidth/1.3f, 1.8f*dipRatioHeight);
    screenShotButton.setTextureExternal(Assets.bg, true);
    screenShotButton.setExternalTextureActive(false);
    
    dronaAnimation = new EmptyAbstractActor(2048/8*0.6f, 275*0.6f, true);
	dronaAnimation.setPosition(300 *AppSettings.getWorldPositionXRatio(),AppSettings.SCREEN_H+2*Drona.height);
	dronaAnimation.setAnimation(Assets.dronaFlying, true, true);
	
    readyMenuTable.add(screenShotButton).size(dipRatioWidth, 3.5f*dipRatioHeight).center();
    readyMenuTable.row();
    readyMenuTable.add(levelInfoButton).size(4*dipRatioWidth/5, dipRatioHeight).center();

    gameScreen.getStage().addActor(readyMenuTable);
    gameScreen.getStage().addActor(dronaAnimation);
         
	}

	@Override
	public void sendInMenu(final GameScreen gameScreen) {
		// SEND AWAY ALL THE SCREEN HELPERS FIRST
		Assets.playSound(Assets.appearing);
		gameScreen.sendAwayAllScreenHelpers();
		levelInfoButton.setText(" LEVEL : "+GameScreen.currentlevel, true);
		
		gameScreen.isReadyMenuDisplaying = true;
		readyMenuTable.addAction(Actions.moveTo(230*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H - 1.1f*readyMenuTable.getHeight(), 0.5f));
		screenShotButton.setExternalTextureActive(true);
		dronaAnimation.addAction(Actions.moveTo(300 *AppSettings.getWorldPositionXRatio(),AppSettings.SCREEN_H-300*AppSettings.getWorldPositionYRatio(), 0.5f));	
	}

	@Override
	public void sendAwayMenu(final GameScreen gameScreen) {
		gameScreen.isReadyMenuDisplaying = false;
		readyMenuTable.addAction(Actions.moveTo(230*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H + 1.1f*readyMenuTable.getHeight(), 0.5f));
		dronaAnimation.addAction(Actions.moveTo(300 *AppSettings.getWorldPositionXRatio(),AppSettings.SCREEN_H+2*Drona.height, 0.5f));
		Assets.appearing.stop();
	}

}
