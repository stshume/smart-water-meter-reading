/**
 * Copyright (C) 2013 pauline ruegg-reymond
 * <pauline.ruegg.reymond@gmail.com>
 * eauservice
 * rue de Gen�ve 36
 * case postale 7416
 * CH-1002 Lausanne
 * 
 * This file is part of SmartWaterMeterReading
 * 
 * SmartWaterMeterReading is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SmartWaterMeterReading is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.addtype;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.encog.neural.som.SOM;
import org.recognizer.TypeSpec;

/**
 * This class contains the main function of the program addtype.
 * It creates a object of class GUI and calls methods on it.
 * 
 * The program addtype allows the user to register a new type of water meter. For now, it works only with meters of type GWF MTK so basically you can add GWF MTK types with different diameters.
 * 
 * @author pauline ruegg-reymond
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TypeSpec newType = new TypeSpec();
		
		String typeName = GUI.name();
		if (typeName == null) System.exit(0);
		
		double[][] coordinates = GUI.interestPoints();
		if (coordinates == null) System.exit(0);
		newType.setTenthX(coordinates[0][0]);
		newType.setTenthY(coordinates[0][1]);
		newType.setHundredthX(coordinates[1][0]);
		newType.setHundredthY(coordinates[1][1]);
		newType.setThousandthX(coordinates[2][0]);
		newType.setThousandthY(coordinates[2][1]);
		newType.setTenthousandthX(coordinates[3][0]);
		newType.setTenthousandthY(coordinates[3][1]);
		newType.setMiddleX(coordinates[4][0]);
		newType.setMiddleY(coordinates[4][1]);
		newType.setDialX(coordinates[5][0]);
		newType.setDialY(coordinates[5][1]);
		newType.setDialWidth(coordinates[6][0] - coordinates[5][0]);
		newType.setDialHeight(coordinates[6][1] - coordinates[5][1]);
		
		int[] charDims = GUI.charDims(newType.getDialWidth(), newType.getDialHeight());
		if (charDims == null) System.exit(0);
		newType.setCharWidth(charDims[0]);
		newType.setCharHeight(charDims[1]);
		
		SOM net = GUI.primaryNetwork(newType);
		if (net == null) System.exit(0);
		newType.setNet(net);
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("res/" + typeName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(newType);
			oos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
