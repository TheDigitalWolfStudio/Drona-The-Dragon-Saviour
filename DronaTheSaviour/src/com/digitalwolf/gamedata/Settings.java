package com.digitalwolf.gamedata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

	public static boolean soundEnabled = true;
    public static int[] highscores = new int[] { 40, 30, 20, 10, 0 };
    public static int NUMBER_OF_LEVELS =20;
    
    public static boolean[] levelUnLocked = new boolean[]
    	{
    	true, true,false,false,false,false,
    	false,false,false,false,false,
    	false,false,false,false,false,
    	false,false,false,false,false
    	};
    
    public static final String PREFS_FILE_NAME = "DronaPreference";
	public final static int MAX_LEVEL_LENGTH = 5000;
	public static Preferences prefs;
		
	public static void createPrefs(){
		 prefs = Gdx.app.getPreferences(PREFS_FILE_NAME);
		 //setLastCreditStar(5);
	}
    
	//THIS METHOD SAVES THE TOP 5 HIGHSCORES IN THE ARRAY highscores[i] UNDER THE KEY highscores1 .. 5
    public static void savePefs(){
	 for(int i = 0; i < 5; i++) {
         prefs.putInteger("highscores"+i, highscores[i]);
     }	  
	 prefs.flush();
    }
    
    public static int[] getHighScores(){
    	for(int i = 0; i < 5; i++) {
    		highscores[i] = prefs.getInteger("highscores"+i);
         }
	prefs.flush();
    return highscores;
    }
    
  //THIS METHOD SAVES THE TOP 5 HIGHSCORES IN THE ARRAY highscores[i] UNDER THE KEY highscores1 .. 5
    public static void saveLevelInfo(){
	 for(int i = 1; i <= NUMBER_OF_LEVELS ; i++) {
         prefs.putBoolean("level"+i, levelUnLocked[i]);
     }
	 prefs.flush();
    }
       
       public static void setSoundEnabled(boolean mode){   	 
           prefs.putBoolean("isSoundEnabled", mode);
           prefs.flush();
           }
    
    public static boolean[] getLevelInfo(){
    	for(int i = 1; i <= NUMBER_OF_LEVELS; i++) {
    		levelUnLocked[i] = prefs.getBoolean("level"+i);
         }
    	
        return levelUnLocked;
    }
   
    public static boolean isSoundEnabled(){
        return prefs.getBoolean("isSoundEnabled", true);
    }
    
    public static void setLastCreditStar(int star){   	 
        prefs.putInteger("star", star);
        prefs.flush();
        }
    
    public static int getLastCredit(){
        return prefs.getInteger("star", -1);
    }
    
    
    // THIS METHOD TAKES THE CURRENT SCORE IF THIS IS GREATER THAN THE PREVIOUS ACHIEVEMENTS AND SAVE IT TO THE 
    //STATIC ARRAY OF highcores[i] IN DECREASING ORDER
    public static void addScore(int score) {
        for(int i=0; i < 5; i++) {
            if(highscores[i] < score) {
                for(int j= 4; j > i; j--)
                    highscores[j] = highscores[j-1];
                highscores[i] = score;
                break;
            }
        }
    }
    
    public static void addToUnLockedLevel(int levelID) {
    	prefs.putBoolean("level"+levelID, true);
    	prefs.flush();
}
}
