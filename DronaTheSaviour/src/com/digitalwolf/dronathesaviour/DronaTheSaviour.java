package com.digitalwolf.dronathesaviour;

import com.badlogic.gdx.Game;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.drona.screens.MainMenuScreen;
import com.digitalwolf.gamedata.Settings;
import com.moribitotech.mtx.SettingsManager;
import com.moribitotech.mtx.settings.AppSettings;
import com.moribitotech.mtx.settings.MtxLogger;

public class DronaTheSaviour extends Game {
	
	 public DronaTheSaviour() {
		  //THIS IS NOT IMPLEMENTED TO BE CALLED BY DESKTOP LAUNCHER 
	 }
	 

	@Override
	public void create() {
		// Called when the game is started
		
      AppSettings.setUp();
		
		if(SettingsManager.isFirstLaunch()){
			SettingsManager.setFirstLaunchDone(true);
			MtxLogger.log(true, true, "LAUNCH", "THIS IS FIRST LAUNCH");
			Settings.createPrefs();
		}
		else{
			MtxLogger.log(true, true, "LAUNCH", "THIS IS NOT FIRST LAUNCH");
			if(Settings.prefs == null)
			   Settings.createPrefs();
		}
		// Load assets before setting the screen
		
		Assets.loadAll();
		// #####################################
		setScreen(new MainMenuScreen(this, "Main Menu Screen"));
	}
	
}
