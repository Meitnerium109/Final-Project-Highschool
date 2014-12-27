package Misc;

import Entities.Entity;
import PlayerPackage.Player;

public class Coin extends Entity {

	public int value = 1;

	public Coin(int x, int y, int value) {
		super(x, y);

		this.value = value;

		width = 5;
		height = 5;

		if (value <= 1) {
			makeGraphic((int) width, (int) height, 0xFFFFFF00);
		} else if (value <= 5) {
			makeGraphic((int) width, (int) height, 0xFF33FF66);
		}

		acceleration.y = Player.gravity;

		drag.x = 100;

	}
}
