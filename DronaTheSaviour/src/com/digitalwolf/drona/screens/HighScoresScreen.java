package com.digitalwolf.drona.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.gamedata.Settings;
import com.moribitotech.mtx.AbstractScreen;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.models.base.EmptyAbstractActor;
import com.moribitotech.mtx.models.base.TableModel;
import com.moribitotech.mtx.models.base.Text;
import com.moribitotech.mtx.settings.AppSettings;

public class HighScoresScreen extends AbstractScreen{

	private TableModel tableMenu;
	float dipRatioWidth = AppSettings.WORLD_WIDTH/2.5f;
	float dipRatioHeight = 50 * AppSettings.getWorldSizeRatio();
	float padding = 2.0f * AppSettings.getWorldSizeRatio();
	private ButtonGame btnHome;
	private EmptyAbstractActor player;
	
	public HighScoresScreen(Game game, String screenName) {
		super(game, screenName);	
		setUpScreenElements();
		setUpActors();
		setUpMenu();
	}

	public void setUpScreenElements() {
		setBackgroundTexture(Assets.bg);
		setBackButtonActive(true);
	}

	private void setUpActors() {
		player = new EmptyAbstractActor(2048/8, 275, true);
		player.setPosition(
				150 * AppSettings.getWorldPositionXRatio()- player.getWidth() / 2.0f,
				200 * AppSettings.getWorldPositionYRatio() - player.getHeight() / 2.0f);
		player.setAnimation(Assets.dronaFlying, true, true);
		getStage().addActor(player);

		
		btnHome = MenuCreator.createCustomGameButton(Assets.gameFont,
				Assets.back_button,Assets.back_button, 64, 64,
				true);
		btnHome.setPosition(AppSettings.SCREEN_W - btnHome.getWidth(), 0);
		btnHome.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);

				getGame().setScreen(new MainMenuScreen(getGame(), "Main Menu Screen"));
		
			}
		});
		getStage().addActor(btnHome);
	}
	
	public void setUpMenu() {
		
		// // #######################################
		tableMenu = new TableModel(Assets.highscore_table,
				520*AppSettings.getWorldPositionXRatio() , 480*AppSettings.getWorldPositionYRatio());			
		tableMenu.setPosition(AppSettings.SCREEN_W - 1.25f*tableMenu.getWidth(), AppSettings.SCREEN_H + tableMenu.getHeight());
		
		tableMenu.addAction(Actions.moveTo(AppSettings.SCREEN_W - 1.25f*tableMenu.getWidth(), AppSettings.SCREEN_H - 1.1f*tableMenu.getHeight(), 0.5f));

		for(int i=0; i<5;i++){
			addScoreButton(i);
		}
		getStage().addActor(tableMenu);
	}

	public void setAwayMenu() {
		tableMenu.addAction(Actions.moveTo(AppSettings.SCREEN_W - 1.25f*tableMenu.getWidth(), AppSettings.SCREEN_H + tableMenu.getHeight(), 0.5f));
	}
	
	private void addScoreButton(int i){
		
		Text scoreText = new Text(Assets.gameFont, dipRatioWidth,dipRatioHeight, true);
		
		scoreText.setText((i+1)+") "+"  "+Settings.getHighScores()[i]);
		tableMenu.add(scoreText).size(dipRatioWidth, 4*dipRatioHeight/5);
		tableMenu.row();
		}
	
	@Override
	public void keyBackPressed() {
		super.keyBackPressed();
		getGame().setScreen(new MainMenuScreen(getGame(), "MenuScreen"));
	}
}
