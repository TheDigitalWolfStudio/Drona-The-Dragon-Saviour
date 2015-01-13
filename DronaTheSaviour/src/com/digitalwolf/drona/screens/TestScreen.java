package com.digitalwolf.drona.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.digitalwolf.drona.assets.Assets;
import com.digitalwolf.parallaxbackground.ParallaxBackground;
import com.digitalwolf.parallaxbackground.ParallaxLayer;
import com.moribitotech.mtx.AbstractScreen;
import com.moribitotech.mtx.models.base.EmptyAbstractActor;
import com.moribitotech.mtx.settings.AppSettings;

public class TestScreen extends AbstractScreen{

	private ParallaxBackground background;
	private EmptyAbstractActor dronaAnimation;
	
	public TestScreen(Game game, String screenName) {
		super(game, screenName);
		// TODO Auto-generated constructor stub
		setUpScreenElements();
	}
	
	private void setUpScreenElements() {
		// TODO Auto-generated method stub
		background = new ParallaxBackground(new ParallaxLayer[]{
	            new ParallaxLayer(Assets.bg,new Vector2(0.5f, 0f),
        		new Vector2(0, 0),new Vector2(0, AppSettings.SCREEN_H)),
        		
        new ParallaxLayer(Assets.parallaxbg2,new Vector2(1.0f, 0f),
		            		new Vector2(0, 0),new Vector2(0, AppSettings.SCREEN_H))
       
    }, AppSettings.SCREEN_W, AppSettings.SCREEN_H, new Vector2(50,0));
		
		
		dronaAnimation = new EmptyAbstractActor(2048/8f, 275, true);
		dronaAnimation.setPosition(100 *AppSettings.getWorldPositionXRatio(),100*AppSettings.getWorldPositionYRatio());
		dronaAnimation.setAnimation(Assets.dronaFlying, true, true);
		getStage().addActor(dronaAnimation);
		
	}

	@Override
	public void render(float delta) {		
		super.render(delta);
		
		update(delta);
		
		background.render(delta);
		
		getStage().getSpriteBatch().begin();
		dronaAnimation.draw(getStage().getSpriteBatch(), 1.0f);
		getStage().getSpriteBatch().end();
	}

	private void update(float delta) {
		// TODO Auto-generated method stub
		
	}

}
