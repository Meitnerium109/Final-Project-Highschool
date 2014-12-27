package Enemies;

import org.flixel.FlxG;

import Misc.Timer;
import PlayerPackage.Player;

public class ExplodingSkeleton extends MeleeEnemy {

	// 38 x 24
	public ExplodingSkeleton(int x, int y) {
		super(x, y, 100);

		width = 48;
		height = 38;

		loadGraphic("ExplodingSkeletonAnimations.png", true, true, (int) width,
				(int) height);
		addAnimation("Walk", new int[] { 0, 1, 2 }, 8, true);
		addAnimation("Idle", new int[] { 0 }, 1, true);

		originalDrag = 500;
		damage = 1;
		canDamage = true;

		knockbackTime = 10;
		walkingSpeed = 80;

		// Explosion damage is 2
		damage = 2;

		numCoins = 14;
		value = 2;

		updateDirectionByVelocity = false;

	}

	public void generateAction() {
		if (!knockedBack && !acting && !dead) {
			if (Math.abs(Player.playerY - y) < 60) {
				
				if (x < Player.playerX) {
					walkRight((int) (0.1 * Timer.FRAMES_TO_SECONDS));
				} else {
					walkLeft((int) (0.1 * Timer.FRAMES_TO_SECONDS));
				}

				acting = true;
			} else {
				velocity.x = 0;
			}
		}
	}

	public void showWalkingAnimation() {
		play("Walk");
	}

	public void showStandingAnimation() {
		play("Idle");

	}

	public void update() {
		if (!dead) {
			super.update();
		}
	}

	/**
	 * Just explodes without the player killing it
	 */
	public void explode() {
		if (!dead) {
			onDeath();
			numCoins = 0;
		}

	}

	public void onDeath() {
		if (!dead) {
			FlxG.play("SFX/Explosion.mp3");
			dead = true;
			// 60 x 56
			y -= (56 - 38);

			width = 60;
			height = 56;

			loadGraphic("ExplodingSkeletonDeath.png", true, true, 60, 56);
			addAnimation("Die", new int[] { 0, 1, 2, 3, 4, 5 }, 10, false);
			play("Die");

			velocity.x = 0;
			acceleration.y = 0;
			velocity.y = 0;
			drag.x = originalDrag;
		}
	}

}
