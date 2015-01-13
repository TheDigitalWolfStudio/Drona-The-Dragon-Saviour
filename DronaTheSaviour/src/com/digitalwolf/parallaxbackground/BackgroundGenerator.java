package com.digitalwolf.parallaxbackground;

import com.badlogic.gdx.math.Vector2;
import com.digitalwolf.drona.assets.Assets;
import com.moribitotech.mtx.settings.AppSettings;

public class BackgroundGenerator {

	public static ParallaxBackground getBackground(){
		
			return  new ParallaxBackground(new ParallaxLayer[]{
		            new ParallaxLayer(Assets.parallax_bg,new Vector2(0.5f, 0f),
		            		new Vector2(0, 0),new Vector2(0, AppSettings.SCREEN_H)),
		            		
		            new ParallaxLayer(Assets.parallaxbg2,new Vector2(1.0f, 0f),
		 		            		new Vector2(0, 0),new Vector2(0, AppSettings.SCREEN_H))
		           
			}, AppSettings.SCREEN_W, AppSettings.SCREEN_H, new Vector2(150,0));
		
	}
}
