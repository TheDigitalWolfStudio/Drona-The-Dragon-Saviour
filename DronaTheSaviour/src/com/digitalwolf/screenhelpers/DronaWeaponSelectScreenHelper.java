package com.digitalwolf.screenhelpers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.screens.GameScreen;
import com.digitalwolf.drona.sprites.Drona;
import com.digitalwolf.drona.sprites.Weapon;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.models.base.TableModel;
import com.moribitotech.mtx.models.base.Text;
import com.moribitotech.mtx.settings.AppSettings;

public class DronaWeaponSelectScreenHelper implements ScreenHelper{

	public TableModel weaponSelectTable;
	private ButtonGame weapon1Button,weapon2Button,weapon3Button;
	private Text selectionStatus;
	private final float dipRatioWidth = 420*AppSettings.getWorldSizeRatio();
	private final float dipRatioHeight = 120*AppSettings.getWorldSizeRatio();
	
	@Override
	public void setUpMenu(final GameScreen gameScreen) {
		
		selectionStatus = new Text(Assets.whiteFont, dipRatioWidth, dipRatioHeight, true);
		selectionStatus.setPosition(AppSettings.SCREEN_W/6, -selectionStatus.getHeight());
		selectionStatus.setText("Select your Favourite Weapon");
		gameScreen.getStage().addActor(selectionStatus);
		
		weaponSelectTable = new TableModel(null,dipRatioWidth, dipRatioHeight);
			
	    weaponSelectTable.setPosition(250*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H + 1.2f*weaponSelectTable.getHeight());
		
	    weapon1Button = MenuCreator.createCustomGameButton(Assets.smallFont,
			Assets.weapon1, Assets.weapon1);	
	    weapon1Button.setTextPosXY(0, -10);
	    weapon1Button.setText("Fire Bowl", true);

	    weapon1Button.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				selectionStatus.setText("Current Selection : Fire Bowl");
				sendAwayMenu(gameScreen);
				
				Drona.setWeapon(Weapon.NORMAL);
				setCurrentWeaponButtonTexture(gameScreen);
				GameScreen.state = GameScreen.GAME_RUNNING;
				
			}
		});
	    
	    
	    weapon2Button = MenuCreator.createCustomGameButton(Assets.smallFont,
			Assets.weapon2, Assets.weapon2);
	    weapon2Button.setTextPosXY(0, -10);
	    weapon2Button.setText("Red Flash", true);

	    weapon2Button.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				sendAwayMenu(gameScreen);
				selectionStatus.setText("Current Selection : Red Flash");
				Drona.setWeapon(Weapon.MEDIUM);
				setCurrentWeaponButtonTexture(gameScreen);
				GameScreen.state = GameScreen.GAME_RUNNING;
				
			}
		});
	    
	    
	    weapon3Button = MenuCreator.createCustomGameButton(Assets.smallFont,
			Assets.weapon3, Assets.weapon3);	
	    weapon3Button.setTextPosXY(0, -10);
	    weapon3Button.setText("Power Shot", true);
	    
	    weapon3Button.addListener(new ActorGestureListener() {
		@Override
		public void touchUp(InputEvent event, float x, float y,
				int pointer, int button) {
			super.touchUp(event, x, y, pointer, button);
			
			if(GameScreen.isSpecialPowerActive){
			Drona.setWeapon(Weapon.LARGE);
			setCurrentWeaponButtonTexture(gameScreen);
			selectionStatus.setText("Current Selection : Power Shot");
			sendAwayMenu(gameScreen);
			GameScreen.state = GameScreen.GAME_RUNNING;
			}
			else{
			selectionStatus.setText("Collect Divines to Unlock Power Shot");	
			}
			
		}
	});


	weaponSelectTable.add(weapon1Button).size(dipRatioWidth/3, dipRatioWidth/3).center();
	weaponSelectTable.add(weapon2Button).size(dipRatioWidth/3, dipRatioWidth/3).center();
	weaponSelectTable.add(weapon3Button).size(dipRatioWidth/3, dipRatioWidth/3).center();
	gameScreen.getStage().addActor(weaponSelectTable);
	}

	@Override
	public void sendInMenu(final GameScreen gameScreen) {		
		GameScreen.state = GameScreen.GAME_PAUSED;
		gameScreen.isPauseMenuDisplaying = true;
		
		if(!GameScreen.isSpecialPowerActive){
			weapon3Button.setExternalTextureActive(true);
			weapon3Button.setTextureExternal(Assets.lock_icon, true);
			weapon3Button.setTextureExternalPosXY(60, 0);
			weapon3Button.setTextureExternalSize(dipRatioHeight/2,dipRatioHeight/2);
		}
		else{
			weapon3Button.setExternalTextureActive(false);
		}
		
		weaponSelectTable.addAction(Actions.moveTo(250*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H - 2f*weaponSelectTable.getHeight(), 0.5f));
		selectionStatus.addAction(Actions.moveTo(AppSettings.SCREEN_W/6,150*AppSettings.getWorldPositionYRatio(), 0.5f));
		
	}

	@Override
	public void sendAwayMenu(GameScreen gameScreen) {
		gameScreen.isPauseMenuDisplaying = false;
		weaponSelectTable.addAction(Actions.moveTo(250*AppSettings.getWorldPositionXRatio(), AppSettings.SCREEN_H + 1.2f*weaponSelectTable.getHeight(), 0.5f));
		selectionStatus.addAction(Actions.moveTo(AppSettings.SCREEN_W/6, -selectionStatus.getHeight(), 0.5f));
		
	}
	
	public void setCurrentWeaponButtonTexture(GameScreen gameScreen){
		if(Drona.currentWeapon == Weapon.NORMAL){
			gameScreen.gameAchievementMenuScreenHeper.weaponSelectButton.setTextureRegion(Assets.weapon1, true);
		}
		
		if(Drona.currentWeapon == Weapon.MEDIUM){
			gameScreen.gameAchievementMenuScreenHeper.weaponSelectButton.setTextureRegion(Assets.weapon2, true);
		}
		
		if(Drona.currentWeapon == Weapon.LARGE){
			gameScreen.gameAchievementMenuScreenHeper.weaponSelectButton.setTextureRegion(Assets.weapon3, true);
		}
	}
}
