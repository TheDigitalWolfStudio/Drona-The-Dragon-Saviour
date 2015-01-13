package com.digitalwolf.drona.screens;


/*
 This is the main menu screen of Drona - The Dragon Saviour
 */


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.gamedata.Settings;
import com.moribitotech.mtx.AbstractScreen;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.IScreen;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.models.base.EmptyAbstractActor;
import com.moribitotech.mtx.models.base.EmptyAbstractActorLight;
import com.moribitotech.mtx.models.base.TableModel;
import com.moribitotech.mtx.settings.AppSettings;

public class MainMenuScreen extends AbstractScreen implements IScreen{

	private EmptyAbstractActorLight logo;
	private EmptyAbstractActor dronaAnimation;
	private ButtonGame creditsButton;
	
	public MainMenuScreen(Game game, String screenName) {
		super(game, screenName);
		
		//Set up all the Initials
		setUpScreenElements();
		setUpInfoPanel();
		setUpMenu();
		
	}

	public void setUpMenu() {
		// Set Up the Menu i.e.the buttons on the Main Menu Screen
	   // Draw a TableMenu that holds the buttons
		TableModel tableMenu = new TableModel(null,
				300*AppSettings.getWorldPositionXRatio(), 3*AppSettings.WORLD_HEIGHT/4);
		
		tableMenu.setPosition(AppSettings.WORLD_WIDTH + tableMenu.getWidth(),
				-AppSettings.getWorldPositionYRatio());
		
		tableMenu.addAction(Actions.moveTo(AppSettings.WORLD_WIDTH - tableMenu.getWidth(), 0, 0.5f));

		// #######################################
		ButtonGame startGameButton = MenuCreator.createCustomGameButton(null, Assets.play_button1, Assets.play_button2);
		
		startGameButton.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				//
				
				getGame().setScreen(new GameScreen(getGame(), "Game Screen"));
			}
		});

		// #########################################
		ButtonGame highScoresButton = MenuCreator.createCustomGameButton(null,
				Assets.highscores_button1, Assets.highscores_button2);
		
		highScoresButton.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				//
				getGame().setScreen(new HighScoresScreen(getGame(), "High Scores Screen"));
			}
		});
		
		// #######################################
		ButtonGame creditsButton = MenuCreator.createCustomGameButton(
				null, Assets.credits_button1,
				Assets.credits_button2);
	
		creditsButton.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				getGame().setScreen(new CreditsScreen(getGame(), "CreditsScreen"));
		
			}
		});

		// #########################################
		final EmptyAbstractActorLight soundToggleButton = new EmptyAbstractActorLight(90, 90, true);
		TextureRegion temp = (Settings.isSoundEnabled())?Assets.sound_on:Assets.sound_off;
		soundToggleButton.setTextureRegion(temp, true);
		
		soundToggleButton.addListener(new ActorGestureListener() {
					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						super.touchUp(event, x, y, pointer, button);
                  //
						if(Settings.isSoundEnabled()){
							soundToggleButton.setTextureRegion(Assets.sound_off, true);
							if(Assets.music.isPlaying()){
								Assets.music.stop();
							}
							Settings.setSoundEnabled(false);
						}
						else{
							soundToggleButton.setTextureRegion(Assets.sound_on, true);
							if(!Assets.music.isPlaying()){
								Assets.music.play();
							}
							Settings.setSoundEnabled(true);
						}
					}
				});
				
		

		//
		float dipRatioWidth = 150 * 1.4f* AppSettings.getWorldSizeRatio();
		float dipRatioHeight =  72 * 1.4f* AppSettings.getWorldSizeRatio();
		float padding = 1.0f * AppSettings.getWorldSizeRatio();

		// #######################################
		
		
		tableMenu.add(startGameButton).size(dipRatioWidth, dipRatioHeight).pad(padding);
		tableMenu.row();
		tableMenu.add(highScoresButton).size(dipRatioWidth, dipRatioHeight).pad(padding);
		tableMenu.row();
		tableMenu.add(creditsButton).size(dipRatioWidth, dipRatioHeight).pad(padding);
		tableMenu.row();
		tableMenu.add(soundToggleButton).padLeft(padding).size(80* AppSettings.getWorldSizeRatio(), 80* AppSettings.getWorldSizeRatio());
	
		getStage().addActor(tableMenu);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
	}

	public void setUpInfoPanel() {
		// Draw the Game Logo on the Main Menu Screen
		logo = new EmptyAbstractActorLight(493, 169, true);
		logo.setPosition(100*AppSettings.getWorldPositionXRatio(), AppSettings.WORLD_HEIGHT - 1.2f*logo.getHeight());		
		logo.setTextureRegion(Assets.logo, true);
		getStage().addActor(logo);
		
		creditsButton = MenuCreator.createCustomGameButton(Assets.whiteFont,
				Assets.nothing, Assets.nothing, 400*AppSettings.getWorldPositionXRatio(), 85*AppSettings.getWorldPositionYRatio(), true);
	    creditsButton.setPosition(50*AppSettings.getWorldPositionXRatio(), 60*AppSettings.getWorldPositionYRatio());
	    creditsButton.setTextureExternalSize(400*AppSettings.getWorldPositionXRatio(), 85*AppSettings.getWorldPositionYRatio());
	    creditsButton.setTextPosXY(0, -10*AppSettings.getWorldPositionYRatio());
	    creditsButton.setText("Last Credit Ranking", true);
	    
	    switch(Settings.getLastCredit()){
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
			creditsButton.setTextureExternal(Assets.star1, true);
			break;
				
		}
	    
	    getStage().addActor(creditsButton);
	}

	public void setUpScreenElements() {
		// Set up the background texture and set back button active
		setBackgroundTexture(Assets.bg);
		setBackButtonActive(true);
		
		dronaAnimation = new EmptyAbstractActor(2048/8f, 275, true);
		dronaAnimation.setPosition(100 *AppSettings.getWorldPositionXRatio(),90*AppSettings.getWorldPositionYRatio());
		dronaAnimation.setAnimation(Assets.dronaFlying, true, true);
		getStage().addActor(dronaAnimation);
	}
	
	@Override
	public void keyBackPressed() {
		super.keyBackPressed();
		Gdx.app.exit();		
    }

}
