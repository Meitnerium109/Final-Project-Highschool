package Misc;

import org.flixel.FlxGroup;
import org.flixel.FlxPoint;

import PlayerPackage.Player;

/**
 * The player's hearts
 * 
 * @author Andrew Lau
 * 
 */
public class HeartsCounter {

	public int x;
	public int y;

	public int currentHeartX;
	public int spacing = 5;
	public int heartWidth = 12;
	public int numHalfHearts = 0;

	public FlxGroup heartGroup;

	public Player player;

	public HeartsCounter(int x, int y, int numHalfHearts, Player player) {
		this.player = player;
		this.x = x;
		this.y = y;
		currentHeartX = x;
		this.numHalfHearts = numHalfHearts;
		/**
		 * Must add the heart group when creating a HeartsCounter
		 */
		heartGroup = new FlxGroup(999999);
		createHearts(numHalfHearts);

	}

	public void incrementSpacing() {
		currentHeartX += heartWidth + spacing;
	}

	public void addHeart(int numHalfHearts) {
		this.numHalfHearts += numHalfHearts;

		for (int i = 0; i < heartGroup.length; i++) {
			heartGroup.members.get(i).kill();
		}

		createHearts(this.numHalfHearts);
	}

	public void damage(int numHalfHearts) {
		if (!player.invulnerable) {
			this.numHalfHearts -= numHalfHearts;

			if (this.numHalfHearts <= 0) {
				// The player dies
				// TODO Player dies
			}

			for (int i = 0; i < heartGroup.length; i++) {
				heartGroup.members.get(i).kill();
			}

			createHearts(this.numHalfHearts);

			// The only place where player is set to invulnerable
			player.invulnerable = true;
			player.playerAnimationSprite.play("Invulnerable");
		}
	}

	public void createHearts(int numHalfHearts) {

		currentHeartX = x;
		for (int i = 0; i < (int) (numHalfHearts / 2); i++) {
			Heart heart = new Heart(currentHeartX, y, Heart.FULL_HEART);
			heartGroup.add(heart);

			incrementSpacing();
		}

		if (numHalfHearts % 2 == 1) {
			Heart heart = new Heart(currentHeartX, y, Heart.HALF_HEART);
			heartGroup.add(heart);

			incrementSpacing();
		}

		heartGroup.setAll("scrollFactor", new FlxPoint(0, 0));

	}

}
