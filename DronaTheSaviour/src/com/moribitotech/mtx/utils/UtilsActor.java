/*******************************************************************************
 * Copyright 2012-Present, MoribitoTech
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.moribitotech.mtx.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class UtilsActor {
	/**
	 * Get the rectangle of an actor from its current POSITION and SIZE
	 * */
	public static Rectangle getRectangleOfActor(Actor actor) {
		float posX = actor.getX();
		float posY = actor.getY();
		float width = actor.getWidth();
		float height = actor.getHeight();
		return new Rectangle(posX, posY, width, height);
	}

	/**
	 * Get center x point of an object
	 * <p>
	 * EXAMPLE<br>
	 * Object's width 200, and we touched the screen in 400 in position X, and
	 * we want to center the object according to our touch position. (200 / 2 =
	 * 100 then 400 - 100), so 300 our center position
	 * 
	 * */
	public static float getCenterX(float eventX, float objectWidth) {
		return eventX - (objectWidth / 2);
	}

	/**
	 * @see getCenterX()
	 * */
	public static float getCenterY(float eventY, float objectHeight) {
		return eventY - (objectHeight / 2);
	}
}
