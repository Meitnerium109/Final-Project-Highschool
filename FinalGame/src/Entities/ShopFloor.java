package Entities;

import Misc.RandomNumber;

public class ShopFloor extends Floor {

	public ShopFloor(int x, int y) {
		super(x, y, 32, 32);

		int randomNumber = RandomNumber.generateRandomNumber(1, 1);

		loadGraphic("ShopFloor2.png");

		switch (randomNumber) {
		case 0:
			loadGraphic("ShopFloor1.png");
			break;
		case 1:
			loadGraphic("ShopFloor2.png");
			break;
		}

	}
}
