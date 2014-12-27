package Entities;

import org.flixel.FlxSprite;

public class LadderBlock extends Ladder {

	public FlxSprite blockSprite;

	public LadderBlock(int x, int y) {
		super(x, y);

		width = 32;
		height = 32;
		
		blockSprite = new FlxSprite(x - 12, y);
		blockSprite.makeGraphic(32, 32, 0xFF000000);

	}

	public void kill() {
		blockSprite.kill();
		super.kill();
	}
}
