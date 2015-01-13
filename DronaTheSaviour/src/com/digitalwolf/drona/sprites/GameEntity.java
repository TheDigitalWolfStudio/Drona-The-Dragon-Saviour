package com.digitalwolf.drona.sprites;

/*
 * The Base class that is extended by most of the sprites in the game
 */
import com.badlogic.gdx.math.Vector2;

public class GameEntity {

	public final Vector2 position;
    public final Vector2 velocity;
    public final Vector2 accel;
    
    public GameEntity(float x, float y) {
        this.position = new Vector2(x,y);
        this.velocity = new Vector2();
        accel = new Vector2();
    }
    
    public void update(float deltaTime) { 	
    }
 
}
