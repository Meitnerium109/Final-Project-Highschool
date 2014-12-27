package Misc;

import Entities.Entity;

public class Key extends Entity {

	// 26 x 12

	public Key(int x, int y) {
		super(x, y);

		width = 26;
		height = 12;

		loadGraphic("Key.png");

		acceleration.y = 980;
	}
}