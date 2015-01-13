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

package com.moribitotech.mtx;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moribitotech.mtx.settings.MtxLogger;
import com.moribitotech.mtx.utils.UtilsActor;

public class CollisionDetector {
	protected static final String logTag = "MtxCollisionDetectorLog";
	public static boolean logActive = true;

	/**
	 * Check collision from actor's rectangles
	 * */
	public static boolean getActorsCollisionFromRectangles(Actor actor1,
			Actor actor2) {
		// Create rectangles from actors
		Rectangle rectA1 = UtilsActor.getRectangleOfActor(actor1);
		Rectangle rectA2 = UtilsActor.getRectangleOfActor(actor2);

		// Check if rectangles collides
		if (Intersector.overlaps(rectA1, rectA2)) {
			logCollision(actor1, actor2);
			return true;
		} else {
			return false;
		}
	}

	private static void logCollision(Actor a1, Actor a2) {
		String str = "Collision detected: Actor (Name: " + a1.getName()
				+ ") and Actor (Name:" + a2.getName() + ")";
		MtxLogger.log(logActive, true, logTag, str);
	}
}
