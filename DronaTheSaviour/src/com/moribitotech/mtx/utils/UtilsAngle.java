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

import com.moribitotech.mtx.settings.MtxLogger;

public class UtilsAngle {
	
	public UtilsAngle(){
		
	}
	
	public double getAngle(float inputX, float inputY, float objX, float objY) {
		double dx = inputX - objX;
		// Minus to correct for coord re-mapping
		double dy = inputY -  objY;
		//
		double inRads = Math.atan2(dy, dx);

		// We need to map to coord system when 0 degree is at 3 O'clock, 270 at
		// 12 O'clock
		if (inRads < 0) {
			inRads = Math.abs(inRads);
		} else {
			inRads = 2 * Math.PI - inRads;
		}

		double finalDegree = Math.toDegrees(inRads);
		MtxLogger.log(true, true, "MtxUtilsAngle", "" + finalDegree);
		return finalDegree;
	}
	
	public double getDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); 
	}
	
	public double getCrossSizeOfAnTriangle(int side1, double crossSideAngle){
		double unknownAngle = 90 - crossSideAngle;
		//MtxLogger.log(true, true, "MtxUtilsAngle", "" + (crossSideAngle * side1) / unknownAngle);
		return (crossSideAngle * side1) / unknownAngle;
	}
	
	public double calculate(int side1, double crossSideAngle){
		double unknownAngle = 90 - crossSideAngle;
		//MtxLogger.log(true, true, "MtxUtilsAngle", "" + (crossSideAngle * side1) / unknownAngle);
		return (crossSideAngle * side1) / unknownAngle;
	}
}
