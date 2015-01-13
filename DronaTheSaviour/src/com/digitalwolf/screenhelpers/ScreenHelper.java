package com.digitalwolf.screenhelpers;

import com.digitalwolf.drona.screens.GameScreen;

/*
 * This is simply an interface that will be implemented by all the Screen Helpers
 */
public interface ScreenHelper {

	public void setUpMenu(GameScreen gameScreen);
	public void sendInMenu(GameScreen gameScreen);
	public void sendAwayMenu(GameScreen gameScreen);
}
