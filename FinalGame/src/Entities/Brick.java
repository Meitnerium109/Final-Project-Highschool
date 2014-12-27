package Entities;

import Misc.RandomNumber;

public class Brick extends Floor {

	public Brick(int x, int y) {
		super(x, y, 36, 30);

		loadGraphic("FloatingTile.png");
	}
}
