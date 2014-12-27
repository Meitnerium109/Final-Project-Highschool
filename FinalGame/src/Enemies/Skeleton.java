package Enemies;

import org.flixel.FlxSprite;

import PlayerPackage.Player;

public class Skeleton extends MeleeEnemy {
	// 32 x 42
	public Skeleton(int x, int y) {
		super(x, y, 200);

		width = 32;
		height = 42;

		loadGraphic("SkeletonAnimations.png", true, true, (int) width,
				(int) height, true);
		addAnimation("Walk", new int[] { 0, 1, 2, 3 }, 7, true);
		addAnimation("Idle", new int[] { 0 }, 1, true);
		addAnimation("Attack", new int[] { 4, 5, 6, 7 }, 12, true);
		addAnimation("Die", new int[] { 8, 9, 10, 11 }, 4, false);

		play("Walk");

		knockbackTime = 10;
		walkingSpeed = 50;
		numCoins = 10;
		value = 1;

		updateDirectionByVelocity = false;

	}

	public void showWalkingAnimation() {
		play("Walk");
	}

	public void showStandingAnimation() {
		play("Idle");
	}

	public void AI() {
		super.AI();

		if (!acting) {
			generateAction();
		}
	}

	public void onDeath() {
		dead = true;
		play("Die");
		velocity.x = 0;
		drag.x = originalDrag;
	}

	public void attackPlayer() {
		canDamage = true;
		play("Attack");
	}

	public void generateAction() {

		if (Math.abs(Player.playerX - x) < 70) {
			attackPlayer();
		} else {
			canDamage = false;
			showWalkingAnimation();
		}

		if (Math.abs(Player.playerY - y) < 60) {
			if (Player.playerX > x) {
				_facing = FlxSprite.RIGHT;

				velocity.x = walkingSpeed;
			} else {
				_facing = FlxSprite.LEFT;

				velocity.x = -walkingSpeed;
			}
		}
	}

}
