package Entities;

import org.flixel.FlxSprite;

public class Ladder extends Entity {

	public FlxSprite ladderSprite;
	public boolean bottom;

	public Ladder(int x, int y) {
		super(x, y);

		width = 1;
		height = 32;

		makeGraphic((int) width, (int) height, 0x00FF0000);

		ladderSprite = new FlxSprite(x - 5, y, "ladder.png");
	}

	public Ladder(int x, int y, boolean bottom) {
		super(x, y);

		this.bottom = bottom;
		width = 1;
		height = 32;

		makeGraphic((int) width, (int) height, 0x00FF0000);

		ladderSprite = new FlxSprite(x - 5, y, "ladder.png");
	}

	public void kill() {
		ladderSprite.kill();
		super.kill();
	}

}
