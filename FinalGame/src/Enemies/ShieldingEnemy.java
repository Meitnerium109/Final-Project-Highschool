package Enemies;

import Misc.Timer;
import PlayerPackage.Player;

public class ShieldingEnemy extends MeleeEnemy {

	boolean previouslyWalking = false;

	public ShieldingEnemy(int x, int y) {
		super(x, y, 40);

		width = 24;
		height = 30;

		loadGraphic("ShieldAlienAnimations.png", true, true, (int) width,
				(int) height);
		addAnimation("Walk", new int[] { 0, 1, 2 }, 20, true);
		addAnimation("Idle", new int[] { 0 }, 1, true);

		play("Walk");

		knockbackTime = 10;
		walkingSpeed = 30;
		damage = 1;

		updateDirectionByVelocity = false;
		numCoins = 10;
		value = 1;

		walkingSpeed = 150;

	}

	public void update() {
		super.update();
	}

	public void showWalkingAnimation() {
		play("Walk");
	}

	public void showStandingAnimation() {
		play("Idle");
	}

	public void standStill(int numFrames) {
		velocity.x = 0;
		acting = true;
		actingTime = numFrames;
		play("Idle");
	}

	public void generateAction() {
		if (!knockedBack && !acting && !dead) {

			if (previouslyWalking) {
				// Stand still after a charge
				standStill((int) 3.5 * Timer.FRAMES_TO_SECONDS);
				previouslyWalking = false;
			} else {
				// If this enemy is to the left of the player
				if (x < Player.playerX) {
					walkRight((int) 1 * Timer.FRAMES_TO_SECONDS);
					previouslyWalking = true;
				} else {
					// To the right of the player
					walkLeft((int) 1 * Timer.FRAMES_TO_SECONDS);
					previouslyWalking = true;
				}
			}

			acting = true;

		}
	}

	public void onDeath() {
		// Show dying AI
		dead = true;
		loadGraphic("rangedAlienDeath.png", true, true, 40, 32);
		addAnimation("Die", new int[] { 0, 1, 2, 3, 4 }, 7, false);
		play("Die");
	}
}
