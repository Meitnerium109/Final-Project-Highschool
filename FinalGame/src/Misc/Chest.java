package Misc;

import Entities.Entity;

public class Chest extends Entity {

	public int numCoins = 0;
	public int value = 0;
	public int explosionForce = 150;

	public boolean opened = false;

	public Chest(int x, int y, int numCoins, int value) {
		super(x, y);

		width = 64;
		height = 64;

		loadGraphic("Chest.png", true, false, (int) width, (int) height);
		addAnimation("Open", new int[] { 0, 1, 2, 3 }, 7, false);
		addAnimation("Closed", new int[] { 0 }, 1, false);

		play("Closed");

		// The chest can hold a bunch of stuff and items sometimes

		this.numCoins = numCoins;
		this.value = value;

		acceleration.y = 980;
	}

	public void open() {
		opened = true;
		play("Open");
	}
}
