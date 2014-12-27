package Misc;

import Entities.Entity;

public class Spring extends Entity {

	public int springCounter = 0;
	public int springTime = 60;
	public boolean springed = false;

	public int springPower = 1000;

	// 28 x 16
	public Spring(int x, int y) {
		super(x, y);

		width = 28;
		height = 16;

		loadGraphic("Spring.png", true, true, (int) width, (int) height);
		addAnimation("Unspring", new int[] { 0 }, 1, true);
		addAnimation("Springed", new int[] { 1 }, 1, true);

	}

	public void update() {
		super.update();

		if (springed) {
			play("Springed");

			if (springCounter < springTime) {
				springCounter++;
			} else {

				springed = false;
				springCounter = 0;
			}
		} else {
			play("Unspring");
		}
	}

	public void spring() {
		play("Springed");
		springed = true;
		springCounter = 0;
	}
}
