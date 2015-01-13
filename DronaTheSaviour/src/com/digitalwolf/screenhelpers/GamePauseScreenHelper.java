package com.digitalwolf.screenhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.screens.GameScreen;
import com.digitalwolf.drona.screens.MainMenuScreen;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.models.base.TableModel;
import com.moribitotech.mtx.settings.AppSettings;

	public class GamePauseScreenHelper implements ScreenHelper{

		public TableModel pauseMenuTable;
		private ButtonGame mainMenuButton,resumeButton, quitButton;
		
		@Override
		public void setUpMenu(final GameScreen gameScreen) {
			
			pauseMenuTable = new TableModel(Assets.transparent,
					AppSettings.WORLD_WIDTH , AppSettings.WORLD_HEIGHT);
			pauseMenuTable.setPosition(0 , AppSettings.WORLD_HEIGHT + pauseMenuTable.getHeight());
				
			//MAIN MENU BUTTON ON THE RIGHT SIDE
			mainMenuButton = MenuCreator.createCustomGameButton(Assets.gameFont,
					Assets.rectangle_button2, Assets.rectangle_button);
			mainMenuButton.setTextPosXY(60, 80);
			mainMenuButton.setText("Main Menu", true);

			mainMenuButton.addListener(new ActorGestureListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);
					
					sendAwayMenu(gameScreen);
					gameScreen.getGame().setScreen(new MainMenuScreen(gameScreen.getGame(), "MainMenuScreen"));
				}
			});
			
			resumeButton = MenuCreator.createCustomGameButton(Assets.gameFont,
					Assets.rectangle_button2, Assets.rectangle_button);
			resumeButton.setTextPosXY(60, 80);
			resumeButton.setText("Resume", true);
			
			resumeButton.addListener(new ActorGestureListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);
					
					sendAwayMenu(gameScreen);			
					GameScreen.state = GameScreen.GAME_RUNNING;
				}
			});
			
			
			quitButton = MenuCreator.createCustomGameButton(Assets.gameFont,
					Assets.rectangle_button2, Assets.rectangle_button);
			quitButton.setTextPosXY(60, 80);
			quitButton.setText("Quit", true);
			
			quitButton.addListener(new ActorGestureListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);
					
					Gdx.app.exit();
				}
			});
			
			float dipRatioWidth = 300 * AppSettings.getWorldSizeRatio();
			float dipRatioHeight = 150* AppSettings.getWorldSizeRatio();
			float dipPadding = 5.0f * AppSettings.getWorldSizeRatio();

			// #######################################
			//tableMenu.add(shootingActor).size(dipRatioWidth, dipRatioHeight).pad(dipPadding);
			pauseMenuTable.add(mainMenuButton).size(dipRatioWidth, dipRatioHeight).pad(dipPadding);
			pauseMenuTable.row();
			pauseMenuTable.add(resumeButton).size(dipRatioWidth, dipRatioHeight).pad(dipPadding);
			pauseMenuTable.row();
			pauseMenuTable.add(quitButton).size(dipRatioWidth, dipRatioHeight).pad(dipPadding);
			gameScreen.getStage().addActor(pauseMenuTable);

		}

		@Override
		public void sendInMenu(GameScreen gameScreen) {
			// SEND AWAY ALL THE SCREEN HELPERS FIRST
			gameScreen.sendAwayAllScreenHelpers();
			gameScreen.isPauseMenuDisplaying = true;
			pauseMenuTable.addAction(Actions.moveTo(0, AppSettings.WORLD_HEIGHT- pauseMenuTable.getHeight(), 0.5f));
		}

		@Override
		public void sendAwayMenu(GameScreen gameScreen) {
			GameScreen.state = GameScreen.GAME_RUNNING;
			gameScreen.isPauseMenuDisplaying = false;
			pauseMenuTable.addAction(Actions.moveTo(0 , AppSettings.WORLD_HEIGHT + pauseMenuTable.getHeight(), 0.5f));
		}
}
