package com.digitalwolf.screenhelpers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.screens.GameScreen;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.models.base.EmptyAbstractActorLight;
import com.moribitotech.mtx.models.base.TableModel;
import com.moribitotech.mtx.settings.AppSettings;

public class GameAchievementMenuScreenHeper implements ScreenHelper{

	public TableModel achievementTable;
	public ButtonGame healthBar,pauseButton;
	public EmptyAbstractActorLight weaponSelectButton;
	private final float dipRatioWidth = 80*AppSettings.getWorldSizeRatio();
	private final float dipRatioHeight = 80*AppSettings.getWorldPositionYRatio();

	@Override
	public void setUpMenu(final GameScreen gameScreen) {
		
		achievementTable = new TableModel(null, 
			300*AppSettings.getWorldSizeRatio(), dipRatioHeight);
		
    achievementTable.setPosition(AppSettings.SCREEN_W-achievementTable.getWidth(), AppSettings.SCREEN_H+achievementTable.getHeight());
    
    weaponSelectButton = new EmptyAbstractActorLight(dipRatioWidth, dipRatioWidth, true);
	weaponSelectButton.setTextureRegion(Assets.weapon1, true);
	
	weaponSelectButton.addListener(new ActorGestureListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);
              
					gameScreen.dronaWeaponSelectScreenHelper.sendInMenu(gameScreen);
				}
			});
	
    healthBar = MenuCreator.createCustomGameButton(null,
		Assets.nothing, Assets.nothing);	
    healthBar.setTextureExternalPosXY(0, 0);
    healthBar.setTextureExternalSize(dipRatioHeight, dipRatioHeight);
    healthBar.addListener(new ActorGestureListener() {
    	@Override
    	public void touchUp(InputEvent event, float x, float y,
    			int pointer, int button) {
    		super.touchUp(event, x, y, pointer, button);
    		
    		sendAwayMenu(gameScreen);
    		if(!gameScreen.isPauseMenuDisplaying){
    			gameScreen.gamePauseScreenHelper.sendInMenu(gameScreen);
    		}
    	}
    });
    
    
    pauseButton = MenuCreator.createCustomGameButton(null,
		Assets.pause_button, Assets.pause_button);	

    pauseButton.addListener(new ActorGestureListener() {
	@Override
	public void touchUp(InputEvent event, float x, float y,
			int pointer, int button) {
		super.touchUp(event, x, y, pointer, button);
		
		GameScreen.state = GameScreen.GAME_PAUSED;
		sendAwayMenu(gameScreen);
		if(!gameScreen.isPauseMenuDisplaying){
			gameScreen.gamePauseScreenHelper.sendInMenu(gameScreen);
		}
	}
});

achievementTable.add(healthBar).size(dipRatioWidth, dipRatioHeight).padRight(dipRatioWidth/4).center();
achievementTable.add(weaponSelectButton).size(dipRatioWidth, dipRatioHeight).padRight(dipRatioWidth/4).center();
achievementTable.add(pauseButton).size(dipRatioWidth, dipRatioHeight).padRight(dipRatioWidth/4).center();


gameScreen.getStage().addActor(achievementTable);

         
	}

	@Override
	public void sendInMenu(final GameScreen gameScreen) {
		// SEND IN ALL THE SCREEN HELPERS FIRST
		gameScreen.sendAwayAllScreenHelpers();
		gameScreen.isAchievementMenuDisplaying = true;
		achievementTable.addAction(Actions.moveTo(AppSettings.SCREEN_W-achievementTable.getWidth(), AppSettings.SCREEN_H-achievementTable.getHeight(), 0.5f));
		
		
	}

	@Override
	public void sendAwayMenu(final GameScreen gameScreen) {
		gameScreen.isAchievementMenuDisplaying = false;
		achievementTable.addAction(Actions.moveTo(AppSettings.SCREEN_W-achievementTable.getWidth(), AppSettings.SCREEN_H+achievementTable.getHeight(), 0.5f));
		
	}
}