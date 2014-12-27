package Enemies;

import org.flixel.FlxSprite;

import Misc.RandomNumber;
import Misc.Timer;
import PlayerPackage.Player;

/**
 * First melee enemy by the character... probably The dog can only damage the
 * player when the variable canDamage is true
 * 
 * @author Andrew Lau
 * 
 */
public class Dog extends MeleeEnemy {

	public Timer actionTimer;
	public boolean onJumpAttack = false;

	public int deathWidth = (int) (32 * 1.5);

	/**
	 * Number of times greater the jump is than the normal walking speed
	 */
	public int jumpSpeedMultiplier = 5;

	public Dog(int x, int y) {
		super(x, y, 100);

		canDamage = false;
		damage = 1;

		originalDrag = 500;

		height = 22;
		width = 32;

		// makeGraphic((int)width,(int)height, 0xFF0000FF);

		loadGraphic("MeleeAlienAnimations.png", true, true, (int) width,
				(int) height);

		addAnimation("Walk", new int[] { 0, 1, 2, 3 }, 12, true);
		addAnimation("Attack Stance", new int[] { 4 }, 1, true);
		addAnimation("Idle", new int[] { 0 }, 1, true);
		addAnimation("Knocked Back", new int[] { 5 }, 1, true);
		play("Walk");

		actionTimer = new Timer(RandomNumber.generateRandomNumber(50, 100));

		knockbackTime = 10;
		walkingSpeed = 50;

		numCoins = 10;
		value = 1;
	}

	public void showWalkingAnimation() {
		play("Walk");
	}

	public void showStandingAnimation() {
		play("Idle");
	}

	public void AI() {

		if (!acting) {
			generateAction();
		}

	}

	public int numActions = 3;

	public void update() {
		if (!dead) {
			super.update();

			if (!knockedBack) {
				// FOR JUST THE ON ATTACK FUNCTION
				if (onJumpAttack) {
					play("Attack Stance");
				}

				if (onJumpAttack
						&& actingCounter == 1 * Timer.FRAMES_TO_SECONDS) {
					// Initiates the jump attack
					jump();
					canDamage = true;
				} else if (onJumpAttack
						&& actingCounter == (int) (1.7 * Timer.FRAMES_TO_SECONDS)) {
					// Sets the velocity to 0 after the jump
					velocity.x = 0;
					onJumpAttack = false;
					canDamage = false;
					play("Idle");
				}
			} else {
				canDamage = false;
			}

			if (knockbackCounter == knockbackTime) {
				standStill((int) (0.8 * Timer.FRAMES_TO_SECONDS));
			}

		}

	}

	public void generateAction() {
		// If the dog is in a specific range of the character, then it will jump
		// at the character

		if (!knockedBack) {
			acting = true;
			drag.x = 0;

			switch (RandomNumber.generateRandomNumber(0, 5)) {
			case 0:
				if (Math.abs(Player.playerX - x) <= 200) {
					jumpAtPlayer();
				} else {
					generateAction();
				}
				break;
			case 1:
				standStill(RandomNumber.generateRandomNumber(
						(int) (1 * Timer.FRAMES_TO_SECONDS),
						3 * Timer.FRAMES_TO_SECONDS));
				break;
			case 2:
			case 4:
				walkLeft(RandomNumber.generateRandomNumber(
						(int) (0.5 * Timer.FRAMES_TO_SECONDS),
						(int) (1.3 * Timer.FRAMES_TO_SECONDS)));
				break;
			case 3:
			case 5:
				walkRight(RandomNumber.generateRandomNumber(
						(int) (0.5 * Timer.FRAMES_TO_SECONDS),
						(int) (1.3 * Timer.FRAMES_TO_SECONDS)));
				break;
			}
		}
	}

	/**
	 * 
	 */
	public void standStill(int numFrames) {
		velocity.x = 0;
		acting = true;
		actingTime = numFrames;
		play("Idle");
	}

	/**
	 * The attack that the dog does to the player
	 */
	public void jumpAtPlayer() {
		onJumpAttack = true;
		acting = true;
		velocity.x = 0;
		play("Attack Stance");

		if (Player.playerX < x) {
			setFacing(FlxSprite.LEFT);
		} else {
			setFacing(FlxSprite.RIGHT);
		}

		actingTime = (int) (2.5 * Timer.FRAMES_TO_SECONDS);
	}

	public void jump() {
		velocity.y = -jumpSpeedY;
		if (getFacing() == FlxSprite.LEFT) {
			velocity.x = -walkingSpeed * jumpSpeedMultiplier;
		} else {
			velocity.x = walkingSpeed * jumpSpeedMultiplier;
		}
	}

	/**
	 * Stops what the enemy is currently doing
	 */

	public void stopAction() {
		acting = false;
		actingCounter = 0;
	}

	public void onDeath() {
		loadGraphic("DogDies.png", true, true, (int) deathWidth, (int) height);
		addAnimation("Death", new int[] { 0, 1, 2, 3 }, 7, false);
		play("Death");
		dead = true;
		velocity.x = 0;
		drag.x = originalDrag;
	}

	public void knockback(int force) {
		if (!dead) {
			onJumpAttack = false;
			play("Knocked Back");
			drag.x = originalDrag;
			super.knockback(force);
		}
	}

}
